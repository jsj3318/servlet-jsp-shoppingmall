package com.nhnacademy.shoppingmall.purchase.repository.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.purchase.domain.Purchase;
import com.nhnacademy.shoppingmall.purchase.repository.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PurchaseRepositoryImpl implements PurchaseRepository {
    @Override
    public int save(String user_id, String destination, BigInteger total_amount) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "inset into purchase " +
                "(user_id, purchased_at, destination, total_amount) " +
                "values (?, ?, ?, ?)";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, user_id);
            psmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            psmt.setString(3, destination);
            psmt.setBigDecimal(4, new BigDecimal(total_amount));
            return psmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int saveAndGetId(String user_id, String destination, BigInteger total_amount) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into purchase " +
                "(user_id, purchased_at, destination, total_amount) " +
                "values (?, ?, ?, ?)";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            psmt.setString(1, user_id);
            psmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            psmt.setString(3, destination);
            psmt.setBigDecimal(4, new BigDecimal(total_amount));
            psmt.executeUpdate();

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
    public List<Purchase> findByUserId(String user_id) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * " +
                "from purchase " +
                "where user_id = ? " +
                "order by purchased_at desc";
        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, user_id);
            ResultSet rs = psmt.executeQuery();

            List<Purchase> purchaseList = new ArrayList<>();

            while (rs.next()){
                Purchase purchase = new Purchase(
                    rs.getInt("purchase_id"),
                        rs.getString("user_id"),
                        rs.getTimestamp("purchased_at").toLocalDateTime(),
                        rs.getString("destination"),
                        rs.getBigDecimal("total_amount").toBigInteger()
                );
                purchaseList.add(purchase);
            }


            return purchaseList;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String user_id) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) " +
                "from purchase " +
                "where user_id = ?";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, user_id);
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
    public Page<Purchase> pagebyUserId(String user_id, int page, int pageSize) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        int offset = (page - 1) * pageSize;
        String sql = "select * " +
                "from purchase " +
                "where user_id = ?" +
                "order by purchased_at desc " +
                "limit ?,?";

        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, user_id);
            psmt.setInt(2, offset);
            psmt.setInt(3, pageSize);
            ResultSet rs = psmt.executeQuery();

            List<Purchase> purchaseList = new ArrayList<>();
            while(rs.next()){
                Purchase purchase = new Purchase(
                        rs.getInt("purchase_id"),
                        rs.getString("user_id"),
                        rs.getTimestamp("purchased_at").toLocalDateTime(),
                        rs.getString("destination"),
                        rs.getBigDecimal("total_amount").toBigInteger()
                );
                purchaseList.add(purchase);
            }

            long total = countByUserId(user_id);

            return new Page<Purchase>(purchaseList, total);

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }
}
