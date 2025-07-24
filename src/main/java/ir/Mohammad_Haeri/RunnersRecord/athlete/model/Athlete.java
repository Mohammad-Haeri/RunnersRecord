package ir.Mohammad_Haeri.RunnersRecord.athlete.model;

public record Athlete(
        Integer id,
        String name,
        String username,
        String email,
        Address address,
        String phone
) {
}
