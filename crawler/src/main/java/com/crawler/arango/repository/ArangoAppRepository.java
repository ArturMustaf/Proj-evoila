package com.crawler.arango.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.crawler.arango.entity.ArangoApp;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArangoAppRepository extends ArangoRepository<ArangoApp, String> {
    @Query("for app in apps filter app.dt >= @date return app")
    List<ArangoApp> findAllByDate(@Param("date") String date);
}
