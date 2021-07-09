package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud;

import de.thbingenproj.backend.entity.Organization;
import de.thbingenproj.backend.entity.VApp;
import org.springframework.stereotype.Service;

@Service
public class VAppMockCreator {

    //ToDo set missing properties for mock object
    public VApp createVApp() {
        VApp vApp = new VApp();
        vApp.setId("urn:vcloud:vapp:bba47763-0ce1-45b9-8470-ea29ba58c52f");
        vApp.setHref("https://vcloud.example.com/api/vApp/vapp-bba47763-0ce1-45b9-8470-ea29ba58c52f");
        vApp.setName("importedVapp");
        vApp.setDescription("VApp Descriptio");
        vApp.setCreationDate("1999-05-31T13:20:00.000-05:00");
        vApp.setIsAutoNature(Boolean.TRUE.toString());
        vApp.setIsBusy(Boolean.TRUE.toString());
        vApp.setIsDeployed(Boolean.TRUE.toString());
        vApp.setIsEnabled(Boolean.TRUE.toString());
        vApp.setIsExpired(Boolean.TRUE.toString());
        vApp.setIsInMaintenanceMode(Boolean.TRUE.toString());
        vApp.setIsPublic(Boolean.TRUE.toString());
        //vApp.setMemoryAllocationMB();
        //vApp.setNumberOfCpus();
        //vApp.setOwnerName();
        vApp.setStatus("8");
        //vApp.setTotalStorageAllocatedMb();
        //vApp.setVdcHRef();
        //vApp.setVdcName();
        //vApp.setVdcID();
        //vApp.setCpuAllocationMhz();
        //vApp.setStorageKB();
        //vApp.setNumberOfVMs();
        //vApp.setCpuAllocationInMhz();
        return vApp;
    }
}
