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
     * @param id id of the resource
     * @return all prices of the requested resource object or null
     */
    @GetMapping("/resources/{id}/prices")
    public List<Prices> getAllPricesByRessourceId(@PathVariable String id) {
        return this.pricesRepository.findAllByResourceID(id);
    }
    /**
     * returns all prices used or in use of an organization
     * @param  id of the organization
     * @return Prices objects
     */
    @GetMapping("/prices/organizations/{id}")
    public List<Prices> getALlPricesByOrgID(@PathVariable String id){
        return this.pricesRepository.findAllByOrgID(id);
    }

    /**
     * returns all Prices used or in use of a vdc
     * @param  id of the organization
     * @return Prices objects of vdc
     */
    @GetMapping("/vdcs/prices/{id}")
    public List<Prices> getALlPricesByVdcID(@PathVariable String id) {
        return this.pricesRepository.findAllByVdcID(id);
    }

    /**
     * returns all Prices used or in use of an organization in a specific time interval
     * @param  id of the organization
     * @param from start date
     * @param until end date
     * @return Prices objects of vdc
     */
    @GetMapping("/organizations/prices/{id}/{from}/{until}")
    public List<Prices>getPricesByOrgIdInGivenTime(@PathVariable String id, @PathVariable long from, @PathVariable long until){
    //  alternative but without query in repository
    /*
     var allPrices=getALlPricesByOrgID(id);
        allPrices.stream()
                .filter(prices -> (prices.getStartDate()<until&&  prices.getEndDate()> from )
                        || (prices.getStartDate()>from && prices.getStartDate()<until)
                        ||(prices.getStartDate()<until && prices.getEndDate()==0))
                .collect(Collectors.toList());

        return allPrices;
         */
     return this.pricesRepository.findAllByOrgIDAndStartDateIsBetweenOrEndDateIsBetweenOrEndDateBeforeAndEndDateIsNull(id, from,until,from,until,until);


    }
    /**
     * returns all prices used or in use of a vdc in a specific time interval
     * @param id of the vdc
     * @param from start date
     * @param until end date
     * @return prices objects of vdc
     */

    @GetMapping("/vdcs/prices/{id}/{from}/{until}")
    public List<Prices>getPricesByVdcIdInGivenTime(@PathVariable String id, @PathVariable long from, @PathVariable long until){
        //  alternative but without query in repository
      /*
        var allPrices=getALlPricesByOrgID(id);
        allPrices.stream()
                .filter(prices -> (prices.getStartDate()<until&&  prices.getEndDate()> from )
                        || (prices.getStartDate()>from && prices.getStartDate()<until)
                        ||(prices.getStartDate()<until && prices.getEndDate()==0))
                .collect(Collectors.toList());

        return allPrices;
*/
        return this.pricesRepository.findAllByVdcIDAndStartDateIsBetweenOrEndDateIsBetweenOrEndDateBeforeAndEndDateIsNull(id, from,until,from,until,until);
    }

}


