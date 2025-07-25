package ir.Mohammad_Haeri.RunnersRecord.athlete;

import ir.Mohammad_Haeri.RunnersRecord.athlete.model.Athlete;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface IAthleteHttpClient {

    @GetExchange("/athletes")
    public List<Athlete> getAll();

    @GetExchange("/athletes/{id}")
    public Athlete getById(@PathVariable int id);
}
