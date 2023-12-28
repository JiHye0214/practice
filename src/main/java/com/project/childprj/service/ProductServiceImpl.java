package com.project.childprj.service;


import com.project.childprj.domain.Product;
import com.project.childprj.domain.ProductImg;
import com.project.childprj.domain.User;
import com.project.childprj.repository.ProductImgRepository;
import com.project.childprj.repository.ProductRepository;
import com.project.childprj.repository.UserRepository;
import com.project.childprj.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${app.upload.path}")
    private String uploadDir;

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private ProductImgRepository productImgRepository;

    @Autowired
    public ProductServiceImpl(SqlSession sqlSession) {
        productImgRepository = sqlSession.getMapper(ProductImgRepository.class);
        productRepository = sqlSession.getMapper(ProductRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
    }

    @Override
    public List<Product> list(Integer page, String sq, String productOrderWay, Model model) {

        String validSq = sq;

        if (page < 1) page = 1;

        Integer pagesPerSection = 5;
        Integer rowsPerPage = 8;

        int totalLength = productRepository.selectCountAll(sq);
        if(totalLength == 0) validSq = "";
        int totalPage = (int) Math.ceil(totalLength / (double) rowsPerPage);

        int startPage = 0;
        int endPage = 0;

        List<Product> products = new ArrayList<>();

        if (totalLength > 0) {
            if (page > totalPage) page = totalPage;

            int fromRow = (page - 1) * rowsPerPage;

            startPage = (((page - 1) / pagesPerSection) * pagesPerSection) + 1;
            endPage = startPage + pagesPerSection - 1;
            if (endPage > totalPage) endPage = totalPage;

            if (productOrderWay.equals("최신순")) {
                products = productRepository.selectFromCntOrderByDate(fromRow, rowsPerPage, validSq);
            } else if (productOrderWay.equals("가격순")) {
                products = productRepository.selectFromCntOrderByPrice(fromRow, rowsPerPage, validSq);
            }
            model.addAttribute("products", products);
        } else {
            page = 0;
        }

        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("rowsPerPage", rowsPerPage);
        model.addAttribute("productOrderWay", productOrderWay);
        model.addAttribute("sq", sq);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        for(Product e : products){
            e.setProductImg(productImgRepository.findByProduct(e.getId()));
        }

        return products;
    }

    public int write(Product product) {
        User user = U.getLoggedUser();

        user = userRepository.findUserById(user.getId());
        product.setUser(user);

        int cnt = productRepository.insert(product);

        return cnt;
    }

    @Override
    public Product productDetail(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public void incViewCnt(Long id) {
        productRepository.incViewCnt(id);
    }

    @Override
    public int detailDelete(Long id) {
        return productRepository.detailDelete(id);
    }

    @Override
    public int update(Product product) {
        int result = productRepository.update(product);
        return result;
    }

    @Override
    public ProductImg findByProduct(Long productId) {
        return productImgRepository.findByProduct(productId);
    }

    @Override
    public boolean imgInsert(Map<String, MultipartFile> file, Long productId) {
        changeImg(file, productId);
        return (file != null);
    }

    private void changeImg(Map<String, MultipartFile> file, Long productId){

        if(productImgRepository.findByProduct(productId) != null){
            productImgRepository.imgDelete(productId);
        }

        if(file == null) return;

        for(Map.Entry<String, MultipartFile> e : file.entrySet()){

            if(!e.getKey().startsWith("product")) continue;

            ProductImg productImg = upload(e.getValue());

            if(productImg != null){
                productImg.setProductId(productId);
                productImgRepository.imgInsert(productImg);
            }
        }
    }

    private ProductImg upload(MultipartFile multipartFile){

        ProductImg productImg = null;
        String sourceName = null;
        String fileName = null;

        String originalFileName = multipartFile.getOriginalFilename();

        if(originalFileName.isEmpty()){
            sourceName = "default.jpg";
            fileName = sourceName;
        } else {
            sourceName = StringUtils.cleanPath(originalFileName);

            fileName = sourceName;

            File file = new File(uploadDir, fileName);

            if(file.exists()){

                int pos = fileName.lastIndexOf(".");
                if(pos > -1){
                    String name = fileName.substring(0, pos);
                    String ext = fileName.substring(pos + 1);

                    fileName = name + "_" + System.currentTimeMillis() + "." + ext;
                } else {
                    fileName += "_" + System.currentTimeMillis();
                }
            }

            Path copyOfLocation = Paths.get(new File(uploadDir, fileName).getAbsolutePath());

            try {
                Files.copy(
                        multipartFile.getInputStream(),
                        copyOfLocation,
                        StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        productImg = ProductImg.builder()
                .sourceName(sourceName)
                .fileName(fileName)
                .build();

        return productImg;
    }

    @Override
    public List<Product> selectFive() {
        return productRepository.selectFive();
    }
}




