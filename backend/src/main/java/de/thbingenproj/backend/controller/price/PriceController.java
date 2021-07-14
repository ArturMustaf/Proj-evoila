package de.thbingenproj.backend.controller.price;

import de.thbingenproj.backend.entity.Price;
import de.thbingenproj.backend.model.PriceDTO;
import de.thbingenproj.backend.repository.PriceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PriceController {
    @Autowired
    PriceRepository priceRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/admin/create/prices")
    public Price postPrice(@RequestBody PriceDTO price) {
        return this.priceRepository.save(modelMapper.map(price, Price.class));
    }

    @PostMapping("/admin/create/resources/{id}/prices")
    public Price updatePrice(@RequestBody PriceDTO price, @PathVariable String id){
       var currPrice=priceRepository.findByResourceIDAndEndIsNull(id);
       long currTime= System.currentTimeMillis();
        currPrice.setEnd(currTime);
        //save updated pirce
        this.priceRepository.save(currPrice);
        return this.priceRepository.save(modelMapper.map(price, Price.class));
    }


    @GetMapping("/prices/{id}")
    public Price getPrice(@PathVariable String id) {
        return this.priceRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/prices")
    public List<Price> getAllPrices() {
        return this.priceRepository.findAll();
    }
    @GetMapping("/ressources/{resourceId}/prices")
    public  List<Price> getAllPricesByRessourceId(@PathVariable String resourceID) {
        return this.priceRepository.findAllByResourceID(resourceID);
    }
}


