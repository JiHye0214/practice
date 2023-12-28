package com.project.childprj.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.project.childprj.domain.Kindergarden;
import com.project.childprj.repository.KindergardenRepository;
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
public class KindergardenServiceImpl implements KindergardenService {

    @Value("${app.api.kinderKey}")
    private String kinderKey;

    private KindergardenRepository kindergardenRepository;

    @Autowired
    public KindergardenServiceImpl(SqlSession sqlSession) {
        kindergardenRepository = sqlSession.getMapper(KindergardenRepository.class);
    }

    @Override
    public int insertKindergarden(Kindergarden kindergarden) {
        return kindergardenRepository.insertKindergarden(kindergarden);
    }

    @Override
    public ResponseEntity<Integer> saveKindergarden(Integer startIndex, Integer endIndex) {
        String type = "json";
        String service = "childSchoolInfo";

        String uri = String.format("http://openapi.seoul.go.kr:8088/%s/%s/%s/%d/%d",
                kinderKey, type, service, startIndex, endIndex);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        try {
            if (response.getStatusCode().is2xxSuccessful()) {
                String jsonData = response.getBody();

                if (jsonData != null && jsonData.length() > 0) {
                    int result = 0;
                    JsonNode rootNode = U.jsonToJsonNode(jsonData);

                    ArrayNode rows = (ArrayNode) rootNode.get("childSchoolInfo").get("row");
                    for (JsonNode row : rows) {
                        Kindergarden kindergarden = Kindergarden.fromJson(row);
                        result += this.insertKindergarden(kindergarden);
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
    public List<Kindergarden> kindergardenList(Integer page, String type, Model model) {
        if (page == null) page = 1;
        if (page < 1) page = 1;

        Integer pagesPerSection = 5;
        Integer rowsPerPage = 10;

        int totalLength = kindergardenRepository.selectCountAll();
        int totalPage = (int) Math.ceil(totalLength / (double) rowsPerPage);

        int startPage = 0;
        int endPage = 0;

        List<Kindergarden> kindergardens = null;

        if (totalLength > 0) {
            if (page > totalPage) page = totalPage;

            int fromRow = (page - 1) * rowsPerPage;

            startPage = (((page - 1) / pagesPerSection) * pagesPerSection) + 1;
            endPage = startPage + pagesPerSection - 1;
            if (endPage > totalPage) endPage = totalPage;

            kindergardens = kindergardenRepository.selectFromCnt(fromRow, rowsPerPage);
            model.addAttribute("kindergardens", kindergardens);
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

        return kindergardens;
    }

    @Override
    public Kindergarden getKindergarden(Long id) {
        return kindergardenRepository.selectKindergarden(id);
    }

    @Override
    public List<Kindergarden> selectAll() {
        return kindergardenRepository.selectAll();
    }

}
