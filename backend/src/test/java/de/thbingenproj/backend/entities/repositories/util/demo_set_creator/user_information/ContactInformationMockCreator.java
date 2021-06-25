package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information;

import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.dataset.DataTypes;
import de.thbingenproj.backend.entities.user.contact_information.ContactInformation;
import de.thbingenproj.backend.entities.user.contact_information.contact_types.ContactInformationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
/**
 * Mock for contactinformations
 */
public class ContactInformationMockCreator {
    @Autowired
    DataCreator dataCreator;
    @Autowired
    AddressCreator addressCreator;
    @Autowired
    PhoneNumberCreator phoneNumberCreator;

    public ContactInformation createContactInformation() {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail(dataCreator.getData(DataTypes.EMAIL));
        contactInformation.setType(dataCreator.getBetween(ContactInformationType.EMERGENCY_CONTACT, ContactInformationType.MAIN_CONTACT));
        contactInformation.setAddress(addressCreator.createAddress());
        contactInformation.setNumbers(new ArrayList<>());
        for (int i = 0; i < dataCreator.getRandom(0, 2); i++) {
            contactInformation.getNumbers().add(phoneNumberCreator.createPhoneNumber());
        }

        return contactInformation;
    }

}
