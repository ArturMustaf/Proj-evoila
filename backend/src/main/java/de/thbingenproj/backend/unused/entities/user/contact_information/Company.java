package de.thbingenproj.backend.unused.entities.user.contact_information;

import de.thbingenproj.backend.unused.entities.base.BaseEntity;
import de.thbingenproj.backend.unused.entities.prices.Project;
import de.thbingenproj.backend.unused.entities.user.contact_information.contact_types.CompanyType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Company informations
 */
public class Company extends BaseEntity {
    /**
     * Company type private Company, GMBH or similar
     */
    @Enumerated(EnumType.STRING)
    CompanyType type;
    /**
     * Name of the company
     */
    String companyName;
    /**
     * Projects belonging to the company
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Project> projects;
    /**
     * contact information of the company / billing addresses
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<ContactInformation> contactInformation;

}
