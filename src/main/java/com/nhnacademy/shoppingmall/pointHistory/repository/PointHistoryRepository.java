package com.nhnacademy.shoppingmall.pointHistory.repository;

import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;

import java.util.List;

public interface PointHistoryRepository {
    List<PointHistory> findById(String userId);
    int save(PointHistory pointHistory);
    int countById(String userId);
}
