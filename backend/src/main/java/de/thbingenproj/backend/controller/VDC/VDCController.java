package de.thbingenproj.backend.controller.VDC;

import de.thbingenproj.backend.entity.VDC;
import de.thbingenproj.backend.model.VDCDTO;
import de.thbingenproj.backend.repository.VDCRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VDCController {
    @Autowired
    private VDCRepository vdcRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/vdc")
    public VDC putVDC(@RequestBody VDCDTO vdc){
        return this.vdcRepository.save(modelMapper.map(vdc, VDC.class));
    }
}
