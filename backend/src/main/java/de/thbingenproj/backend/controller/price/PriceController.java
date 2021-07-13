package de.thbingenproj.backend.controller.price;

import de.thbingenproj.backend.entity.Organization;
import de.thbingenproj.backend.entity.Price;
import de.thbingenproj.backend.model.PriceDTO;
import de.thbingenproj.backend.repository.PriceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PriceController {
    @Autowired
    PriceRepository priceRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create/prices")
    public Price postPrice(@RequestBody PriceDTO price) {
        Date date=new Date();
        return this.priceRepository.save(modelMapper.map(price, Price.class));
    }

    @GetMapping("/prices/{id}")
    public Price getPrice(@PathVariable String id) {
        return this.priceRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/Prices")
    public List<Price> getAllPrices() {
        return this.priceRepository.findAll();
    }
    @GetMapping("/vdcs/{id}/prices")
    public List<Price> getAllVDCPrices(@PathVariable String vdcID){
        return this.priceRepository.findAllByVdcID(vdcID);
    }
}


