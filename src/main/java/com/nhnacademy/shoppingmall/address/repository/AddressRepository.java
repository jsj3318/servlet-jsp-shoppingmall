package com.nhnacademy.shoppingmall.address.repository;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.common.page.Page;

import java.util.List;

public interface AddressRepository {
    List<Address> findByUserId(String userId);
    int save(Address address);
    int delete(Address address);
    int update(Address original, String newAddress);
    int countByUserId(String userId);

    long totalCount();
    Page<Address> findAllbyId(String userId,int page, int pageSize);

    String getAddressById(int addressId);
}
