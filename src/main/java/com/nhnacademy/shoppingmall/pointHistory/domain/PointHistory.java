package com.nhnacademy.shoppingmall.pointHistory.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class PointHistory {
    private String user_id;
    private int change_amount;
    private String reason;
    private LocalDateTime created_at;

    public PointHistory(String user_id, int change_amount, String reason, LocalDateTime created_at) {
        this.user_id = user_id;
        this.change_amount = change_amount;
        this.reason = reason;
        this.created_at = created_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getChange_amount() {
        return change_amount;
    }

    public void setChange_amount(int change_amount) {
        this.change_amount = change_amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointHistory that = (PointHistory) o;
        return change_amount == that.change_amount && Objects.equals(user_id, that.user_id) && Objects.equals(reason, that.reason) && Objects.equals(created_at, that.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, change_amount, reason, created_at);
    }

}
