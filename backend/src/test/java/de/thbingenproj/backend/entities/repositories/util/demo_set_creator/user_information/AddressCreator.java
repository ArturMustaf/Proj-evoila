package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information;

import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.dataset.DataTypes;
import de.thbingenproj.backend.entities.user.contact_information.Address;
import de.thbingenproj.backend.entities.user.contact_information.contact_types.AddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/**
 * Mock data for addresses
 */
public class AddressCreator {
    @Autowired
    private DataCreator creator;
    @Autowired
    CountryMockCreator countryMockCreator;

    Address createAddress() {
        Address address = new Address();
        address.setAddressType(creator.getBetween(AddressType.BILLING_ADDRESS, AddressType.CONTACT_ADDRESS));
        address.setPlz(creator.getData(DataTypes.PLZ));
        address.setAddress(creator.getData(DataTypes.ADDRESS));
        address.setCity(creator.getData(DataTypes.CITY));
        address.setCountry(countryMockCreator.createCountry());


        return address;
    }

}
