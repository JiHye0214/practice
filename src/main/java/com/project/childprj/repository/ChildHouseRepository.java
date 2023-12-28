package com.project.childprj.repository;

import com.project.childprj.domain.ChildHouse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildHouseRepository {
    int insertChildHouse(ChildHouse childHouse);

    List<ChildHouse> selectFromCnt(int from, int cnts);

    int selectCountAll();

    ChildHouse selectChildHouse(Long id);

    List<ChildHouse> selectAll();
}
