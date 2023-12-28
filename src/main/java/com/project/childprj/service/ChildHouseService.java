package com.project.childprj.service;

import com.project.childprj.domain.ChildHouse;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.util.List;

public interface ChildHouseService {
    int insertChildHouse(ChildHouse childHouse);

    ResponseEntity<Integer> saveChildHouse(Integer startIndex, Integer endIndex);

    List<ChildHouse> childHouseList(Integer page, String type, Model model);

    ChildHouse getChildHouse(Long id);

    List<ChildHouse> selectAll();
}
