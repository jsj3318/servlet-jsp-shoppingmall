package com.nhnacademy.shoppingmall.address.service.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAddress(String userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void updateAddress(Address original, String newAddress) {
        addressRepository.update(original, newAddress);
    }

    @Override
    public void deleteAddress(Address address) {
        addressRepository.delete(address);
    }

}
