package de.thbingenproj.backend.controller.cf;

import de.thbingenproj.backend.entity.App;
import de.thbingenproj.backend.model.AppDTO;
import de.thbingenproj.backend.repository.AppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppController {
    @Autowired
    AppRepository appRepository;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("create/apps")
    public App postApp(@RequestBody AppDTO app) {
        return this.appRepository.save(modelMapper.map(app, App.class));
    }

    @GetMapping("apps/{id}")
    public App getApp(@PathVariable String id) {
        return this.appRepository.findById(id).orElse(null);
    }

    @GetMapping("organizations/{id}/apps")
    public List<App> getAllAppsByOrg(@PathVariable String id) {
        return this.appRepository.findAllByOrg(id);
    }

    @GetMapping("spaces/{id}/apps")
    public List<App> getAllAppBySpace(@PathVariable String id) {
        return this.appRepository.findAllBySpace(id);
    }

    @GetMapping("admin/apps")
    public List<App> getAllApps() {
        return this.appRepository.findAll();
    }

}
