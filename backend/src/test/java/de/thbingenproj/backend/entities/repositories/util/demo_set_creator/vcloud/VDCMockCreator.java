package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud;

import de.thbingenproj.backend.entity.VDC;
import de.thbingenproj.backend.entity.VDCComputeCapacity;
import org.springframework.stereotype.Service;

@Service
public class VDCMockCreator {

    //ToDo set missing properties for mock object
    public VDC createVDC() {
        VDC vdc = new VDC();
        vdc.setId("urn:vcloud:vdc:afaafb99-228c-4838-ad07-5bf3aa649d42");
        vdc.setName("Default vDC");
        vdc.setHref("https://vcloud.example.com/api/vdc/afaafb99-228c-4838-ad07-5bf3aa649d42");
        //vdc.setTasks();
        vdc.setAllocationModel("AllocationVApp");
        //vdc.setOrgID();
        return vdc;
    }
}
