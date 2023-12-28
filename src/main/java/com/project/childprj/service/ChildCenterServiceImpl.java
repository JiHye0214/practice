package com.project.childprj.service;

import com.project.childprj.util.U;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChildCenterServiceImpl implements ChildCenterService {

    @Value("${app.api.childCenterKey}")
    private String childCenterKey;

    @Override
    public List<Map<String, Object>> getChildCenter(Integer startIndex, Integer endIndex) {
        String type = "json";
        String service = "TnFcltySttusInfo1003";

        String uri = String.format("http://openapi.seoul.go.kr:8088/%s/%s/%s/%d/%d",
                childCenterKey, type, service, startIndex, endIndex);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JSONObject> response = restTemplate.getForEntity(uri, JSONObject.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject body = response.getBody();
            if (body != null && !body.isEmpty()) {
                Map<String, Object> data = (Map<String, Object>) body.get(service);

                return (List<Map<String, Object>>) data.get("row");
            }
        }

        return new ArrayList<>();
    }

    @Override
    public Object childCenterList(Integer page, String type, Model model) {
        if (page == null) page = 1;
        if (page < 1) page = 1;

        Integer pagesPerSection = 5;
        Integer rowsPerPage = 10;

        var allChildCenters = (ArrayList) (U.getSession().getAttribute("childCenter"));
        int totalLength = allChildCenters.size();
        int totalPage = (int) Math.ceil(totalLength / (double) rowsPerPage);

        int startPage = 0;
        int endPage = 0;

        ArrayList childCenters = new ArrayList<>();

        if (totalLength > 0) {
            if (page > totalPage) page = totalPage;

            int startRow = (page - 1) * rowsPerPage;
            int endRow = startRow + rowsPerPage > totalLength ? totalLength : startRow + rowsPerPage;

            startPage = (((page - 1) / pagesPerSection) * pagesPerSection) + 1;
            endPage = startPage + pagesPerSection - 1;
            if (endPage > totalPage) endPage = totalPage;

            for (int i = startRow; i < endRow; i++) {
                childCenters.add(allChildCenters.get(i));
            }

            model.addAttribute("childCenters", childCenters);
        } else {
            page = 0;
        }

        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("rowsPerPage", rowsPerPage);
        model.addAttribute("type", type);
        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return childCenters;
    }

}
