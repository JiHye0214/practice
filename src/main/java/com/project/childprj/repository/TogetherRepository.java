package com.project.childprj.repository;

import com.project.childprj.domain.Together;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TogetherRepository {
    int insertTogether(Together together);

    List<Together> selectType12FromCnt(int from, int cnts, String type);

    List<Together> selectType3FromCnt(int from, int cnts);

    List<Together> selectAllTogether();

    int selectCountType12(String type);

    int selectCountType3();

    int countAllTogether();

    Together selectTogether(Long id);

    int changeZzimCnt(Long num, Long id);

    int changeIsZzimClicked(String bool, Long id);

    int changeAllZzimClicked();

    List<Together> selectFive();

    int changeType1();
    int changeType2();
    int changeType3();
}
