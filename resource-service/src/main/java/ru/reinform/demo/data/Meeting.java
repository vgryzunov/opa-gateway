package ru.reinform.demo.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Meeting {
    private final Integer id;
    private String name;
    private String managerId;

    @Setter(value = AccessLevel.NONE)
    private Map<Integer, Assignment> assignments;

    public Meeting(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.assignments = Collections.synchronizedMap(new HashMap<>());
    }

    public int getAssignmentsCount() {
        return assignments.size();
    }

    public Meeting assign(Assignment assignment) {
        assignments.put(assignment.getId(), assignment);
        return this;
    }

}
