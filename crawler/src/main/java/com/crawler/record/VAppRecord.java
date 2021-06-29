package com.crawler.record;

import com.crawler.model.VAppDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VAppRecord {
    ArrayList<VAppDTO> record;
}
