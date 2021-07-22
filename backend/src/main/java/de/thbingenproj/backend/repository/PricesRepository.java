package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Prices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource
public interface PricesRepository extends CrudRepository<Prices, Long> {
    List<Prices> findAll();
    List<Prices> findAllByResourceID(long resourceID);
    Prices findByResourceIDAndEndDateIsNull(long resourceID);
    List<Prices> findAllByVdcID(String vdcID);
    List<Prices> findAllByOrgID(String orgID);

    /**
     * Finds all Prices that were active in the given Timeframe by org id, useful to search for prices between data x and y
     * @param orgID
     * @param startDate
     * @param endDate
     * @return List of Prices
     */
    @Query("select p from Prices p where p.orgID = ?1  and (p.startDate < ?3 and (p.endDate > ?2 or p.endDate = 0))")
    List<Prices> findAllByOrgIDWithinTimeframe(String orgID, long startDate, long endDate);
    /**
     * Finds all Prices that were active in the given Timeframe by vdc id, useful to search for prices between data x and y
     * @param vdcID
     * @param startDate
     * @param endDate
     * @return List of Prices
     */
    @Query("select p from Prices p where p.vdcID = ?1 and (p.startDate < ?3 and (p.endDate > ?2 or p.endDate = 0))")
    List<Prices> findAllByVDCIDWithinTimeframe(String vdcID, long startDate, long endDate);

    /**
     * Finds all Prices of a resource that were active in the given Timeframe by vdc id, useful to search for prices between data x and y
     * @param id
     * @param startDate
     * @param endDate
     * @return List of Prices
     */
    @Query("select p from Prices p where p.resourceID = ?1  and (p.startDate < ?3 and (p.endDate > ?2 or p.endDate = 0))")
    List<Prices> findAllByResourceIDWithinTimeframe(long id, long startDate, long endDate);
}
