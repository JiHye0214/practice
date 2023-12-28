package com.project.childprj.repository;

import com.project.childprj.domain.ProductImg;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImgRepository {

    ProductImg findByProduct(Long productId);

    int imgInsert(ProductImg productImg);

    int imgDelete(Long productId);
}
