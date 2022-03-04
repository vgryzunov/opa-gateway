package ru.reinform.bundleserver.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

@Data
@AllArgsConstructor
public class ResponseBundle {
    private InputStream inputStream;
    private Long length;
}
