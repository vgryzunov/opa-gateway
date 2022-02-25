package ru.reinform.security.opa;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Decision {
    private boolean allowed;
    private String name;
}
