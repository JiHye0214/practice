package com.project.childprj.repository;

import com.project.childprj.domain.Zzim;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZzimRepository {
    List<Zzim> selectFromCnt(int from, int cnts, Long userId);

    int selectCountAll(Long userId);

    Zzim zzimCheck(Long userId, Long togetherId);

    int insertZzim(Zzim zzim);

    int deleteZzim(Long userId, Long togetherId);

}
