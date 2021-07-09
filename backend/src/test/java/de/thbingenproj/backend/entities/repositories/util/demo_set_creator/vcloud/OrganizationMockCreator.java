package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud;

import de.thbingenproj.backend.entity.OrgNetwork;
import de.thbingenproj.backend.entity.Organization;
import org.springframework.stereotype.Service;

@Service
public class OrganizationMockCreator {

    //ToDo set missing properties for mock object
    public Organization createOrganization() {
        Organization organization = new Organization();
        organization.setId("urn:vcloud:org:7b832bc5-3d65-45a2-8d35-da28388ab80a");
        organization.setHref("https://vcloud.example.com/api/org/7b832bc5-3d65-45a2-8d35-da28388ab80a");
        organization.setDescription("Default organization description");
        organization.setName("Default");
        organization.setFullName("Default Organization");
        organization.setIsEnabled(Boolean.TRUE.toString());
        return organization;
    }
}
