package com.example.bank.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserService.class)
@DataJpaTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void join_test(){
        // given
//        private String username;
//        private String password;
//        private String email;
//        private String fullname;
        UserRequest.JoinDTO reqDTO = new UserRequest.JoinDTO();
        reqDTO.setUsername("ssar1");
        reqDTO.setPassword("1234");
        reqDTO.setEmail("ssar1@nate.com");
        reqDTO.setFullname("ssar Kim");

        // when
        UserResponse.JoinDTO respDTO = userService.회원가입(reqDTO);
        // then
        System.out.println(respDTO.toString());
    }
}
