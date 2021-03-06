package ru.reinform.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.reinform.demo.data.Meeting;
import ru.reinform.demo.service.DataService;

import java.util.Collection;

@RestController
public class MeetingController {
    private final DataService ds;

    MeetingController(DataService ds) {
        this.ds = ds;
    }

    @GetMapping("/meetings")
    public Collection<Meeting> meetings() {
        return ds.getDb().values();
    }
}
