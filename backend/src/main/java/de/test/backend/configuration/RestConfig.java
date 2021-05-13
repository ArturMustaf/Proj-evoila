package de.test.backend.configuration;

import de.test.backend.entities.prices.Project;
import de.test.backend.entities.ressources.Event;
import de.test.backend.entities.ressources.ResourceLog;
import de.test.backend.entities.ressources.ResourceType;
import de.test.backend.entities.user.Customer;
import de.test.backend.entities.user.contact_information.Address;
import de.test.backend.entities.user.contact_information.ContactInformation;
import de.test.backend.entities.user.contact_information.PhoneNumber;
import de.test.backend.entities.user.contact_information.contact_types.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Component to expose IDs for Datafields, they are used for the admin-portal only, to specify which element should be
 * changed
 */
@Component
public class RestConfig implements RepositoryRestConfigurer {
    @Autowired
    private EntityManager entityManager;

    /**
     * Exposes Ids for specified classes
     * @param config Autoinjected from Spring
     *
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Customer.class);
        config.exposeIdsFor(ContactInformation.class);
        config.exposeIdsFor(Project.class);
        config.exposeIdsFor(ResourceLog.class);
        config.exposeIdsFor(ResourceType.class);
        config.exposeIdsFor(Address.class);
        config.exposeIdsFor(Country.class);
        config.exposeIdsFor(PhoneNumber.class);
        config.exposeIdsFor(Event.class);

    }
}
