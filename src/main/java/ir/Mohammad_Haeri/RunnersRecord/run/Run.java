package ir.Mohammad_Haeri.RunnersRecord.run;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record Run(
        @Positive
        @Id
        Integer id,
        @NotEmpty
        String title,
        LocalDateTime startedOn,
        LocalDateTime completedOn,
        @PositiveOrZero
        Integer meters,
        @Max(100)
        @Min(0)
        Integer score,
        Location location
) {
        public Run{
                if (completedOn.toEpochSecond(ZoneOffset.UTC) <= startedOn.toEpochSecond(ZoneOffset.UTC)){
                        throw new IllegalArgumentException("Starting time cannot be equal or exceed Completion time");
                }
        }
}
