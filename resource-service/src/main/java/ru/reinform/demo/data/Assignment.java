package ru.reinform.demo.data;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Assignment {
    private final Integer id;
    private String executorId;
    private String description;

    public Assignment(Integer id) {
        this.id = id;
    }
}
