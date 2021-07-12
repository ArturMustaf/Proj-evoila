package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PriceRepository extends CrudRepository <Price,Long> {
    ArrayList<Price> findAll();
    ArrayList<Price>findAllByVdcID(String vdcID);

}
