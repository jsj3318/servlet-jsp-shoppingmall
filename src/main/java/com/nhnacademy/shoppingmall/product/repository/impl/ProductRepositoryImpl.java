package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public int saveAndGetId(String name, BigInteger price, String description, int quantity) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into " +
                "product (product_name, price, description, quantity) " +
                "values (?, ?, ?, ?)";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            psmt.setString(1, name.trim());
            psmt.setBigDecimal(2, new BigDecimal(price));
            psmt.setString(3, description.trim());
            psmt.setInt(4, quantity);
            psmt.executeUpdate();

            // 생성된 키(ID)를 반환
            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);  // 첫 번째 열에서 생성된 ID를 반환
                }
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return -1;
    }

    @Override
    public int update(Product product) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update product " +
                "set product_name = ?, " +
                    "price = ?, " +
                    "description = ?, " +
                    "quantity = ? " +
                "where product_id = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, product.getProduct_name());
            psmt.setBigDecimal(2, new BigDecimal(product.getPrice()));
            psmt.setString(3, product.getDescription());
            psmt.setInt(4, product.getQuantity());
            psmt.setInt(5, product.getProduct_id());
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
    public Optional<Product> findbyId(int productId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select * " +
                "from product " +
                "where product_id = ?";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setInt(1, productId);
            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("price").toBigInteger(),
                        rs.getString("description"),
                        rs.getInt("quantity")
                );
                return Optional.of(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
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
                        rs.getString("description"),
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
                        rs.getString("description"),
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
                "order by product_id desc " +
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
                        rs.getString("description"),
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
                        rs.getString("description"),
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
