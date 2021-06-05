package de.test.backend.controller;

import de.test.backend.entities.repositories.ContactInformationRepository;
import de.test.backend.entities.user.contact_information.ContactInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Basic Controller which offers functionality for the frontend to read,put and post contactInformation from the frontend
 */
@RestController
@CrossOrigin()

public class ContactInformationController {
    @Autowired
    ContactInformationRepository contactInformationRepository;

    /**
     * @param id id of the contactInformation
     * @return the requested ContactInformation Object or null
     */
    @GetMapping("create/contactInformations/{id}")
    public ContactInformation getContactInformations(@PathVariable long id) {
        return this.contactInformationRepository.findById(id).orElse(null);
    }

    /**
     * @param id                 id of the contactInformation
     * @param contactInformation Overwritting contactInformation Objects with id field present
     * @return the saved ContactInformation Object or error
     */
    @PutMapping("create/contactInformations/{id}")
    public ContactInformation putContactInformation(@PathVariable long id, @RequestBody ContactInformation contactInformation) {
        contactInformation.setId(id);
        return this.contactInformationRepository.save(contactInformation);
    }

    /**
     * @param contactInformation contactInformation Objects without id field present
     * @return the saved ContactInformation Object or error
     */
    @PostMapping("create/contactInformations")
    public ContactInformation postCustomer(@RequestBody ContactInformation contactInformation) {
        return this.contactInformationRepository.save(contactInformation);
    }
}
