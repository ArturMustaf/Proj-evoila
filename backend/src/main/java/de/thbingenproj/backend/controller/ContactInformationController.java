package de.thbingenproj.backend.controller;

import de.thbingenproj.backend.entities.repositories.ContactInformationRepository;
import de.thbingenproj.backend.entities.user.contact_information.ContactInformation;
import de.thbingenproj.backend.entities.user.contact_information.ContactInformationDTO;
import org.modelmapper.ModelMapper;
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
    @Autowired
    ModelMapper modelMapper;
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
    public ContactInformation putContactInformation(@PathVariable long id, @RequestBody ContactInformationDTO contactInformation) {
        contactInformation.setId(id);
        return this.contactInformationRepository.save(modelMapper.map(contactInformation, ContactInformation.class));
    }

    /**
     * @param contactInformation contactInformation Objects without id field present
     * @return the saved ContactInformation Object or error
     */
    @PostMapping("create/contactInformations")
    public ContactInformation postCustomer(@RequestBody ContactInformationDTO contactInformation) {
        return this.contactInformationRepository.save(modelMapper.map(contactInformation, ContactInformation.class));
    }
}
