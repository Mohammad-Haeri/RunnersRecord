package ir.Mohammad_Haeri.RunnersRecord.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunnersRecordRepository repository;

    public RunController(RunnersRecordRepository repository) {
        this.repository = repository;
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


}
