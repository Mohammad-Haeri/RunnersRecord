package ir.Mohammad_Haeri.RunnersRecord.athlete.components;

import ir.Mohammad_Haeri.RunnersRecord.athlete.IAthleteHttpClient;
import ir.Mohammad_Haeri.RunnersRecord.athlete.model.Athlete;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class AthletesRestComponent implements IAthleteHttpClient {

    private final RestClient restClient;

    public AthletesRestComponent(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
    }

    @Override
    public List<Athlete> getAll() {
        return restClient.get()
                .uri("/users")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public Athlete getById(@PathVariable int id) {
                return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(Athlete.class);
    }

}
