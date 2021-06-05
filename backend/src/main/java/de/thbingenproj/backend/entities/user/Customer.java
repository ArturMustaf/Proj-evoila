package de.thbingenproj.backend.entities.user;

import de.thbingenproj.backend.entities.base.BaseEntity;
import de.thbingenproj.backend.entities.prices.Project;
import de.thbingenproj.backend.entities.rights.Permission;
import de.thbingenproj.backend.entities.user.contact_information.ContactInformation;
import lombok.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@ToString
@Table(indexes = {@Index(unique = true, columnList = "userId", name = "IDX_CUSTOMER_ID")})
@NoArgsConstructor
@AllArgsConstructor
@Indexed(index = "idx_customer")
/**
 * Customer / User information => every user of the plattform has a customer account
 * they only differ in the rights they have and the permissions of which projects they can see
 */
public class Customer extends BaseEntity {
    @Field(store = Store.YES)
    @Field(name = "fullName")
    String name;
    @Field(store = Store.YES)
    @Field(name = "fullName")
    String surname;
    String userId;

    @IndexedEmbedded
    @ManyToOne(cascade = CascadeType.ALL)
    ContactInformation contactInformation;
    /**
     * Idea was to use this to map Customer to Projects later approach was to only give projects as a list
     * The addition of such a field in future to allow custom user permissions per project can then be done by implementing
     * permissions and creating projections for all fields which should be occluded
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Permission> permissions;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Project> projects = new ArrayList<>();
}
