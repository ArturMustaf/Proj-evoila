package de.thbingenproj.backend.controller.price;

import de.thbingenproj.backend.entity.Prices;
import de.thbingenproj.backend.model.PricesDTO;
import de.thbingenproj.backend.repository.PricesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PricesController {
    @Autowired
    PricesRepository pricesRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create/prices")
    public Prices postPrice(@RequestBody PricesDTO price) {
        return this.pricesRepository.save(modelMapper.map(price, Prices.class));
    }

    @PostMapping("/create/resources/{id}/prices")
    public Prices updatePrice(@RequestBody PricesDTO price, @PathVariable String id) {
        var currPrice = pricesRepository.findByResourceIDAndEndIsNull(id);
        long currTime = System.currentTimeMillis();
        currPrice.setEnd(currTime);
        //save updated pirce
        this.pricesRepository.save(currPrice);
        return this.pricesRepository.save(modelMapper.map(price, Prices.class));
    }


    @GetMapping("/prices/{id}")
    public Prices getPrice(@PathVariable String id) {
        return this.pricesRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/prices")
    public List<Prices> getAllPrices() {
        return this.pricesRepository.findAll();
    }

    @GetMapping("/ressources/{resourceID}/prices")
    public List<Prices> getAllPricesByRessourceId(@PathVariable String resourceID) {
        return this.pricesRepository.findAllByResourceID(resourceID);
    }
}


