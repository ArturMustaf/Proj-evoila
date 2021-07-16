package com.crawler.arango.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.crawler.arango.entity.ArangoOSB;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArangoOSBRepository extends ArangoRepository<ArangoOSB, String> {
    @Query("for osb in osbs filter osb.dt >= @date return osb")
    List<ArangoOSB> findAllByDate(@Param("date") String date);
}
