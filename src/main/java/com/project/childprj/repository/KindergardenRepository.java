package com.project.childprj.repository;

import com.project.childprj.domain.Kindergarden;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KindergardenRepository {
    int insertKindergarden(Kindergarden kindergarden);

    List<Kindergarden> selectFromCnt(int from, int cnts);

    int selectCountAll();

    Kindergarden selectKindergarden(Long id);

    List<Kindergarden> selectAll();
}
