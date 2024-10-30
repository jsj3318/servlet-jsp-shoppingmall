package com.nhnacademy.shoppingmall.category.domain;

import java.util.Objects;

public class Category {
    private final int category_id;
    private String category_name;

    public Category(int category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return category_id == category.category_id && Objects.equals(category_name, category.category_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category_id, category_name);
    }
}
