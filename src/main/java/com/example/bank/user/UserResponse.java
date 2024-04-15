package com.example.bank.user;

import lombok.Data;

public class UserResponse {

    @Data
    public static class DTO{
        private String username ;
        private String password;
        private String fullname;

        public DTO(User user) {
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.fullname = user.getFullname();
        }
    }
}
