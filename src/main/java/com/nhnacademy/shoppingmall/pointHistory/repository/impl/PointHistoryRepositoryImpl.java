package com.nhnacademy.shoppingmall.pointHistory.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.pointHistory.repository.PointHistoryRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    @Override
    public List<PointHistory> findById(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select * " +
                "from point_history " +
                "where user_id = ? " +
                "order by history_id desc";

        log.debug("sql:{}", sql);

        try (PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, userId);
            ResultSet rs = psmt.executeQuery();
            List<PointHistory> pointHistoryList = new ArrayList<>();

            while(rs.next()){
                PointHistory pointHistory = new PointHistory(
                        rs.getString("user_id"),
                        rs.getInt("change_amount"),
                        rs.getString("reason"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );

                pointHistoryList.add(pointHistory);
            }

            return pointHistoryList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public int save(PointHistory pointHistory) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into " +
                "point_history(user_id, change_amount, reason, created_at) " +
                "values (?, ?, ?, ?)";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, pointHistory.getUser_id());
            psmt.setInt(2, pointHistory.getChange_amount());
            psmt.setString(3, pointHistory.getReason().trim());
            psmt.setTimestamp(4, Timestamp.valueOf(pointHistory.getCreated_at()));

            return psmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countById(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) " +
                "from point_history " +
                "where user_id = ?";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, userId);
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
    public long totalCount() {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) " +
                "from point_history";

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
    public Page<PointHistory> findAllbyId(String userId, int page, int pageSize) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        int offset = (page - 1) * pageSize;
        String sql = "select * " +
                "from point_history " +
                "where user_id = ?" +
                "order by history_id desc " +
                "limit ?,?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, userId);
            psmt.setInt(2,offset);
            psmt.setInt(3,pageSize);
            ResultSet rs = psmt.executeQuery();

            List<PointHistory> pointHistoryList = new ArrayList<>();
            while(rs.next()){
                PointHistory pointHistory = new PointHistory(
                        rs.getString("user_id"),
                        rs.getInt("change_amount"),
                        rs.getString("reason"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );

                pointHistoryList.add(pointHistory);
            }

            long total = 0;
            if(!pointHistoryList.isEmpty()){
                total = totalCount();
            }

            return new Page<PointHistory>(pointHistoryList, total);


        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

}
