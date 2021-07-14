package de.thbingenproj.backend.controller;

import de.thbingenproj.backend.entity.App;
import de.thbingenproj.backend.entity.OSB;
import de.thbingenproj.backend.entity.Space;
import de.thbingenproj.backend.model.AppDTO;
import de.thbingenproj.backend.model.OSBDTO;
import de.thbingenproj.backend.model.SpaceDTO;
import de.thbingenproj.backend.repository.AppRepository;
import de.thbingenproj.backend.repository.OSBRepository;
import de.thbingenproj.backend.repository.SpaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArangoController {
    @Autowired
    AppRepository appRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    OSBRepository osbRepository;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("create/apps")
    public App postApp(@RequestBody AppDTO app) {
        return this.appRepository.save(modelMapper.map(app, App.class));
    }

    @PostMapping("create/osbs")
    public OSB postOSB(@RequestBody OSBDTO osb) {
        return this.osbRepository.save(modelMapper.map(osb, OSB.class));
    }

    @PostMapping("create/spaces")
    public Space postSpace(@RequestBody SpaceDTO space) {
        return this.spaceRepository.save(modelMapper.map(space, Space.class));
    }
    
    @GetMapping("create/customers/{id}")
    public Space getSpace(@PathVariable String id) {
        return this.spaceRepository.findById(id).orElse(null);
    }

    @GetMapping("arango/lastUpdate")
    public long lastUpdate() {
        long lastUpdate = 0;
        try {
            var osb = osbRepository.findTopByOrderByDtDesc();
            var app = appRepository.findTopByOrderByDtDesc();
            if(osb != null){
                if (lastUpdate < osb.getDt()){
                    lastUpdate = osb.getDt();
                }
            }
            if(app != null){
                if (lastUpdate < app.getDt()) {
                    lastUpdate = app.getDt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastUpdate;
    }

}
