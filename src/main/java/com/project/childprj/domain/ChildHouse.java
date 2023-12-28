package com.project.childprj.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String CRNAME;
    private String CRTYPENAME;
    private String CRADDR;
    private String CRTELNO;
    private String CRHOME;
    private Integer NRTRROOMCNT;
    private Integer CRCAPAT;
    private Integer CRCHCNT;
    private Double LA;
    private Double LO;
    private String CRCARGBNAME;

    public static ChildHouse fromJson(JsonNode row){
        ChildHouse childHouse = new ChildHouse();

        childHouse.setCRNAME(row.get("CRNAME").asText());
        childHouse.setCRTYPENAME(row.get("CRTYPENAME").asText());
        childHouse.setCRADDR(row.get("CRADDR").asText());
        childHouse.setCRTELNO(row.get("CRTELNO").asText());
        childHouse.setCRHOME(row.get("CRHOME").asText());
        childHouse.setNRTRROOMCNT(row.get("NRTRROOMCNT").asInt());
        childHouse.setCRCAPAT(row.get("CRCAPAT").asInt());
        childHouse.setCRCHCNT(row.get("CRCHCNT").asInt());
        childHouse.setLA(row.get("LA").asDouble());
        childHouse.setLO(row.get("LO").asDouble());
        childHouse.setCRCARGBNAME(row.get("CRCARGBNAME").asText());

        return childHouse;
    }

}
