package de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.user_information;

import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataTypes;
import de.thbingenproj.backend.unused.entities.user.contact_information.Company;
import de.thbingenproj.backend.unused.entities.user.contact_information.contact_types.CompanyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
/**
 * Mock data for company names
 */
public class CompanyMockCreator {
    @Autowired
    DataCreator creator;

    @Autowired
    ContactInformationMockCreator contactInformationMockCreator;
    @Autowired
    ProjectMockCreator projectMockCreator;

    public Company generateCompany() {
        Company company = new Company();

        company.setCompanyName(creator.getData(DataTypes.COMPANY_NAME));

        company.setType(creator.getBetween(CompanyType.COMPANY, CompanyType.INDIVIDUUM));
        company.setContactInformation(new ArrayList<>());
        for (int i = 0; i < creator.getRandom(1, 2); i++) {
            company.getContactInformation().add(contactInformationMockCreator.createContactInformation());
        }
        company.setProjects(new ArrayList<>());
        for(int i = 0; i< creator.getRandom(1,5); i++){
            company.getProjects().add(projectMockCreator.createProject());
        }

        return company;
    }
}
