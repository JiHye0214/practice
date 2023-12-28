package com.project.childprj.service;

import com.project.childprj.domain.Together;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.util.List;

public interface TogetherService {
    int insertTogether(Together together);

    ResponseEntity<Integer> saveTogether(Integer startIndex, Integer endIndex);

    List<Together> togetherList(Integer page, String type, Model model);

    String togetherDetail(String type, Long id, Model model);

    Together getTogether(Long id);

    int changeZzimCnt(Long num, Long id);

    int changeIsZzimClicked(String bool, Long id);

    List<Together> selectFive();

    int changeType1();
    int changeType2();
    int changeType3();
}
