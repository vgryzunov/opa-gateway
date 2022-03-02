package ru.reinform.bundleserver.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

@Data
@AllArgsConstructor
public class Bundle {
    private InputStream inputStream;
    private Long length;
}
