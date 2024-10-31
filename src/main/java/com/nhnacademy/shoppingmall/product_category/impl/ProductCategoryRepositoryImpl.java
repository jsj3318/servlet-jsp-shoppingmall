package com.nhnacademy.shoppingmall.product_category.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product_category.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Integer> findByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select (category_id) " +
                "from product_category " +
                "where product_id = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, productId);
            ResultSet rs =  psmt.executeQuery();

            List<Integer> categoryList = new ArrayList<>();
            while(rs.next()){
                categoryList.add(rs.getInt(1));
            }

            return categoryList;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from product_category " +
                "where product_id = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, productId);
            return psmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

}
