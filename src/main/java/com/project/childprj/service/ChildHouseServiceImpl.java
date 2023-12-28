package com.project.childprj.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.project.childprj.domain.ChildHouse;
import com.project.childprj.repository.ChildHouseRepository;
import com.project.childprj.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class ChildHouseServiceImpl implements ChildHouseService {

    @Value("${app.api.kinderKey}")
    private String childHouseKey;

    private ChildHouseRepository childHouseRepository;

    @Autowired
    public ChildHouseServiceImpl(SqlSession sqlSession) {
        childHouseRepository = sqlSession.getMapper(ChildHouseRepository.class);
    }

    @Override
    public int insertChildHouse(ChildHouse childHouse) {
        return childHouseRepository.insertChildHouse(childHouse);
    }

    @Override
    public ResponseEntity<Integer> saveChildHouse(Integer startIndex, Integer endIndex) {
        String type = "json";
        String service = "ChildCareInfo";

        String uri = String.format("http://openapi.seoul.go.kr:8088/%s/%s/%s/%d/%d",
                childHouseKey, type, service, startIndex, endIndex);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        try {
            if (response.getStatusCode().is2xxSuccessful()) {
                String jsonData = response.getBody();

                if (jsonData != null && jsonData.length() > 0) {
                    int result = 0;
                    JsonNode rootNode = U.jsonToJsonNode(jsonData);

                    ArrayNode rows = (ArrayNode) rootNode.get("ChildCareInfo").get("row");
                    for (JsonNode row : rows) {
                        ChildHouse childHouse = ChildHouse.fromJson(row);
                        result += this.insertChildHouse(childHouse);
                    }

                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.status(204).body(null);
                }
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public List<ChildHouse> childHouseList(Integer page, String type, Model model) {
        if (page == null) page = 1;
        if (page < 1) page = 1;

        Integer pagesPerSection = 5;
        Integer rowsPerPage = 10;

        int totalLength = childHouseRepository.selectCountAll();
        int totalPage = (int) Math.ceil(totalLength / (double) rowsPerPage);

        int startPage = 0;
        int endPage = 0;

        List<ChildHouse> childHouses = null;

        if (totalLength > 0) {
            if (page > totalPage) page = totalPage;

            int fromRow = (page - 1) * rowsPerPage;

            startPage = (((page - 1) / pagesPerSection) * pagesPerSection) + 1;
            endPage = startPage + pagesPerSection - 1;
            if (endPage > totalPage) endPage = totalPage;

            childHouses = childHouseRepository.selectFromCnt(fromRow, rowsPerPage);
            model.addAttribute("childHouses", childHouses);
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

        return childHouses;
    }

    @Override
    public ChildHouse getChildHouse(Long id) {
        return childHouseRepository.selectChildHouse(id);
    }

    @Override
    public List<ChildHouse> selectAll() {
        return childHouseRepository.selectAll();
    }

}
