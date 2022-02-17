package ru.reinform.demo.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.reinform.demo.data.Assignment;
import ru.reinform.demo.data.Meeting;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Data
public class DataService {
    private final Map<Integer, Meeting> db;
    private final Random random  = new Random();

    public DataService() {
        AtomicInteger ctr = new AtomicInteger();
        int cnt = 5;

        this.db = IntStream.range(1,cnt).boxed().map(n -> String.format("Meeting %d", n))
                .map(name -> new Meeting(ctr.incrementAndGet(), name))
                .map(this::setAssignments)
                .map(meeting -> meeting.setManagerId(managerId()))
                .collect(Collectors.toMap(Meeting::getId, meeting -> meeting));
    }

    private Meeting setAssignments(Meeting meeting) {
        AtomicInteger ctr = new AtomicInteger();

        int cnt = ThreadLocalRandom.current().nextInt(1, 5);
        IntStream.range(1,cnt).boxed()
                .map(n -> new Assignment(n).setDescription(String.format("Assignment %d %s", meeting.getId(), ctr.incrementAndGet()))
                        .setExecutorId(executorId()))
                .forEach(meeting::assign);
        return meeting;
    }

    public String executorId() {
        return executorIds[random.nextInt(executorIds.length)];
    }

    public String managerId() {
        return managerIds[random.nextInt(managerIds.length)];
    }

    private String[] executorIds = { "ivanov1", "petrov1", "sidorov1"};
    private String[] managerIds = {"manager1"};


}
