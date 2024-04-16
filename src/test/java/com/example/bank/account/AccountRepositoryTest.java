package com.example.bank.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    private static ObjectMapper om;

    @BeforeAll // 모든 테스트 메서드가 실행되기 직전마다 실행됨.
    public static void setUp(){
        om = new ObjectMapper();
        // 빈 객체가 있어도 json 변환할 때 오류 안나게 도와줌
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Test
    public void findByNumber_test() throws JsonProcessingException {
        // given
        int number = 1111;

        // when
        Optional<Account> accountOP = accountRepository.findByNumber(number);

        // eye
        Account account = accountOP.get();
        ObjectMapper om = new ObjectMapper();
        String respBody = om.writeValueAsString(account);
        System.out.println(respBody);

        // then
    }

    @Test
    public void findByUserId_test() throws JsonProcessingException {
        // given
        int userId = 1;

        // when
        List<Account> accountList = accountRepository.findByUserId(userId);

        // eye
        ObjectMapper om = new ObjectMapper();
        String respBody = om.writeValueAsString(accountList);
        System.out.println(respBody);

        // then
    }

    @Test
    public void RandomNumber_test() {
        //given
        Integer number=1111;

        //when
        do {
            number = (int) (Math.random() * 9000) + 1000;
        }while(accountRepository.findByNumber(number).isPresent());
        
        //eye
        System.out.println("number = " + number);
        
    }
}
