package de.thbingenproj.backend.unused.entities.prices.price;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * This is a example implementation of a discount, here we can define a fixed discount price. And the time area
 * where it belongs to.
 */
public class FixedDiscount extends Discount {
    int discount;
    Date startDate;
    Date endDate;
}
