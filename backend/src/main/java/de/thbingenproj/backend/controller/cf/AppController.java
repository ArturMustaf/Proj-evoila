package de.thbingenproj.backend.controller.cf;

import de.thbingenproj.backend.entity.App;
import de.thbingenproj.backend.model.AppDTO;
import de.thbingenproj.backend.repository.AppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AppController {
    @Autowired
    AppRepository appRepository;
    @Autowired
    ModelMapper modelMapper;

    /**
     * Accepts a single app via post request and saves it
     * @param app describes the DTO object of an actual App
     * @return saved App for handling correct responses
     */
    @PostMapping("create/apps")
    public App postApp(@RequestBody AppDTO app) {
        return this.appRepository.save(modelMapper.map(app, App.class));
    }

    /**
     * Returns a single app per ID via get request
     * @param id internal ID of the requested App
     * @return found App
     */
    @GetMapping("apps/{id}")
    public App getApp(@PathVariable String id) {
        return this.appRepository.findById(id).orElse(null);
    }

    /**
     * Returns all apps for given organization via get request
     * @param id internal ID of the organization
     * @return all found Apps
     */
    @GetMapping("organizations/{id}/apps")
    public List<App> getAllAppsByOrg(@PathVariable String id) {
        return this.appRepository.findAllByOrg(id);
    }

    /**
     * Returns all apps for given space via get request
     * @param id internal ID of the space
     * @return all found Apps
     */
    @GetMapping("spaces/{id}/apps")
    public List<App> getAllAppsBySpace(@PathVariable String id) {
        return this.appRepository.findAllBySpace(id);
    }

    /**
     * Returns all apps regardless of organization or space via get request
     * @return all found Apps
     */
    @GetMapping("admin/apps")
    public List<App> getAllApps() {
        return this.appRepository.findAll();
    }

    @GetMapping("organizations/{orgID}/latest-app")
    public List<App> getLatestAppsForSpace(@PathVariable String orgID) {
        var spaceList = this.appRepository.findAllDistinctSpacesByOrg(orgID);
        System.out.println(spaceList);
        var appList = new ArrayList<String>();
        var sendList = new ArrayList<App>();
        for (String space: spaceList) {
            appList.addAll(this.appRepository.findAllGUIDBySpaceAndOrg(space, orgID));
        }
        System.out.println(appList);
        for (String app: appList) {
            sendList.add(this.appRepository.findTopByGuidOrderByDtDesc(app));
        }
        System.out.println(sendList);
        return sendList;
    }
}
