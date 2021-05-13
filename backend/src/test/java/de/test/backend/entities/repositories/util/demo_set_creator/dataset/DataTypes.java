package de.test.backend.entities.repositories.util.demo_set_creator.dataset;

/**
 * Helper for some "real" data entries out of a csv
 */
public enum DataTypes {
    PHONE_NUMBER("telefon_number"),
    COMPANY_NAME("company_name"),
    STEUER_ID("companySteuerId"),
    CITY("city"),
    COUNTRY("country"),
    PLZ("plz"),
    ADDRESS("address"),
    FIRST_NAME("firstname"),
    LAST_NAME("lastname"),
    EMAIL("email");


    private final String name;

    public String getName() {
        return name;
    }

    DataTypes(String name) {
        this.name = name;
    }
}
