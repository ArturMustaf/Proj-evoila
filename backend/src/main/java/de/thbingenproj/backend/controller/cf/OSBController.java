package de.thbingenproj.backend.controller.cf;

import de.thbingenproj.backend.entity.OSB;
import de.thbingenproj.backend.model.OSBDTO;
import de.thbingenproj.backend.repository.OSBRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OSBController {
    @Autowired
    OSBRepository osbRepository;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("create/osbs")
    public OSB postOSB(@RequestBody OSBDTO osb) {
        return this.osbRepository.save(modelMapper.map(osb, OSB.class));
    }

    @GetMapping("osbs/{id}")
    public OSB getSpace(@PathVariable String id) {
        return this.osbRepository.findById(id).orElse(null);
    }

    @GetMapping("admin/osbs/{id}")
    public List<OSB> getAllSpaces(@PathVariable String id) {
        return this.osbRepository.findAll();
    }
}
