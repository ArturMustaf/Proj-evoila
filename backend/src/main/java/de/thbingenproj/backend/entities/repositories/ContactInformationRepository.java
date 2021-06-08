package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.user.contact_information.ContactInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
/**
 * Basic Spring crud repo
 */
public interface ContactInformationRepository extends CrudRepository<ContactInformation, Long> {
}
