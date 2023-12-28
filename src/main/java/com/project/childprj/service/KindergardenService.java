package com.project.childprj.service;

import com.project.childprj.domain.Kindergarden;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.List;

public interface KindergardenService {
    int insertKindergarden(Kindergarden kindergarden);

    ResponseEntity<Integer> saveKindergarden(Integer startIndex, Integer endIndex);

    List<Kindergarden> kindergardenList(Integer page, String type, Model model);

    Kindergarden getKindergarden(Long id);

    List<Kindergarden> selectAll();
}
