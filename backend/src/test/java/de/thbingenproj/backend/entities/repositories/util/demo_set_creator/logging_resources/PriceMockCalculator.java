package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.logging_resources;

import de.thbingenproj.backend.entities.prices.Price;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/**
 * Class to create random prices
 * currently deprecated since generation is done elsewhere to correct timeframes
 */
//TODO remove
public class PriceMockCalculator {
    @Autowired
    DataCreator creator;
    Price priceCreator(){
        Price price = new Price();
        price.setPriceInCt(creator.getRandom(500,15000));

        return price;
    }
}
