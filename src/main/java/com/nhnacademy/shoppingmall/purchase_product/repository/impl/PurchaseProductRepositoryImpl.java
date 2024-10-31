package com.nhnacademy.shoppingmall.purchase_product.repository.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.purchase_product.domain.PurchaseProduct;
import com.nhnacademy.shoppingmall.purchase_product.repository.PurchaseProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PurchaseProductRepositoryImpl implements PurchaseProductRepository {
    @Override
    public int save(int purchase_id, int product_id, int quantity) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into purchase_product " +
                "values (?, ?, ?)";

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, purchase_id);
            psmt.setInt(2, product_id);
            psmt.setInt(3, quantity);
            return psmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public long countByPurchaseId(int purchase_id) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) " +
                "from purchase_product " +
                "where purchase_id = ?";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setInt(1, purchase_id);
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
    public Page<PurchaseProduct> pageByPurchaseId(int purchase_id, int page, int pageSize) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        int offset = (page - 1) * pageSize;
        String sql = "select * " +
                "from purchase_product " +
                "where purchase_id = ?" +
                "limit ?,?";

        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1,purchase_id);
            psmt.setInt(2,offset);
            psmt.setInt(3,pageSize);
            ResultSet rs = psmt.executeQuery();

            List<PurchaseProduct> purchaseProductList = new ArrayList<>();

            while (rs.next()){
                PurchaseProduct purchaseProduct = new PurchaseProduct(
                        rs.getInt("purchase_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );
                purchaseProductList.add(purchaseProduct);
            }

            long total = countByPurchaseId(purchase_id);

            return new Page<PurchaseProduct>(purchaseProductList, total);


        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
