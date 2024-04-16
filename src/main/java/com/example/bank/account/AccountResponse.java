package com.example.bank.account;

import com.example.bank.history.History;
import com.example.bank.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AccountResponse {

    // 계좌 이체 응답 화면
    @Data
    public static class TransferDTO {
        private Long historyId;
        private Integer senderNumber;
        private Integer receiverNumber;
        private Long amount;
        private Long myBalance; // 보낸이 잔액
        private Timestamp createdAt;

        public TransferDTO(History history) {
            this.historyId = history.getId();
            this.senderNumber = history.getSender().getNumber();
            this.receiverNumber = history.getReceiver().getNumber();
            this.amount = history.getAmount();
            this.myBalance = history.getSenderBalance();
            this.createdAt = history.getCreatedAt();
        }
    }

    // 계좌 상세보기 화면
    @Data
    public static class DetailDTO {
        private Integer userId;
        private String fullname;
        private Integer number;
        private Long balance; // 현재 계좌 잔액

        private List<HistoryDTO> histories;

        public DetailDTO(Account account, List<History> historyList, int searchNumber) {
            this.userId = account.getUser().getId();
            this.fullname = account.getUser().getFullname();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.histories = historyList.stream().map(history ->
                new HistoryDTO(history, searchNumber)
            ).toList();
        }

        @Data
        class HistoryDTO {
            private Integer senderNumber;
            private Integer receiverNumber;
            private Long amount;
            private Long balance; // 그때 잔액
            private String gubun; // 입금, 출금

            public HistoryDTO(History history, int searchNumber) {
                this.senderNumber = history.getSender().getNumber();
                this.receiverNumber = history.getReceiver().getNumber();
                this.amount = history.getAmount();

                if(history.getSender().getNumber() == searchNumber){
                    this.balance = history.getSenderBalance();
                    this.gubun = "출금";
                }else{
                    this.balance = history.getReceiverBalance();
                    this.gubun = "입금";
                }

            }
        }
    }

    // 계좌 목록보기 화면
    @Data
    public static class MainDTO{
        private int userId;
        private String fullname;

        private List<AccountDTO> accounts;

        public MainDTO(List<Account> accountList) {
            this.userId = accountList.get(0).getUser().getId();
            this.fullname = accountList.get(0).getUser().getFullname();
            this.accounts = accountList.stream().map(AccountDTO::new).toList();
        }

        @Data
        class AccountDTO {
            private Long accountId;
            private Integer number;
            private Long balance;

            public AccountDTO(Account account) {
                this.accountId = account.getId();
                this.number = account.getNumber();
                this.balance = account.getBalance();
            }
        }
    }

    @Data
    public static class SaveDTO {
        private String username;
        private String fullname;
        private Integer number;
        private Long balance;

        public SaveDTO(Account account, User user) {
            this.username = user.getUsername();
            this.fullname = user.getFullname();
            this.number = account.getNumber();
            this.balance = account.getBalance();
        }
    }
}
