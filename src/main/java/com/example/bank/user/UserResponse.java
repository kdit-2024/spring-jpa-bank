package com.example.bank.user;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

public class UserResponse {

    @Data
    public static class JoinDTO {
        private Integer id;
        private String username;
        private String fullname;
        private String email;
        private String jwt;
        private Timestamp createdAt;


        public JoinDTO(User user, String jwt) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullname = user.getFullname();
            this.email = user.getEmail();
            this.jwt = jwt;
            this.createdAt = user.getCreatedAt();
        }
    }
}
