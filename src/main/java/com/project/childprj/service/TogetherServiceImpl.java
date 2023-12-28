package com.project.childprj.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.project.childprj.domain.Together;
import com.project.childprj.repository.TogetherRepository;
import com.project.childprj.repository.ZzimRepository;
import com.project.childprj.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class TogetherServiceImpl implements TogetherService {

    @Value("${app.api.SeoulDataKey}")
    private String SeoulDataKey;

    @Autowired
    private ZzimService zzimService;

    private TogetherRepository togetherRepository;
    private ZzimRepository zzimRepository;

    @Autowired
    public TogetherServiceImpl(SqlSession sqlSession) {
        togetherRepository = sqlSession.getMapper(TogetherRepository.class);
        zzimRepository = sqlSession.getMapper(ZzimRepository.class);
    }

    @Override
    public int insertTogether(Together together) {
        return togetherRepository.insertTogether(together);
    }

    @Override
    public ResponseEntity<Integer> saveTogether(Integer startIndex, Integer endIndex) {
        String type = "json";
        String service = "culturalEventInfo";

        String uri = String.format("http://openapi.seoul.go.kr:8088/%s/%s/%s/%d/%d",
                SeoulDataKey, type, service, startIndex, endIndex);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        try {
            if (response.getStatusCode().is2xxSuccessful()) {
                String jsonData = response.getBody();

                if (jsonData != null && jsonData.length() > 0) {
                    int result = 0;
                    JsonNode rootNode = U.jsonToJsonNode(jsonData);

                    ArrayNode rows = (ArrayNode) rootNode.get("culturalEventInfo").get("row");
                    for (JsonNode row : rows) {
                        Together together = Together.fromJson(row);
                        result += this.insertTogether(together);
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
    public List<Together> togetherList(Integer page, String type, Model model) {
        if (page < 1) page = 1;

        Integer pagesPerSection = 10;
        Integer rowsPerPage = 10;

        int totalLength = 0;
        if (type.equals("체험") || type.equals("축제")) {
            totalLength = togetherRepository.selectCountType12(type);
        } else {
            totalLength = togetherRepository.selectCountType3();
        }

        int totalPage = (int) Math.ceil(totalLength / (double) rowsPerPage);

        int startPage = 0;
        int endPage = 0;

        String user = "" + SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String logged = null;

        togetherRepository.changeAllZzimClicked();

        if (!user.equals("anonymousUser")) {
            int allTogetherLen = togetherRepository.countAllTogether();
            List<Together> allTogether = togetherRepository.selectAllTogether();

            Long userId = U.getLoggedUser().getId();

            if (allTogetherLen > 0) {
                for (var together : allTogether) {
                    boolean isZzimChecked = zzimService.isZzimChecked(userId, together.getId());

                    if (isZzimChecked) {
                        togetherRepository.changeIsZzimClicked("true", together.getId());
                    }
                }
            }

            System.out.println("로그인됨 : " + userId);

            logged = "true";
        }

        List<Together> togethers = null;

        if (totalLength > 0) {
            if (page > totalPage) page = totalPage;

            int fromRow = (page - 1) * rowsPerPage;

            startPage = (((page - 1) / pagesPerSection) * pagesPerSection) + 1;
            endPage = startPage + pagesPerSection - 1;
            if (endPage > totalPage) endPage = totalPage;

            if (type.equals("체험") || type.equals("축제")) {
                togethers = togetherRepository.selectType12FromCnt(fromRow, rowsPerPage, type);
            } else {
                togethers = togetherRepository.selectType3FromCnt(fromRow, rowsPerPage);
            }

            model.addAttribute("togethers", togethers);
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
        model.addAttribute("logged", logged);

        return togethers;
    }

    @Override
    public String togetherDetail(String type, Long id, Model model) {
        String user = "" + SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String logged = null;

        togetherRepository.changeAllZzimClicked();

        if (!user.equals("anonymousUser")) {
            int allTogetherLen = togetherRepository.countAllTogether();
            List<Together> allTogether = togetherRepository.selectAllTogether();

            Long userId = U.getLoggedUser().getId();

            if (allTogetherLen > 0) {
                for (var together : allTogether) {
                    boolean isZzimChecked = zzimService.isZzimChecked(userId, together.getId());

                    if (isZzimChecked) {
                        togetherRepository.changeIsZzimClicked("true", together.getId());
                    }
                }
            }

            logged = "true";
        }

        model.addAttribute("type", type);
        model.addAttribute("logged", logged);

        return logged;
    }

    @Override
    public Together getTogether(Long id) {
        return togetherRepository.selectTogether(id);
    }

    @Override
    public int changeZzimCnt(Long num, Long id) {
        return togetherRepository.changeZzimCnt(num, id);
    }

    @Override
    public int changeIsZzimClicked(String bool, Long id) {
        return togetherRepository.changeIsZzimClicked(bool, id);
    }

    @Override
    public List<Together> selectFive() {
        return togetherRepository.selectFive();
    }

    @Override
    public int changeType1() {
        return togetherRepository.changeType1();
    }

    @Override
    public int changeType2() {
        return togetherRepository.changeType2();
    }

    @Override
    public int changeType3() {
        return togetherRepository.changeType3();
    }
}
