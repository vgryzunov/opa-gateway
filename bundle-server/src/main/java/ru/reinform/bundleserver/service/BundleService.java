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
    private final WebDataService webDataService;
    private final ResourceDataService resourceDataService;
    private final BundleConfiguration bundleConfiguration;

    public BundleService(WebDataService ws,
                         ResourceDataService rs,
                         BundleConfiguration bc) {
        this.webDataService = ws;
        this.resourceDataService = rs;
        this.bundleConfiguration = bc;
    }


    public ResponseBundle getBundle() throws IOException {

        String manifest = this.buildManifest();
        log.info("Manifest: \n{}", manifest);

        List<BundleRoot> roots = bundleConfiguration.getBundleProperties().getRoots();

        ByteArrayOutputStream bundleOutputStream = new ByteArrayOutputStream();
        TarArchiveOutputStream tos = new TarArchiveOutputStream(bundleOutputStream);

        addEntry(tos, ".manifest", manifest);

        for (BundleRoot r : roots) {
            addBundleRoot(tos, r);
        }

        byte[] ba = bundleOutputStream.toByteArray();
        InputStream bundleInputStream = new ByteArrayInputStream(ba);
        return new ResponseBundle(bundleInputStream, (long)ba.length);
    }

    private String buildManifest() {
        BundleConfigProperties bc = bundleConfiguration.getBundleProperties();

        String revision = bc.getRevision();
        List<BundleRoot> roots = bc.getRoots();

        StringBuilder mb = new StringBuilder("{\n");
        mb.append("    \"revision\" : \"").append(revision).append("\",\n");
        mb.append("    \"roots\" : [");

        StringJoiner joiner = new StringJoiner(", ");
        roots.forEach(r -> joiner.add( String.format("\"%s\"", r.getName())));
        mb.append(joiner);

        mb.append("]\n}");
        return mb.toString();
    }

    private void addBundleRoot(TarArchiveOutputStream tos, BundleRoot root)  throws IOException {
        for (BundleTarget t : root.getTargets()) {
            addBundleTarget(tos, t);
        }
    }

    private void addBundleTarget(TarArchiveOutputStream tos, BundleTarget target) throws IOException {
        String ty = target.getType();

        if (ty == null){
            throw new IllegalArgumentException("\"bundle.roots[].targets[].type is null\"");
        }

        if (ty.equals("resource")) {
            addResourceTarget(tos, target.getTarget(), target.getUri() );
        } else if (ty.equals("http")) {
            addHttpTarget(tos, target.getTarget(), target.getUri());
        } else {
            throw new IllegalArgumentException("Invalid bundle.roots[].targets[].type: " + ty);
        }
    }

    private void addHttpTarget(TarArchiveOutputStream tos, String target, String uri) throws IOException {
        String httpData = webDataService.getHttpData(uri);
        addEntry(tos, target, httpData);
    }

    private void addEntry(TarArchiveOutputStream tos,
                          String target, @NotNull String contents ) throws IOException {
        TarArchiveEntry entry = new TarArchiveEntry(target);
        long length = contents.length();
        entry.setSize(length);
        tos.putArchiveEntry(entry);

        byte[] bytes = contents.getBytes();
        tos.write(bytes);
        tos.closeArchiveEntry();
    }

    private void addResourceTarget(TarArchiveOutputStream tos, String target, String uri) throws IOException {
        InputStream resourceStream = resourceDataService.getResourceStream(uri);

        TarArchiveEntry entry = new TarArchiveEntry(target);
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
