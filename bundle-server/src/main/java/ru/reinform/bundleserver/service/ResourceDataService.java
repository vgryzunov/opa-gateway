package ru.reinform.bundleserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class ResourceDataService {

    public InputStream getResourceStream(final String fileName)
    {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (inputStream == null) {
            log.error("Resource file {} not found", fileName);
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return inputStream;
    }

}
