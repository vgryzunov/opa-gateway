package ru.reinform.bundleserver.service;
import ru.reinform.bundleserver.config.BundleConfigProperties;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.reinform.bundleserver.config.BundleConfiguration;
import ru.reinform.bundleserver.config.BundleRoot;
import ru.reinform.bundleserver.config.BundleTarget;
import ru.reinform.bundleserver.data.ResponseBundle;

import java.io.*;
import java.util.List;
import java.util.StringJoiner;

@Service
@Slf4j
public class BundleService {
    private final CertsDataService certsDataService;
    private final ResourceDataService resourceDataService;
    private final BundleConfiguration bundleConfiguration;

    public BundleService(CertsDataService cs,
                         ResourceDataService rs,
                         BundleConfiguration bc) {
        this.certsDataService = cs;
        this.resourceDataService = rs;
        this.bundleConfiguration = bc;
    }

    public ResponseBundle getBundle() throws IOException {

        BundleConfigProperties bc = bundleConfiguration.getBundleProperties();

        String revision = bc.getRevision();
        List<BundleRoot> roots = bc.getRoots();

        StringBuilder mb = new StringBuilder("{\n");
        mb.append("    \"revision\" : \"").append(revision).append("\",\n");
        mb.append("    \"roots\" : [");

        StringJoiner joiner = new StringJoiner(", ");
        roots.stream().forEach(r -> joiner.add( String.format("\"%s\"", r.getName())));
        mb.append(joiner);

        mb.append("]\n}");

        log.info("Manifest: \n{}", mb);

        ByteArrayOutputStream bundleOutputStream = new ByteArrayOutputStream();
        TarArchiveOutputStream tos = new TarArchiveOutputStream(bundleOutputStream);

        addEntry(tos, certsDataService.getCertsData(), "certs/data.json");
        addResourceEntry(tos, "authz/policy.rego");
        addResourceEntry(tos, "authz/data.json");

        addEntry(tos, mb.toString(), ".manifest");

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
