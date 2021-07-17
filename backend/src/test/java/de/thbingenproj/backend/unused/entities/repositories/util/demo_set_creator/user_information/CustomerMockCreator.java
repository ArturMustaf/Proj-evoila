package de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.user_information;

import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataTypes;
import de.thbingenproj.backend.unused.entities.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
/**
 * Mock creation of customers
 */
public class CustomerMockCreator {
    @Autowired
    DataCreator dataCreator;
    @Autowired
    ContactInformationMockCreator contactInformationMockCreator;

    public Customer createUser() {
        Customer customer = new Customer();
        customer.setContactInformation(contactInformationMockCreator.createContactInformation());
        customer.setName(dataCreator.getData(DataTypes.FIRST_NAME));
        customer.setSurname(dataCreator.getData(DataTypes.LAST_NAME));
        customer.setPermissions(new ArrayList<>());
        return customer;
    }
}
