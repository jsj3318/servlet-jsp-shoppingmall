package com.nhnacademy.shoppingmall.product_category.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product_category.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.*;

@Slf4j
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

    @Override
    public int save(int productId, int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into product_category " +
                "values (?, ?)";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, productId);
            psmt.setInt(2, categoryId);
            return psmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }


}
