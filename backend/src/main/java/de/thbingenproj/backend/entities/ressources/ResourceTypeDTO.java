package de.thbingenproj.backend.entities.ressources;

import de.thbingenproj.backend.entities.base.BaseEntity;
import de.thbingenproj.backend.entities.prices.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
// Not allowed generatedToString would run into a StackOverflow
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@Indexed
/**
 * Description of a ResourceType. For example 2GB RAM 2CPU VM
 */
public class ResourceTypeDTO extends BaseEntity {
    /**
     *
     */
    @Field
    String type;
    /**
     * Short description of the ressourcetpye
     */
    @Field
    String shortDescription;
    /**
     * complete description
     */
    @Field
    @Column(length = 4000)
    String fullDescription;
    /**
     * List of prices for running instances.
     * Prices must always have a timeframe defined which is useable for running machines.
     */
    @IndexedEmbedded
    @OneToMany(cascade = CascadeType.ALL)
    List<Price> price;
    // TODO Add stopped support => Further the results in getResourceRuntimePairs in CostsController
    /**
     * Offline Price support / offline prices for ressource => currently not implemented
     */
    @IndexedEmbedded
    @OneToMany(cascade = CascadeType.ALL)
    List<Price> offlinePrice;
    /**
     * If there are resource changes for example in description or resources given a next Resourcetype can be defined
     * to ensure the correct usage and price calculation without changing the base resource type
     */
    @ManyToOne
    ResourceTypeDTO nextResource;
    /**
     * unit type of the resource for example piece, GB (for traffic) or seconds (for runtimes)
     */
    @Field
    String unitType;

    @Override
    public String toString() {
        return shortDescription + ": " + price;
    }
}
