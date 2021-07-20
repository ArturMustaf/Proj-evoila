package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findAll();
    List<Resource> findAllByVdcID(String vdcID);
    List<Resource> findAllByOrgID(String orgID);

    // findaAll ById, StartDate between from until || EndDate between from until || EnDate before from && until==null (0)
    // findaAll ById, StartDate between from until || EndDate between from until || EnDate before from && until==null (0)
    @Query("select r from Resource r where r.orgID = ?1  and (r.startDate < ?3 and (r.endDate > ?2 or r.endDate = 0))")
    List<Resource> findAllByOrgIDWithinTimeframe(String orgID, long startDate, long endDate);
    @Query("select r from Resource r where r.vdcID = ?1 and (r.startDate < ?3 and (r.endDate > ?2 or r.endDate = 0))")
    List<Resource> findAllByVDCIDWithinTimeframe(String vdcID, long startDate, long endDate);
}
