package de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.user_information;

import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataTypes;
import de.thbingenproj.backend.unused.entities.user.contact_information.contact_types.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
/**
 * Creation of countries
 */
// TODO save objects prior to ensure not dozens of same countries
public class CountryMockCreator {
    @Autowired
    DataCreator creator;
    Map<String, Country> countries = new HashMap<>();

    Country createCountry() {
        Country country = new Country();
        String countryName = creator.getData(DataTypes.COUNTRY);
        if (countries.containsKey(countryName))
            return countries.get(countryName);
        country.setCountryName(countryName);
        country.setShortDescription(countryName.substring(0, 2));

        return country;
    }
}
