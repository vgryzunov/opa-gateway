package ru.reinform.bundleserver.service;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.springframework.stereotype.Service;
import ru.reinform.bundleserver.data.Bundle;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class BundleService {

    public Bundle getBundle() throws IOException {
        String data = "{ \"field\": \"value\" }";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TarArchiveOutputStream tos = new TarArchiveOutputStream(os);

        TarArchiveEntry entry = new TarArchiveEntry("data.json");

        long length = data.length();
        entry.setSize(length);

        tos.putArchiveEntry(entry);
        byte[] bytes = data.getBytes();
        tos.write(bytes);

        tos.closeArchiveEntry();
        byte[] ba = os.toByteArray();
        InputStream is = new ByteArrayInputStream(ba);
        return new Bundle(is, (long)ba.length);
    }
}
