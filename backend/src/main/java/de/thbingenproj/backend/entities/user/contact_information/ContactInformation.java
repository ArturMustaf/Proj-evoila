package de.test.backend.entities.user.contact_information;

import de.test.backend.entities.base.BaseEntity;
import de.test.backend.entities.user.contact_information.contact_types.ContactInformationType;
import lombok.*;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@Indexed
@NoArgsConstructor

@AllArgsConstructor
/**
 * Contact informations of a user or a company which embeddeds address, mail, telefon-number informations
 */
public class ContactInformation extends BaseEntity {
    @IndexedEmbedded
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Address address;
    @Enumerated(EnumType.STRING)
    ContactInformationType type;
    @Field
    String email;
    @IndexedEmbedded
    @ContainedIn
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<PhoneNumber> numbers;
}
