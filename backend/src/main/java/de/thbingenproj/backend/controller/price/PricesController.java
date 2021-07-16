package de.thbingenproj.backend.controller.price;

import de.thbingenproj.backend.entity.Prices;
import de.thbingenproj.backend.entity.Resource;
import de.thbingenproj.backend.model.PricesDTO;
import de.thbingenproj.backend.repository.PricesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller which offers functionality for the frontend to read and post prices to the backend
 */

@RestController
public class PricesController {
    @Autowired
    PricesRepository pricesRepository;

    @Autowired
    ModelMapper modelMapper;
    /**
     * creates a new price
     * @param price price objects without id field present
     * @return the saved price object or error
     */
    @PostMapping("/create/prices")
    public Prices postPrice(@RequestBody PricesDTO price) {
        return this.pricesRepository.save(modelMapper.map(price, Prices.class));
    }

    /**
     * updates a current price
     * @param price price objects without id field present
     * @return the updated price object or error
     */
    @PostMapping("/create/resources/{id}/prices")
    public Prices updatePrice(@RequestBody PricesDTO price, @PathVariable String id) {
        var currPrice = pricesRepository.findByResourceIDAndEndDateIsNull(id);
        if (currPrice != null) {
            long currTime = System.currentTimeMillis();
            currPrice.setEndDate(currTime);
            //save updated pirce
            this.pricesRepository.save(currPrice);
        }
        return this.pricesRepository.save(modelMapper.map(price, Prices.class));
    }

    /**
     * @param id id of the price
     * @return the requested price object or null
     */
    @GetMapping("/prices/{id}")
    public Prices getPrice(@PathVariable String id) {
        return this.pricesRepository.findById(id).orElse(null);
    }
    /**
     *
     * @return all price objects or null
     */
    @GetMapping("/admin/prices")
    public List<Prices> getAllPrices() {
        return this.pricesRepository.findAll();
    }
    /**
     * @param resourceID id of the resource
     * @return all prices of the requested resource object or null
     */
    @GetMapping("/resources/{resourceID}/prices")
    public List<Prices> getAllPricesByRessourceId(@PathVariable String resourceID) {
        return this.pricesRepository.findAllByResourceID(resourceID);
    }


}


