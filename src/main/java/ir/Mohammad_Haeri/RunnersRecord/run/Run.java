package ir.Mohammad_Haeri.RunnersRecord.run;

import jakarta.validation.constraints.*;

public record Run(
        @Positive
        int id,
        @NotEmpty
        String title,
        long startedOn,
        long completedOn,
        @PositiveOrZero
        int meters,
        @Max(100)
        @Min(0)
        int score,
        Location location
) {
        public Run{
                if (completedOn <= startedOn){
                        throw new IllegalArgumentException("Starting time cannot be equal or exceed Completion time");
                }
        }
}
