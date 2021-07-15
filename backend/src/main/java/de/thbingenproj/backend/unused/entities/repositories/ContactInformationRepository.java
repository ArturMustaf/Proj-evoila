package de.thbingenproj.backend.unused.entities.repositories;

import de.thbingenproj.backend.unused.entities.user.contact_information.ContactInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
/**
 * Basic Spring crud repo
 */
public interface ContactInformationRepository extends CrudRepository<ContactInformation, Long> {
    List<ContactInformation> findAll();
}
