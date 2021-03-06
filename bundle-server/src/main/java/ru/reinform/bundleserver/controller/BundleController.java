package ru.reinform.bundleserver.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.reinform.bundleserver.data.ResponseBundle;
import ru.reinform.bundleserver.service.BundleService;

import java.io.IOException;

@RestController
public class BundleController {
    private final BundleService bundleService;

    public BundleController(BundleService bs) {
        this.bundleService = bs;
    }

    @GetMapping(value="/bundle.tar.gz",
            produces= MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    ResponseEntity<InputStreamResource> data() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Type", "application/gzip");

        ResponseBundle bundle = bundleService.getBundle();

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(bundle.getLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(bundle.getInputStream()));
    }

}
