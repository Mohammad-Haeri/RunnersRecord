package ir.Mohammad_Haeri.RunnersRecord.run;

import ir.Mohammad_Haeri.RunnersRecord.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * <p>There are 3 "resolve query parameter" error which in fact is purely an IDE's inspection fault since IDE is not
 * aware of my db and sql model and ... . the easiest way is to suppress the warning, but I am going to implement 2
 * way to solve that warning.
 * 1: using Named Parameter Jdbc Template(MapSqlParameterSource)
 * 2: more robust and reusable and scalable and test suited way of using the query in .sql file and load it here .</p>
 * <p><h6> At the end all the 2 way are just to demonstrate the implementation. my experience say that in terms of
 * scalability factors and testability factors and ..., the 3rd is more senior-eng way.</h6></p><br/>
 * <a href="https://www.Mohammad-Haeri.ir">Portfolio</a> -
 * <a href="https://github.com/Mohammad-Haeri/">GitHub</a> -
 * <a href="https://www.linkedin.com/in/Mohammad-Haeri/">LinkedIn</a>
 *
 * @author Mohammad-Haeri.
 */

@Repository
public class RunnersRecordRepository {
//    private static final Logger log = LoggerFactory.getLogger(RunnersRecordRepository.class);


    private final JdbcClient jdbcClient;

    //since I used Named Parameter Jdbc in getById method, I had to use this.
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Value("classpath:sql/run/delete.sql")
    private Resource deleteSql;

    @Value("classpath:sql/run/findAllByLocation.sql")
    private Resource findAllByLocationSql;

    public RunnersRecordRepository(JdbcClient jdbcClient, NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Run> getAll() {
        return jdbcClient.sql("SELECT * FROM Run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> getById(int id) {
        String sql = "SELECT * FROM Run WHERE id = :id";
        MapSqlParameterSource  parameter = new MapSqlParameterSource("id", id);

        DataClassRowMapper<Run> mapper = DataClassRowMapper.newInstance(Run.class);

        List<Run> runs = jdbcTemplate.query(
                sql,
                parameter,
                mapper);

        return runs.stream().findFirst();
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
            Assert.state(result == 1, "Inserting " + run.title() + " With Id of " + run.id() + " has been failed.");
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
        //Using 2nd way here
        String deleteSql;
        try (InputStream in = this.deleteSql.getInputStream()) {
            deleteSql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load SQL for delete", e);
        }


        var deleted = jdbcClient.sql(deleteSql)
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

    public List<Run> findAllByLocation(Location location) {

        String findAllByLocationSql;
        try (InputStream in = this.findAllByLocationSql.getInputStream()) {
            findAllByLocationSql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load SQL for findAllByLocationSql", e);
        }

        return jdbcClient.sql(findAllByLocationSql)
                .param("location", location.name())
                .query(Run.class)
                .list();
    }
}













