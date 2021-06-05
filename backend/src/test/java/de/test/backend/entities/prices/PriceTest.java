package de.test.backend.entities.prices;

import de.test.backend.controller.costs.models.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

class PriceTest {

    @Test
    void getTimeInTimeRange() {
        Price price = new Price();
        price.setPrice(1L);
        price.setCreated(Date.from(Instant.ofEpochSecond(0)));
        price.setExpireDate(Date.from(Instant.ofEpochSecond(3600)));

        Pair<Long, Long> time = price.getTimeInTimeRange(Date.from(Instant.ofEpochSecond(0)), Date.from(Instant.ofEpochSecond(3600)));
        Assertions.assertEquals(3600000, (long) time.elm1);
        Assertions.assertEquals(3600000, (long) time.elm2);
        time = price.getTimeInTimeRange(Date.from(Instant.ofEpochSecond(1800)), Date.from(Instant.ofEpochSecond(3600)));
        Assertions.assertEquals(1800000, (long) time.elm1);
        Assertions.assertEquals(3600000, (long) time.elm2);
        time = price.getTimeInTimeRange(Date.from(Instant.ofEpochSecond(-100)), Date.from(Instant.ofEpochSecond(3600)));
        Assertions.assertEquals(3600000, (long) time.elm1);
        Assertions.assertEquals(3600000, (long) time.elm2);
        time = price.getTimeInTimeRange(Date.from(Instant.ofEpochSecond(3600)), Date.from(Instant.ofEpochSecond(3600)));
        Assertions.assertEquals(0, (long) time.elm1);
        Assertions.assertEquals(3600000, (long) time.elm2);
        time = price.getTimeInTimeRange(Date.from(Instant.ofEpochSecond(3700)), Date.from(Instant.ofEpochSecond(3600)));
        Assertions.assertEquals(0, (long) time.elm1);
        Assertions.assertEquals(3700000, (long) time.elm2);
        time = price.getTimeInTimeRange(Date.from(Instant.ofEpochSecond(3600)), Date.from(Instant.ofEpochSecond(3600)));
        Assertions.assertEquals(0, (long) time.elm1);
        Assertions.assertEquals(3600000, (long) time.elm2);
        time = price.getTimeInTimeRange(Date.from(Instant.ofEpochSecond(3400)), Date.from(Instant.ofEpochSecond(4600)));
        Assertions.assertEquals(200000, (long) time.elm1);
        Assertions.assertEquals(3600000, (long) time.elm2);
    }
}
