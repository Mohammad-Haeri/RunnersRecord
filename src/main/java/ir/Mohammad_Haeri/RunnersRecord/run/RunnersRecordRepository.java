package ir.Mohammad_Haeri.RunnersRecord.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunnersRecordRepository {
    private static final Logger log = LoggerFactory.getLogger(RunnersRecordRepository.class);

    private final JdbcClient jdbcClient;

    public RunnersRecordRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    //Must be changed later to use JDBC
    private final List<Run> runsList = new ArrayList<>();

    public List<Run> getAll() {
        return jdbcClient.sql("SELECT * FROM Run")
                .query(Run.class)
                .list();
    }

    @PostConstruct
    private void init() {

//        runsList.add(new Run(
//                1,
//                "First Run",
//                LocalDateTime.now().minusHours(10).toEpochSecond(ZoneOffset.UTC),
//                LocalDateTime.now().minusHours(10).plusMinutes(2).toEpochSecond(ZoneOffset.UTC),
//                4010,
//                98,
//                Location.OUTDOOR
//        ));
//
//        runsList.add(new Run(
//                2,
//                "Second Run",
//                LocalDateTime.now().minusHours(6).toEpochSecond(ZoneOffset.UTC),
//                LocalDateTime.now().plusMinutes(4).toEpochSecond(ZoneOffset.UTC),
//                6212,
//                85,
//                Location.OUTDOOR
//        ));
//
//        runsList.add(new Run(
//                3,
//                "Third Run",
//                LocalDateTime.now().minusHours(3).toEpochSecond(ZoneOffset.UTC),
//                LocalDateTime.now().plusMinutes(3).toEpochSecond(ZoneOffset.UTC),
//                7650,
//                100,
//                Location.OUTDOOR
//        ));

    }

    public Optional<Run> getById(int id) {
        return jdbcClient.sql("SELECT * FROM Run WHERE id = :id")
                // Cannot resolve query paramete error in line 74 is IDE's false positive error.
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var inserted = jdbcClient.sql("INSERT INTO Run(id, title, started_on, completed_on, meters, score, location) VALUES(?,?,?,?,?,?,?)")
                .param(run.id())
                .param(run.title())
                .param(run.startedOn())
                .param(run.completedOn())
                .param(run.meters())
                .param(run.score())
                .param(run.location().name())
                .update();
        Assert.state(inserted == 1, "Inserting " + run.title() + " has been failed.");
    }

    public void update(Run run, int id) {
        var updated = jdbcClient.sql("UPDATE run SET title = ?, started_on = ?, completed_on = ?, meters = ?, score = ?, location = ? WHERE id = ?")
                .param(run.id())
                .param(run.title())
                .param(run.startedOn())
                .param(run.completedOn())
                .param(run.meters())
                .param(run.score())
                .param(run.location().name())
                .update();

        Assert.state(updated == 1, "Updating " + run.title() + " has been failed.");
    }

    public void delete(int id) {
        var deleted = jdbcClient.sql("DELETE FROM Run WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(deleted == 1, "Deleting item with id of " + id + " has been failed.");
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM Run").query().listOfRows().size();
    }

    public void createAll(List<Run> runs) {
        runs.stream().forEach(this::create);
    }

    public List<Run> findByEventLocation(Location location) {
        return jdbcClient.sql("SELECT * FRON Run WHERE location = :location")
                .param("location", location.toString())
                .query(Run.class)
                .list();
    }


}













