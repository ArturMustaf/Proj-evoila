package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud;

import de.thbingenproj.backend.entity.VDCComputeCapacity;
import org.springframework.stereotype.Service;

@Service
public class VDCComputeCapacityMockCreator {

    //ToDo set missing properties for mock object
    public VDCComputeCapacity createVDCComputeCapacity() {
        VDCComputeCapacity vdcComputeCapacity = new VDCComputeCapacity();
        vdcComputeCapacity.setId(0l);
        vdcComputeCapacity.setCpuUnits("MHz");
        //vdcComputeCapacity.setCpuLimit();
        //vdcComputeCapacity.setCpuAllocated();
        //vdcComputeCapacity.setCpuReserved();
        //vdcComputeCapacity.setCpuUsed();
        //vdcComputeCapacity.setMemoryUsed();
        //vdcComputeCapacity.setMemoryLimit();
        //vdcComputeCapacity.setMemoryAllocated();
        //vdcComputeCapacity.setMemoryReserved();
        //vdcComputeCapacity.setMemoryUsed();
        //vdcComputeCapacity.setDate();
        //vdcComputeCapacity.setVdcID();
        return vdcComputeCapacity;
    }
}
