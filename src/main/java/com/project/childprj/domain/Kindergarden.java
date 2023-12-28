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
public class Kindergarden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String KINDERNAME;
    private String ESTABLISH;
    private String LDGRNAME;
    private String ODATE;
    private String ADDR;
    private String TELNO;
    private String HPADDR;
    private String OPERTIME;

    public static Kindergarden fromJson(JsonNode row) {
        Kindergarden kindergarden = new Kindergarden();

        kindergarden.setKINDERNAME(row.get("KINDERNAME").asText());
        kindergarden.setESTABLISH(row.get("ESTABLISH").asText());
        kindergarden.setLDGRNAME(row.get("LDGRNAME").asText());
        kindergarden.setODATE(row.get("ODATE").asText());
        kindergarden.setADDR(row.get("ADDR").asText());
        kindergarden.setTELNO(row.get("TELNO").asText());
        kindergarden.setHPADDR(row.get("HPADDR").asText());
        kindergarden.setOPERTIME(row.get("OPERTIME").asText());

        return kindergarden;
    }
}
