package com.example.bank.user;

import lombok.Data;

public class UserRequest {

    @Data
    public static class JoinDTO{
        private String username;
        private String password;
        private String fullname;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .fullname(fullname)
                    .build();
        }
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }
}
