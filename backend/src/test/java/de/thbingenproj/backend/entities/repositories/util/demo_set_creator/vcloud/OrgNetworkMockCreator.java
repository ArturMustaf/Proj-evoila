package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud;

import de.thbingenproj.backend.entity.OrgNetwork;
import org.springframework.stereotype.Service;

@Service
public class OrgNetworkMockCreator {

    public OrgNetwork createOrgNetwork() {
        OrgNetwork orgNetwork = new OrgNetwork();
        orgNetwork.setId("urn:vcloud:network:1fcc8a15-340d-43cc-a200-76c2cbbe70ba");
        orgNetwork.setHref("https://vcloud.example.com/api/network/1fcc8a15-340d-43cc-a200-76c2cbbe70ba");
        orgNetwork.setName("VM Network");
        orgNetwork.setOrgID("urn:vcloud:org:7b832bc5-3d65-45a2-8d35-da28388ab80a");
        orgNetwork.setVdcID("urn:vcloud:vdc:afaafb99-228c-4838-ad07-5bf3aa649d42");
        return orgNetwork;
    }
}
