package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Prices;
import org.springframework.data.jpa.repository.Query;
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
    @Query("select p from Prices p where p.orgID = ?1  and (p.startDate < ?3 and (p.endDate > ?2 or p.endDate = 0))")
    List<Prices> findAllByOrgIDWithinTimeframe(String orgID, long startDate, long endDate);
    @Query("select p from Prices p where p.vdcID = ?1 and (p.startDate < ?3 and (p.endDate > ?2 or p.endDate = 0))")
    List<Prices> findAllByVDCIDWithinTimeframe(String vdcID, long startDate, long endDate);
}
