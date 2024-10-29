package com.nhnacademy.shoppingmall.address.repository;

import com.nhnacademy.shoppingmall.address.domain.Address;

import java.util.List;

public interface AddressRepository {
    List<Address> findByUserId(String userId);
    int save(Address address);
    int delete(Address address);
    int update(Address original, String newAddress);
    int countByUserId(String userId);
}
