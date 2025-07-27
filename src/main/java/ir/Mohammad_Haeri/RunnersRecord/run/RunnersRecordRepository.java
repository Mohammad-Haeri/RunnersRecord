package ir.Mohammad_Haeri.RunnersRecord.run;

import ir.Mohammad_Haeri.RunnersRecord.Utils;
import jakarta.annotation.Nullable;
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


    public List<Run> getAll() {
        return jdbcClient.sql("SELECT * FROM Run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> getById(int id) {
        return jdbcClient.sql("SELECT * FROM Run WHERE id = :id")
                // Cannot resolve query paramete error in line 74 is IDE's false positive error.
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    private int create(Run run) {
        return jdbcClient.sql("""
                        INSERT INTO Run(id, title, started_on, completed_on, meters, score, location) 
                        VALUES(?,?,?,?,?,?,?)
                        ON CONFLICT (id) DO NOTHING
                        """)
                .param(run.id())
                .param(run.title())
                .param(run.startedOn())
                .param(run.completedOn())
                .param(run.meters())
                .param(run.score())
                .param(run.location().name())
                .update();
    }

    public void create(Run run, boolean isInitial) {
        if (!isInitial) {
            int result = create(run);
            Assert.state(result == 1, "Inserting " + run.title() + " With Id of "+ run.id() + " has been failed.");
        } else {
            Utils.ignore(create(run));
        }
    }

    public void update(Run run, int id) {
        var updated = jdbcClient.sql("UPDATE run SET title = ?, started_on = ?, completed_on = ?, meters = ?, score = ?, location = ? WHERE id = ?")
                .param(run.title())
                .param(run.startedOn())
                .param(run.completedOn())
                .param(run.meters())
                .param(run.score())
                .param(run.location().name())
                .param(id)
                .update();

        Assert.state(updated == 1, "Updating " + run.title() + " has been failed.");
    }

    public void delete(int id) {
        var deleted = jdbcClient.sql("DELETE FROM Run WHERE id = :id")
                // Cannot resolve query paramete error in line 109 is IDE's false positive error.
                .param("id", id)
                .update();

        Assert.state(deleted == 1, "Deleting item with id of " + id + " has been failed.");
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM Run").query().listOfRows().size();
    }

    public void createAll(List<Run> runs, boolean isInitial) {
        runs.forEach(run -> this.create(run, isInitial));
    }

    public List<Run> findByEventLocation(Location location) {
        return jdbcClient.sql("SELECT * FRON Run WHERE location = :location")
                .param("location", location.name())
                .query(Run.class)
                .list();
    }
}













