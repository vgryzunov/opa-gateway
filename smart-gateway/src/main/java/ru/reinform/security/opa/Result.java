package ru.reinform.security.opa;

import lombok.Data;

@Data
public class Result {
    private boolean allowed;
    private String name;
    private boolean tokenValid;
}
