package ir.Mohammad_Haeri.RunnersRecord.run;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunnersRecordRepository {
    private List<Run> runsList = new ArrayList<>();

    public List<Run> getAll() {
        return runsList;
    }

    @PostConstruct
    private void init() {

        runsList.add(new Run(
                1,
                "First Run",
                LocalDateTime.now().minusHours(10).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.now().minusHours(10).plusMinutes(2).toEpochSecond(ZoneOffset.UTC),
                4010,
                98,
                Location.OUTDOOR
        ));

        runsList.add(new Run(
                2,
                "Second Run",
                LocalDateTime.now().minusHours(6).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.now().plusMinutes(4).toEpochSecond(ZoneOffset.UTC),
                6212,
                85,
                Location.OUTDOOR
        ));

        runsList.add(new Run(
                3,
                "Third Run",
                LocalDateTime.now().minusHours(3).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.now().plusMinutes(3).toEpochSecond(ZoneOffset.UTC),
                7650,
                100,
                Location.OUTDOOR
        ));

    }

    public Optional<Run> getById(int id) {
        return runsList.stream()
                .filter(i -> i.id() == id)
                .findFirst();
    }

    public void create(Run run) {
        runsList.add(run);
    }

    public void update(Run run, int id) {
        Optional<Run> existingRun = getById(id);
        existingRun.ifPresent(foundedItem -> runsList.set(runsList.indexOf(foundedItem), run));
    }

    public void delete(int id) {
        runsList.removeIf(run -> run.id() == id);
    }
}



