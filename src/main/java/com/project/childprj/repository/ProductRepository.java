package com.project.childprj.repository;

import com.project.childprj.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {
    List<Product> selectFromCntOrderByDate(int from, int cnts, String sq);

    List<Product> selectFromCntOrderByPrice(int from, int cnts, String sq);

    int selectCountAll(String sq);

    int insert(Product product);

    Product findProductById(Long id);

    int incViewCnt(Long id);

    int detailDelete(Long id);

    int update(Product product);

    List<Product> selectFive();
}
