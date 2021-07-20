package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Prices;
import de.thbingenproj.backend.entity.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource
public interface ResourceRepository extends CrudRepository<Resource, String> {
    List<Resource> findAll();
    List<Resource> findAllByVdcID(String vdcID);
    List<Resource> findAllByOrgID(String orgID);

    List<Resource> findAllByVdcIDAndStartDateIsBetweenOrEndDateIsBetweenOrEndDateBeforeAndEndDateIsNull(String vdcID, long starDate, long endDate, long starDate1, long endDate1, long startDate3 );
    List<Resource>findAllByOrgIDAndStartDateIsBetweenOrEndDateIsBetweenOrEndDateBeforeAndEndDateIsNull(String orgID,long starDate, long endDate,long starDate1, long endDate1, long startDate3 );

}
