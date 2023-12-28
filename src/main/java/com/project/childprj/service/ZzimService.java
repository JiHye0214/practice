package com.project.childprj.service;

import com.project.childprj.domain.Zzim;
import org.springframework.ui.Model;
import java.util.List;

public interface ZzimService {
    List<Zzim> zzimList(Integer page, Model model);

    boolean isZzimChecked(Long userId, Long togetherId);

    int insertZzim(Long userId, Long togetherId);

    int deleteZzim(Long userId, Long togetherId);
}
