package com.nhnacademy.shoppingmall.address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class AddressRepositoryImpl implements AddressRepository {
    @Override
    public List<Address> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        // 최근에 등록한 주소를 먼저 보여주도록 키값을 기준으로 내림차순 정렬
        String sql = "select " +
                "user_id, address" +
                "from address " +
                "where user_id = ? " +
                "order by address_id desc";

        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, userId);
            ResultSet rs = psmt.executeQuery();
            List<Address> addressList = new ArrayList<>();

            if(rs.next()){
                Address address = new Address(
                        rs.getString("user_id"),
                        rs.getString("address")
                );

                addressList.add(address);

            }

            return addressList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int save(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into " +
                "address (user_id, address) " +
                "values (?, ?)";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, address.getUserId());
            psmt.setString(2, address.getAddress().trim());

            return psmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int delete(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from address " +
                "where user_id = ? and address = ?";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, address.getUserId());
            psmt.setString(2, address.getAddress());

            return psmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Address original, String newAddress) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "update address " +
                "set address = ? " +
                "where user_id = ? and address = ?";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, newAddress.trim());
            psmt.setString(2, original.getUserId());
            psmt.setString(3, original.getAddress());

            return psmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) " +
                "from address " +
                "where user_id = ?";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, userId);
            ResultSet rs = psmt.executeQuery();;

            if(rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }
}
