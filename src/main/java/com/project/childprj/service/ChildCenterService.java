package com.project.childprj.service;

import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface ChildCenterService {
    List<Map<String, Object>> getChildCenter(Integer startIndex, Integer endIndex);

    Object childCenterList(Integer page, String type, Model model);
}
