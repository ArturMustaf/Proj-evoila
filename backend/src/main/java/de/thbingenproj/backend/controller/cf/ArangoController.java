package de.thbingenproj.backend.controller.cf;

import de.thbingenproj.backend.repository.AppRepository;
import de.thbingenproj.backend.repository.OSBRepository;
import de.thbingenproj.backend.repository.SpaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * Finds the latest entry date of any CF resource
     * @return last updated date
     */
    @GetMapping("arango/lastUpdate")
    public long lastUpdate() {
        long lastUpdate = 0;
        try {
            var osb = osbRepository.findTopByOrderByDtDesc();
            var app = appRepository.findTopByOrderByDtDesc();
            if(osb != null && (lastUpdate < osb.getDt())){
                lastUpdate = osb.getDt();
            }
            if(app != null && (lastUpdate < app.getDt())){
                lastUpdate = app.getDt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastUpdate;
    }

}
