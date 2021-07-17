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
    
    /**
     * Accepts a single osb via post request and saves it
     * @param osb describes the DTO object of an actual OSB
     * @return saved OSB for handling correct responses
     */
    @PostMapping("create/osbs")
    public OSB postOSB(@RequestBody OSBDTO osb) {
        return this.osbRepository.save(modelMapper.map(osb, OSB.class));
    }
    
    /**
     * Returns a single osb per ID via get request
     * @param id internal ID of the requested OSB
     * @return found OSB
     */
    @GetMapping("osbs/{id}")
    public OSB getOSB(@PathVariable String id) {
        return this.osbRepository.findById(id).orElse(null);
    }
    
    /**
     * Returns all osbs for given organization via get request
     * @param id internal ID of the organization
     * @return all found OSBs
     */
    @GetMapping("organizations/{id}/osbs")
    public List<OSB> getAllOSBsByOrg(@PathVariable String id) {
        return this.osbRepository.findAllByOrg(id);
    }
    
    /**
     * Returns all osbs for given space via get request
     * @param id internal ID of the space
     * @return all found OSBs
     */
    @GetMapping("spaces/{id}/osbs")
    public List<OSB> getAllOSBsBySpace(@PathVariable String id) {
        return this.osbRepository.findAllBySpace(id);
    }
    
    /**
     * Returns all osbs regardless of organization or space via get request
     * @return all found OSBs
     */
    @GetMapping("admin/osbs")
    public List<OSB> getAllOSBs() {
        return this.osbRepository.findAll();
    }
}
