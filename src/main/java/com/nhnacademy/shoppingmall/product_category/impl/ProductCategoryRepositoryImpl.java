package com.nhnacademy.shoppingmall.product_category.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product_category.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.purchase_product.domain.PurchaseProduct;
import lombok.extern.slf4j.Slf4j;

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


    @Override
    public List<Category> findCategoryByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select c.category_id as id, c.category_name as name " +
                "from product_category pc " +
                    "inner join category c on c.category_id = pc.category_id " +
                "where pc.product_id = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, productId);
            ResultSet rs =  psmt.executeQuery();

            List<Category> categoryList = new ArrayList<>();
            while(rs.next()){
                categoryList.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

            return categoryList;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }



}
