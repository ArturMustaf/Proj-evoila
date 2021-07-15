package de.thbingenproj.backend.unused.entities.user.contact_information.contact_types;

import de.thbingenproj.backend.unused.entities.base.BaseEntity;
import lombok.*;
import org.hibernate.search.annotations.Field;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Country of the user
 */
public class Country extends BaseEntity {
    /**
     * Country fullname
     */
    @Field
    String countryName;
    /**
     * country short name
     */
    String shortDescription;
}
