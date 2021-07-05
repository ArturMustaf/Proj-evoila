package de.thbingenproj.backend.record;

import de.thbingenproj.backend.model.VDCDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VDCRecord {
    @JsonAlias("vdc")
    ArrayList<VDCDTO> vdc;
}
