package com.crawler.arango.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.crawler.arango.entity.ArangoApp;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArangoAppRepository extends ArangoRepository<ArangoApp, String> {
    /**
     * Used to get all data since the latest update in backend
     * @param date returned date of latest update from backend
     * @return all found apps in DB
     */
    @Query("for app in apps filter app.dt >= @date return app")
    List<ArangoApp> findAllByDate(@Param("date") String date);
}
