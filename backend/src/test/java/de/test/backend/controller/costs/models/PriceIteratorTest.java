package de.test.backend.controller.costs.models;

import de.test.backend.entities.prices.Price;
import de.test.backend.entities.ressources.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/**
 * Price Iterator unit test
 */
class PriceIteratorTest {
    ResourceType type;

    @BeforeAll
    void setup() {
        type = new ResourceType();
        List<Price> prices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            prices.add(new Price());
        }
        type.setPrice(prices);
        ResourceType type2 = new ResourceType();
        type.setNextResource(type2);
        type2.setPrice(prices);
        ResourceType type3 = new ResourceType();
        type2.setNextResource(type3);
    }


    @Test
    void test_iter() {
        PriceIterator iter = new PriceIterator(this.type);
        for (int i = 0; i < 20; i++) {
            boolean hasNext = iter.hasNext();
            Assertions.assertTrue(hasNext);
            Assertions.assertNotNull(iter.next());

        }
        boolean hasNext = iter.hasNext();
        Assertions.assertFalse(hasNext);

        Assertions.assertThrows(IndexOutOfBoundsException.class, iter::next);


    }


}
