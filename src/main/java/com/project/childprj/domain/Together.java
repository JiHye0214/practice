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
public class Together {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String CODENAME;
    private String GUNAME;
    private String TITLE;
    private String DATE;
    private String PLACE;
    private String ORG_NAME;
    private String USE_TRGT;
    private String USE_FEE;
    private String ORG_LINK;
    private String MAIN_IMG;
    private String STRTDATE;
    private String END_DATE;
    private double LOT;
    private double LAT;
    private String type;
    private Long zzimCnt;
    private String isZzimClicked;

    public static Together fromJson(JsonNode row) {
        Together together = new Together();

        together.setCODENAME(getTextValue(row, "CODENAME"));
        together.setGUNAME(getTextValue(row, "GUNAME"));
        together.setTITLE(getTextValue(row, "TITLE"));
        together.setDATE(getTextValue(row, "DATE"));
        together.setPLACE(getTextValue(row, "PLACE"));
        together.setORG_NAME(getTextValue(row, "ORG_NAME"));
        together.setUSE_TRGT(getTextValue(row, "USE_TRGT"));
        together.setUSE_FEE(getTextValue(row, "USE_FEE"));
        together.setORG_LINK(getTextValue(row, "ORG_LINK"));
        together.setMAIN_IMG(getTextValue(row, "MAIN_IMG"));
        together.setSTRTDATE(getTextValue(row, "STRTDATE"));
        together.setEND_DATE(getTextValue(row, "END_DATE"));
        together.setLOT(getDoubleValue(row, "LAT"));
        together.setLAT(getDoubleValue(row, "LOT"));

        return together;
    }

    private static String getTextValue(JsonNode row, String fieldName) {
        JsonNode node = row.get(fieldName);
        return (node != null && !node.isNull()) ? node.asText() : null;
    }

    private static double getDoubleValue(JsonNode row, String fieldName) {
        JsonNode node = row.get(fieldName);
        return (node != null && !node.isNull()) ? node.asDouble() : 0.0;
    }
}
