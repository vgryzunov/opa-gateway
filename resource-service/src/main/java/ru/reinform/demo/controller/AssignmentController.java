package ru.reinform.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.reinform.demo.data.Assignment;
import ru.reinform.demo.service.DataService;

import java.util.Collection;

@RestController
public class AssignmentController {
    private final DataService ds;

    AssignmentController(DataService ds) { this.ds = ds; }

    @GetMapping("/assignments")
    public Collection<Assignment> assignments(@RequestParam(value="meetingId") int meetingId) {
        return ds.getDb().get(meetingId).getAssignments().values();
    }

}
