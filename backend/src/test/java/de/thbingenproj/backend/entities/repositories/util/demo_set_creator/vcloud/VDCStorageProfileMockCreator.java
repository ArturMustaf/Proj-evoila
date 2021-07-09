package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud;

import de.thbingenproj.backend.entity.VDCComputeCapacity;
import de.thbingenproj.backend.entity.VDCStorageProfile;
import org.springframework.stereotype.Service;

@Service
public class VDCStorageProfileMockCreator {

    //ToDo set missing properties for mock object
    public VDCStorageProfile createVDCStorageProfile() {
        VDCStorageProfile vdcStorageProfile = new VDCStorageProfile();
        vdcStorageProfile.setId("urn:vcloud:vdcstorageProfile:eb6aacbd-f0d8-4a07-8248-1de28151e98f");
        vdcStorageProfile.setHref("https://vcloud.example.com/api/vdcStorageProfile/eb6aacbd-f0d8-4a07-8248-1de28151e98f");
        vdcStorageProfile.setName("profile");
        //vdcStorageProfile.setVdcID();
        //vdcStorageProfile.setDate();
        vdcStorageProfile.setEnabled(Boolean.FALSE.toString());
        vdcStorageProfile.setUnits("MB");
        vdcStorageProfile.setMaxLimit(Long.valueOf(301).toString());
        //vdcStorageProfile.setStorageUsedMB();
        //vdcStorageProfile.setIopsAllocated();
        return vdcStorageProfile;
    }
}
