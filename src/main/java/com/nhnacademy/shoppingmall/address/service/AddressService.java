package com.nhnacademy.shoppingmall.address.service;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.user.domain.User;

import java.util.List;

public interface AddressService {
    List<Address> getAddress(String userId);

    void saveAddress(Address address);

    void updateAddress(Address original, String newAddress);

    void deleteAddress(Address address);

}
