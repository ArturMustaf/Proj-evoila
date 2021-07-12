package de.thbingenproj.backend.controller.price;

import de.thbingenproj.backend.entity.Price;
import de.thbingenproj.backend.model.PriceDTO;
import de.thbingenproj.backend.repository.PriceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {
    @Autowired
    PriceRepository priceRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create/prices")
    public Price postPrice(@RequestBody PriceDTO price){
        return this.priceRepository.save(modelMapper.map(price, Price.class));
    }


}
