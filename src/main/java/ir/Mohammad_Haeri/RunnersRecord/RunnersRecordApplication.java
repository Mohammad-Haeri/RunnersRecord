package ir.Mohammad_Haeri.RunnersRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunnersRecordApplication {

    private static  final Logger log = LoggerFactory.getLogger(RunnersRecordApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RunnersRecordApplication.class, args);
    }

}
