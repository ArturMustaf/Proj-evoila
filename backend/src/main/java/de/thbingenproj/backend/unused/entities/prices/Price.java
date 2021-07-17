package de.thbingenproj.backend.unused.entities.prices;

import de.thbingenproj.backend.unused.controller.costs.models.Pair;
import de.thbingenproj.backend.unused.entities.base.BaseEntity;
import de.thbingenproj.backend.unused.entities.prices.price.PriceType;
import lombok.*;
import org.hibernate.search.annotations.Field;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data

@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Price object in database.
 */
public class Price extends BaseEntity {
    /**
     * Price in cents
     */
    @Field(name = "price")
    long priceInCt;
    /**
     * Price type of a object. Defined through enum PriceType
     */
    @Enumerated(EnumType.STRING)
    PriceType priceType;
    /**
     * Currency of the price
     */
    Currency currency;
    /**
     * Date of the price where this object becomes active
     */
    Date created;
    /**
     * Date of the price where the price is expired
     */
    Date expireDate;

    /**
     * supportes price entity switches where second price entity has
     *
     * @param from timerange from
     * @param to   timerange to
     * @return tuple timeinmillis and lastbilled date to ensure no double billing in splitting price times
     */

    public Pair<Long, Long> getTimeInTimeRange(Date from, Date to) {

        long fromTime = from.getTime();
        long toTime = to.getTime();
        var expire = expireDate;
        if (expire == null) {
            var cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 200);
            expire = cal.getTime();
        }
        // not in timeframe
        if (fromTime >= expire.getTime())
            return new Pair<>(0L, from.getTime());

        // set time in the frame up since other timeframe is outside of the billing period
        if (fromTime < created.getTime()) {
            fromTime = created.getTime();
        }

        // to runs over billing period
        if (toTime >= expire.getTime()) {
            return new Pair<>(expire.getTime() - fromTime, expire.getTime());
        }

        return new Pair<>(toTime - fromTime, toTime);
    }

}
