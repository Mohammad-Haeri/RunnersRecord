package ir.Mohammad_Haeri.RunnersRecord;

import ir.Mohammad_Haeri.RunnersRecord.run.Location;
import ir.Mohammad_Haeri.RunnersRecord.run.Run;
import ir.Mohammad_Haeri.RunnersRecord.run.RunnersRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RunnersRecordApplication {

    private static  final Logger log = LoggerFactory.getLogger(RunnersRecordApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RunnersRecordApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RunnersRecordRepository repository){
        return args -> {
            List<Run> runs = new ArrayList<>();
            runs.add(new Run(1,
                    "First Run",
                    LocalDateTime.now().minusHours(6).minusMinutes(2),
                    LocalDateTime.now().minusHours(6),
                    3352,
                    98,
                    Location.OUTDOOR));
            runs.add(new Run(2,
                    "Second Run",
                    LocalDateTime.now().minusHours(3).minusMinutes(3),
                    LocalDateTime.now().minusHours(3),
                    5603,
                    100,
                    Location.OUTDOOR));
            runs.add(new Run(3,
                    "Third Run",
                    LocalDateTime.now().minusMinutes(4),
                    LocalDateTime.now(),
                    5428,
                    90,
                    Location.OUTDOOR));
            repository.createAll(runs);
        };

    }

}
