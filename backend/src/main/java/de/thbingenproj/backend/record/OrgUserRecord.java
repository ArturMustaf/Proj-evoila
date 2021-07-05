package de.thbingenproj.backend.record;

import de.thbingenproj.backend.model.OrgUserDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgUserRecord {
    @JsonAlias("userReference")
    ArrayList<OrgUserDTO> orgUserRecords;
}
