package de.thbingenproj.backend.unused.entities.user.contact_information;

import de.thbingenproj.backend.unused.entities.base.BaseEntity;
import de.thbingenproj.backend.unused.entities.user.contact_information.contact_types.AddressType;
import de.thbingenproj.backend.unused.entities.user.contact_information.contact_types.Country;
import lombok.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Address data of a company or user
 */
public class Address extends BaseEntity {
    /**
     * Street or place
     */
    @Field(name = "address")
    String streetAddress;
    /**
     * plz
     */
    @Field
    String plz;
    /**
     * City
     */
    @Field
    String city;
    /**
     * Country which the address is belonging
     */
    @IndexedEmbedded
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Country country;
    /**
     * Addresstype of the address
     */
    @Enumerated(EnumType.STRING)
    AddressType addressType;
}
