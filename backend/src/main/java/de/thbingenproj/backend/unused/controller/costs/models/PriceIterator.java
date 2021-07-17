package de.thbingenproj.backend.unused.controller.costs.models;

import de.thbingenproj.backend.unused.entities.prices.Price;
import de.thbingenproj.backend.unused.entities.ressources.ResourceType;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterator through Prices. To allow following RessourceTypes and keep the timeframes correctly
 */
public class PriceIterator implements Iterator<Price> {
    List<Price> prices;
    ResourceType type;
    int i = 0;

    /**
     * @param resourceType takes ResourceType over which should be iterated
     */
    public PriceIterator(ResourceType resourceType) {
        this.prices = resourceType.getPrice();
        type = resourceType;


    }

    /**
     * @return wether a next price is available
     */
    @Override
    public boolean hasNext() {
        return i < this.prices.size() ||
                (type.getNextResource() != null &&
                        type.getNextResource().getPrice() != null &&
                        !type.getNextResource().getPrice().isEmpty());
    }

    /**
     * @return gives next price
     * @throws IndexOutOfBoundsException if iterating and there is no next price
     */
    @Override
    public Price next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException("Cant iterate further - completete price history is iterated");
        }
        if (i >= prices.size()) {
            this.type = this.type.getNextResource();
            this.prices = this.type.getPrice();
            this.i = 0;
        }
        return this.prices.get(i++);
    }


}
