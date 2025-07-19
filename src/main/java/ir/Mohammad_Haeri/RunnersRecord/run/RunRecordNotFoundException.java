package ir.Mohammad_Haeri.RunnersRecord.run;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RunRecordNotFoundException extends RuntimeException {
    public RunRecordNotFoundException(){
        super("Run Record Not Founded");
    }
}
