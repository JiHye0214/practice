package com.project.childprj.service;

import com.project.childprj.domain.Together;
import com.project.childprj.domain.Zzim;
import com.project.childprj.repository.*;
import com.project.childprj.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@Service
public class ZzimServiceImpl implements ZzimService {

    private TogetherRepository togetherRepository;
    private ZzimRepository zzimRepository;

    @Autowired
    public ZzimServiceImpl(SqlSession sqlSession) {
        togetherRepository = sqlSession.getMapper(TogetherRepository.class);
        zzimRepository = sqlSession.getMapper(ZzimRepository.class);
    }

    @Override
    public List<Zzim> zzimList(Integer page, Model model) {
        Long userId = U.getLoggedUser().getId();

        if (page < 1) page = 1;

        Integer pagesPerSection = 5;
        Integer rowsPerPage = 10;

        int totalLength = zzimRepository.selectCountAll(userId);
        int totalPage = (int) Math.ceil(totalLength / (double) rowsPerPage);

        int startPage = 0;
        int endPage = 0;

        List<Zzim> zzims = null;

        if (totalLength > 0) {
            if (page > totalPage) page = totalPage;

            int fromRow = (page - 1) * rowsPerPage;

            startPage = (((page - 1) / pagesPerSection) * pagesPerSection) + 1;
            endPage = startPage + pagesPerSection - 1;
            if (endPage > totalPage) endPage = totalPage;

            zzims = zzimRepository.selectFromCnt(fromRow, rowsPerPage, userId);

            model.addAttribute("zzims", zzims);
        } else {
            page = 0;
        }

        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("rowsPerPage", rowsPerPage);
        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return zzims;
    }

    @Override
    public boolean isZzimChecked(Long userId, Long togetherId) {
        Zzim zzim = zzimRepository.zzimCheck(userId, togetherId);
        return (zzim != null);
    }

    @Override
    public int insertZzim(Long userId, Long togetherId) {
        Together together = togetherRepository.selectTogether(togetherId);
        together.setIsZzimClicked("true");

        Zzim zzim = new Zzim();
        zzim.setUserId(userId);
        zzim.setTogether(together);

        int cnt = zzimRepository.insertZzim(zzim);

        return cnt;
    }

    @Override
    public int deleteZzim(Long userId, Long togetherId) {
        Together together = togetherRepository.selectTogether(togetherId);
        together.setIsZzimClicked("false");

        return zzimRepository.deleteZzim(userId, togetherId);
    }

}
