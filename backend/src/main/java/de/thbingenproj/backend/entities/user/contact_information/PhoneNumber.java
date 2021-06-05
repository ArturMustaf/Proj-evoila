package de.test.backend.entities.user.contact_information;

import de.test.backend.entities.base.BaseEntity;
import de.test.backend.entities.user.contact_information.contact_types.PhoneNumberType;
import lombok.*;
import org.hibernate.search.annotations.Field;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Telefon number definition
 */
public class PhoneNumber extends BaseEntity {
    @Field
    String number;
    @Enumerated(EnumType.STRING)
    PhoneNumberType type;
}
