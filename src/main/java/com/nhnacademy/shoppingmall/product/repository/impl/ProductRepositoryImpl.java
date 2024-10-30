package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public int save(Product product) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into " +
                "product (product_name, price, thumbnail_uri, description, image_uri, quantity) " +
                "values (?, ?, ?, ?, ?, ?)";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, product.getProduct_name().trim());
            psmt.setBigDecimal(2, new BigDecimal(product.getPrice()));
            psmt.setString(3, product.getThumnail_uri().trim());
            psmt.setString(4, product.getDescription().trim());
            psmt.setString(5, product.getImage_uri().trim());
            psmt.setInt(6, product.getQuantity());
            return psmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Product product) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update product " +
                "set product_name = ?, " +
                    "price = ?, " +
                    "thumbnail_uri = ?, " +
                    "description = ?, " +
                    "image_uri = ?, " +
                    "quantity = ? " +
                "where product_id = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, product.getProduct_name());
            psmt.setBigDecimal(2, new BigDecimal(product.getPrice()));
            psmt.setString(3, product.getThumnail_uri());
            psmt.setString(4, product.getDescription());
            psmt.setString(5, product.getImage_uri());
            psmt.setInt(6, product.getQuantity());
            psmt.setInt(7, product.getProduct_id());
            return psmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deletebyId(int id) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from product " +
                "where product_id = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, id);
            return psmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public long countAll() {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) from product";

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

    @Override
    public long countbyCategoryId(int categoryId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) " +
                "from product p " +
                    "inner join product_category pc on pc.product_id = p.product_id " +
                "where pc.category_id = ?";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setInt(1, categoryId);
            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;

    }

    @Override
    public List<Product> findAll() {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select * " +
                "from product";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            ResultSet rs = psmt.executeQuery();
            List<Product> productList = new ArrayList<>();
            while(rs.next()){
                productList.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("price").toBigInteger(),
                        rs.getString("thumbnail_uri"),
                        rs.getString("description"),
                        rs.getString("image_uri"),
                        rs.getInt("quantity")
                ));
            }

            return productList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Product> findbyCategoryId(int categoryId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select * " +
                "from product p" +
                "inner join product_category pc on pc.product_id = p.product_id " +
                "where pc.category_id = ?";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setInt(1, categoryId);
            ResultSet rs = psmt.executeQuery();
            List<Product> productList = new ArrayList<>();
            while(rs.next()){
                productList.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("price").toBigInteger(),
                        rs.getString("thumbnail_uri"),
                        rs.getString("description"),
                        rs.getString("image_uri"),
                        rs.getInt("quantity")
                ));
            }

            return productList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Page<Product> pageAll(int page, int pageSize) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        int offset = (page - 1) * pageSize;
        String sql = "select * " +
                "from product " +
                "limit ?,?";

        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1,offset);
            psmt.setInt(2,pageSize);
            ResultSet rs = psmt.executeQuery();
            List<Product> productList = new ArrayList<>();
            while(rs.next()){
                productList.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("price").toBigInteger(),
                        rs.getString("thumbnail_uri"),
                        rs.getString("description"),
                        rs.getString("image_uri"),
                        rs.getInt("quantity")
                ));
            }

            long total = countAll();

            return new Page<Product>(productList, total);


        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Page<Product> pagebyCategoryId(int categoryId, int page, int pageSize) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        int offset = (page - 1) * pageSize;
        String sql = "select * " +
                "from product p " +
                    "inner join product_category pc on pc.product_id = p.product_id " +
                "where pc.category_id = ? " +
                "limit ?,?";

        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, categoryId);
            psmt.setInt(2, offset);
            psmt.setInt(3, pageSize);
            ResultSet rs = psmt.executeQuery();
            List<Product> productList = new ArrayList<>();
            while(rs.next()){
                productList.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("price").toBigInteger(),
                        rs.getString("thumbnail_uri"),
                        rs.getString("description"),
                        rs.getString("image_uri"),
                        rs.getInt("quantity")
                ));
            }

            long total = countbyCategoryId(categoryId);

            return new Page<Product>(productList, total);


        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

}
