package com.nhnacademy.shoppingmall.category.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public List<Category> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * " +
                "from category";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            ResultSet rs = psmt.executeQuery();

            List<Category> categoryList = new ArrayList<>();

            while (rs.next()){
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );
                categoryList.add(category);
            }


            return categoryList;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public int save(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into category(category_name) " +
                "values (?)";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, category.getName());
            return psmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deletebyId(int id) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from category " +
                "where category_id = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, id);
            return psmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(int id, String newName) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update category " +
                "set category_name = ? " +
                "where category_id = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, newName);
            psmt.setInt(2, id);
            return psmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int totalCount() {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) " +
                "from category";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

}
