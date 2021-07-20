package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Prices;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource
public interface PricesRepository extends CrudRepository<Prices, String> {
    List<Prices> findAll();
    List<Prices> findAllByResourceID(String resourceID);
    Prices findByResourceIDAndEndDateIsNull(String resourceID);
    List<Prices> findAllByVdcID(String vdcID);
    List<Prices> findAllByOrgID(String orgID);
    // findaAll ById, StartDate between from until || EndDate between from until || EnDate before from && until==null (0)
    List<Prices> findAllByVdcIDAndStartDateIsBetweenOrEndDateIsBetweenOrEndDateBeforeAndEndDateIsNull(String vdcID,long starDate, long endDate,long starDate1, long endDate1, long startDate3 );
    List<Prices>findAllByOrgIDAndStartDateIsBetweenOrEndDateIsBetweenOrEndDateBeforeAndEndDateIsNull(String orgID,long starDate, long endDate,long starDate1, long endDate1, long startDate3 );

}
