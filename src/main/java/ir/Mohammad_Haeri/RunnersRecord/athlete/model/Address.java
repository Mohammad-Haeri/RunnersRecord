package ir.Mohammad_Haeri.RunnersRecord.athlete.model;

public record Address(
        String province,
        String city,
        String zipcode,
        String street,
        Geo geo
) {
}
