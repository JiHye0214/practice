package com.project.childprj.service;

import com.project.childprj.domain.Product;
import com.project.childprj.domain.ProductImg;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> list(Integer page, String sq, String productOrderWay, Model model);

    int write(Product product);

    Product productDetail(Long id);

    void incViewCnt(Long id);

    int detailDelete(Long id);

    int update(Product product);

    ProductImg findByProduct (Long productId);

    boolean imgInsert(Map<String, MultipartFile> file, Long productId);

    List<Product> selectFive();
}
