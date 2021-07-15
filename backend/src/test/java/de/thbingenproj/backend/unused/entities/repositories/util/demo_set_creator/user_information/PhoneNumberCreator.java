package de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.user_information;

import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataTypes;
import de.thbingenproj.backend.unused.entities.user.contact_information.PhoneNumber;
import de.thbingenproj.backend.unused.entities.user.contact_information.contact_types.PhoneNumberType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/**
 * Mock creation of phone-numbers
 */
public class PhoneNumberCreator {
    @Autowired
    DataCreator dataCreator;

    PhoneNumber createPhoneNumber() {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber(dataCreator.getData(DataTypes.PHONE_NUMBER));
        phoneNumber.setType(dataCreator.getBetween(PhoneNumberType.MOBILE, PhoneNumberType.PHONE));
        return new PhoneNumber();
    }
}
