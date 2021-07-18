package com.crawler.repository;

import com.crawler.entity.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PriceRepository extends CrudRepository <Price, String> {
    ArrayList<Price> findAll();
    ArrayList<Price>findAllByVdcID(String vdcID);

}
