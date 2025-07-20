package ir.Mohammad_Haeri.RunnersRecord.run;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record Run(
        @Positive
        int id,
        @NotEmpty
        String title,
        LocalDateTime startedOn,
        LocalDateTime completedOn,
        @PositiveOrZero
        int meters,
        @Max(100)
        @Min(0)
        int score,
        Location location
) {
        public Run{
                if (completedOn.toEpochSecond(ZoneOffset.UTC) <= startedOn.toEpochSecond(ZoneOffset.UTC)){
                        throw new IllegalArgumentException("Starting time cannot be equal or exceed Completion time");
                }
        }
}
