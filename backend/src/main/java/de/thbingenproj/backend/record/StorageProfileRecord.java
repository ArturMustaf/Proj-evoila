package de.thbingenproj.backend.record;

import de.thbingenproj.backend.model.VDCStorageProfileDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StorageProfileRecord {
    ArrayList<VDCStorageProfileDTO> vdcStorageProfile;
}
