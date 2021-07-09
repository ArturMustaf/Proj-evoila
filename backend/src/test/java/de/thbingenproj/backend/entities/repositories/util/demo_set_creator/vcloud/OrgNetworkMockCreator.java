package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud;

import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.dataset.DataTypes;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.ContactInformationMockCreator;
import de.thbingenproj.backend.entities.user.Customer;
import de.thbingenproj.backend.entity.OrgNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
