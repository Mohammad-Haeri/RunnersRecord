package ir.Mohammad_Haeri.RunnersRecord.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunnersRecordRepository repository;
    private final IRunRecordDataJdbcCrudRepository dataJdbcCrudRepository;

    public RunController(RunnersRecordRepository repository, IRunRecordDataJdbcCrudRepository dataJdbcCrudRepository) {
        this.repository = repository;
        this.dataJdbcCrudRepository = dataJdbcCrudRepository;
    }

    @GetMapping("/test")
    String connectionTest() {
        return "Connection is Ok";
    }

    @GetMapping("/getAll")
    public List<Run> getAll() {
        return this.repository.getAll();
    }

    @GetMapping("/{id}")
    public Run getById(@PathVariable int id) {
        var optional = this.repository.getById(id);
        return optional.orElse(null);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void create(@Valid @RequestBody Run run) {
        this.repository.create(run, false);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable int id) {
        this.repository.update(run, id);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.repository.delete(id);
    }

    @GetMapping("/byLocation/{location}")
    public List<Run> getAllByLocation(@PathVariable String location){
        if (location.equals(Location.OUTDOOR.name())){
            return this.dataJdbcCrudRepository.findAllByLocation(Location.OUTDOOR);
        } else if (location.equals(Location.INDOOR.name())) {
            return this.dataJdbcCrudRepository.findAllByLocation(Location.INDOOR);
        }

        return null;

    }

    @GetMapping("/greaterThan/{score}")
    public List<Run> getAllByScoreGreaterThan(@PathVariable int score){
        return this.dataJdbcCrudRepository.findAllByScoreGreaterThan(score);

    }

    @GetMapping("/lessThan/{score}")
    public List<Run> getAllByScoreIsLessThan(@PathVariable int score){
        return this.dataJdbcCrudRepository.findAllByScoreIsLessThan(score);

    }

    @GetMapping("/between/{min}/{max}")
    public List<Run> getAllByScoreBetween(@PathVariable int min, @PathVariable int max){
        return this.dataJdbcCrudRepository.findAllByScoreBetween(min, max);

    }




}
