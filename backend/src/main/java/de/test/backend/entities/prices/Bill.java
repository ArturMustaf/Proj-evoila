package de.test.backend.entities.prices;


import de.test.backend.entities.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

// TODO Deprecated => Generated through code
/**
 Currently bills would be calculated through code. This is depreacted.
 The previous idea would be to generate bills through batch jobs monthly to keep the bills consistent

 */
public class Bill extends BaseEntity {
    Date startDate;
    Date endDate;
    @Lob
    private byte[] data;
}
