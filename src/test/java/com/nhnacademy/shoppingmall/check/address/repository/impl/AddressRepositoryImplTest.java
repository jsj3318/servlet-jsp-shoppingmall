package com.nhnacademy.shoppingmall.check.address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class AddressRepositoryImplTest {

    AddressRepository addressRepository = new AddressRepositoryImpl();
    Address testAddress;

    // admin 계정 있어야 함

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testAddress = new Address("admin","광주 동구 서석동");
        addressRepository.save(testAddress);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("주소 조회 by userId")
    void findByUserId() {
        List<Address> addressList = addressRepository.findByUserId("admin");
        List<Address> actual = new ArrayList<>(Arrays.asList(
                new Address("admin", "광주 동구 서석동")
        ));
        Assertions.assertEquals(addressList.getFirst(), actual.getFirst()
        );
    }

    @Test
    @Order(2)
    @DisplayName("address 등록")
    void save() {
        Address newAddress = new Address("admin", "광주 충장로");
        int result = addressRepository.save(newAddress);
        Assertions.assertAll(
                ()->Assertions.assertEquals(1,result),
                ()->Assertions.assertEquals(newAddress, addressRepository.findByUserId("admin").getFirst())
        );
    }


    @Test
    @Order(3)
    @DisplayName("address 삭제")
    void deleteByUserId() {
        int result = addressRepository.delete((testAddress));
        Assertions.assertAll(
                ()->Assertions.assertEquals(1,result),
                ()->Assertions.assertTrue(addressRepository.findByUserId("admin").isEmpty())
        );
    }

    @Test
    @Order(4)
    @DisplayName("address 수정")
    void update() {

        int result = addressRepository.update(testAddress, "하남대로 248-10");
        testAddress.setAddress("하남대로 248-10");

        Assertions.assertAll(
                ()-> Assertions.assertEquals(1,result),
                ()-> Assertions.assertEquals(testAddress, addressRepository.findByUserId("admin").getFirst())
        );
    }


}
