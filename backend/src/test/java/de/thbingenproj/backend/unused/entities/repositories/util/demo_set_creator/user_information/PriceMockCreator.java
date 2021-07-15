package de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.user_information;

import de.thbingenproj.backend.unused.entities.prices.Price;
import de.thbingenproj.backend.unused.entities.prices.price.PriceType;
import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
/**
 * Mock generator for prices
 */
public class PriceMockCreator {
    @Autowired
    DataCreator dataCreator;
    // 50 * 15 * 8 * 15 =>
    public List<Price> createPrices(Calendar calendar) {
        List<Price> prices = new ArrayList<>();
        int random = dataCreator.getRandom(1, 15);
        Currency currency = Currency.getInstance(Locale.GERMANY);
        PriceType priceType = dataCreator.getBetween(PriceType.HOURLY_PRICE, PriceType.MINUTE_PRICE);
        for (int i = 0; i < random; i++) {
            Price price = new Price();
            price.setPriceInCt(dataCreator.getBetween(1, 2, 4, 1, 15, 20, 100, 200));
            price.setCreated(calendar.getTime());
            calendar.add(Calendar.MINUTE, dataCreator.getRandom(10, 15000));
            price.setCurrency(currency);
            price.setPriceType(priceType);
            price.setExpireDate(calendar.getTime());
            prices.add(price);
        }
        return prices;
    }

}
