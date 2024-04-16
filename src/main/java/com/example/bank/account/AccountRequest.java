package com.example.bank.account;

import lombok.Data;

public class AccountRequest {
    @Data
    public static class TransferDTO {
        private Integer senderNumber;
        private Integer receiverNumber;
        private Long amount;
        private String senderPassword;
    }

    @Data
    public static class SaveDTO {

        private String password;
        private Long balance;
    }
}
