package de.test.backend.entities.repositories;

import de.test.backend.entities.user.contact_information.ContactInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
/**
 * Basic Spring crud repo
 */
public interface ContactInformationRepository extends CrudRepository<ContactInformation, Long> {
}
