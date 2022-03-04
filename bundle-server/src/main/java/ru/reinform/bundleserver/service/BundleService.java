package ru.reinform.bundleserver.service;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.reinform.bundleserver.data.ResponseBundle;

import java.io.*;

@Service
public class BundleService {
    private final CertsDataService certsDataService;
    private final ResourceDataService resourceDataService;

    public BundleService(CertsDataService cs,
                         ResourceDataService rs) {
        this.certsDataService = cs;
        this.resourceDataService = rs;
    }

    public ResponseBundle getBundle() throws IOException {

        ByteArrayOutputStream bundleOutputStream = new ByteArrayOutputStream();
        TarArchiveOutputStream tos = new TarArchiveOutputStream(bundleOutputStream);

        String manifestData = "{\n" +
                "    \"revision\" : \"0.0.1\",\n" +
                "    \"roots\" : [\"authz\", \"certs\"]\n" +
                "}";

        addEntry(tos, certsDataService.getCertsData(), "certs/data.json");
        addResourceEntry(tos, "authz/policy.rego");
        addResourceEntry(tos, "authz/data.json");

        addEntry(tos, manifestData, ".manifest");

        byte[] ba = bundleOutputStream.toByteArray();
        InputStream bundleInputStream = new ByteArrayInputStream(ba);
        return new ResponseBundle(bundleInputStream, (long)ba.length);
    }

    private void addEntry(TarArchiveOutputStream tos,
                          @NotNull String contents, String name) throws IOException {
        TarArchiveEntry entry = new TarArchiveEntry(name);
        long length = contents.length();
        entry.setSize(length);
        tos.putArchiveEntry(entry);

        byte[] bytes = contents.getBytes();
        tos.write(bytes);
        tos.closeArchiveEntry();
    }

    private void addResourceEntry(TarArchiveOutputStream tos, String name) throws IOException {
        InputStream resourceStream = resourceDataService.getResourceStream(name);

        TarArchiveEntry entry = new TarArchiveEntry(name);
        byte[] buf = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int length;
        int size = 0;
        while ((length = resourceStream.read(buf)) > 0) {
            size += length;
            byteArrayOutputStream.write(buf, 0, length);
        }
        entry.setSize(size);
        tos.putArchiveEntry(entry);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        while ((length = byteArrayInputStream.read(buf)) > 0) {
            tos.write(buf, 0, length);
        }
        tos.closeArchiveEntry();
    }
}
