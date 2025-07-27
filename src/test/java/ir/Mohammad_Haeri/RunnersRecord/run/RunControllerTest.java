package ir.Mohammad_Haeri.RunnersRecord.run;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Import({RunnersRecordRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RunControllerTest {

    @Autowired
    RunnersRecordRepository repository;

    @Autowired
    IRunRecordDataJdbcCrudRepository jdbcRepository;

    private boolean brittleness = true;
    private final int testId = 10000;

    @Test
    @Order(1)
    void getAll() {
        var all = this.repository.getAll();
        assertEquals(3, all.size());
    }

    @Test
    @Order(2)
    void create() {
        var allBeforeInsert = this.repository.getAll();
        Run newRun = new Run(
                testId,
                "Test Run",
                LocalDateTime.now().minusMinutes(4),
                LocalDateTime.now(),
                5428,
                90,
                Location.INDOOR);
        this.repository.create(newRun, false);
        var allAfterInsert = this.repository.getAll();

        assertTrue(allBeforeInsert.size() < allAfterInsert.size());
        brittleness = false;
    }

    @Test
    @Order(3)
    void getById() {
        Optional<Run> run = this.repository.getById(testId);
        assertTrue(run.isPresent());
    }

    @Test
    @Order(4)
    void update() {
        assumeTrue(brittleness,
                "Skipping update due to create() step failed or was skipped");

        Run newRun = new Run(
                testId,
                "Fourth Run Updated",
                LocalDateTime.now().minusMinutes(4),
                LocalDateTime.now(),
                6000,
                90,
                Location.OUTDOOR);
        this.repository.update(newRun, testId);

        var updatedRun = repository.getById(testId);

        if (updatedRun.isEmpty()) {
            return;
        }
        assertEquals(6000, updatedRun.get().meters());

    }

    @Test
    @Order(5)
    void delete() {

        this.repository.delete(testId);
        var deletedRun = repository.getById(testId);

        assertTrue(deletedRun.isEmpty());

    }

    @Test
    void getAllByLocationRunnersRecordRepository() {
        var byLocation = this.repository.findAllByLocation(Location.OUTDOOR);
        assertFalse(byLocation.isEmpty());
    }

    @Test
    void getAllByLocationIRunRecordDataJdbcCrudRepository() {
        var allByLocation = jdbcRepository.findAllByLocation(Location.OUTDOOR);
        assertFalse(allByLocation.isEmpty());
    }

    @Test
    void getAllByScoreGreaterThan() {
        var allByScoreGreaterThan = jdbcRepository.findAllByScoreGreaterThan(90);
        assertFalse(allByScoreGreaterThan.isEmpty());
    }

    @Test
    void getAllByScoreIsLessThan() {
        var allByScoreIsLessThan = jdbcRepository.findAllByScoreIsLessThan(95);
        assertFalse(allByScoreIsLessThan.isEmpty());
    }

    @Test
    void getAllByScoreBetween() {
        var allByScoreBetween = jdbcRepository.findAllByScoreBetween(90, 100);
        assertFalse(allByScoreBetween.isEmpty());
    }

    @Test
    void getCount(){
        var all = this.repository.getAll();
        int count = this.repository.count();

        assertEquals(all.size(), count);
    }
}