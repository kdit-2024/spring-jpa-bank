package com.example.bank.account;

import com.example.bank._core.errors.exception.Exception400;
import com.example.bank._core.errors.exception.Exception403;
import com.example.bank._core.errors.exception.Exception404;
import com.example.bank.history.History;
import com.example.bank.history.HistoryRepository;
import com.example.bank.user.SessionUser;
import com.example.bank.user.User;
import com.example.bank.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;

    public AccountResponse.MainDTO 계좌목록보기(int userId) {
        List<Account> accountList = accountRepository.findByUserId(userId);
        return new AccountResponse.MainDTO(accountList);
    }

    public AccountResponse.DetailDTO 계좌상세보기(int number, SessionUser sessionUser) {
        Account account = accountRepository.findByNumber(number)
                .orElseThrow(() -> new Exception404("조회하신 계좌가 존재하지 않습니다"));

        if (account.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("해당 계좌를 조회할 권한이 없습니다");
        }

        List<History> historyList = historyRepository.findBySenderOrReceiver(number);
        return new AccountResponse.DetailDTO(account, historyList, number);
    }

    @Transactional
    public AccountResponse.TransferDTO 계좌이체(AccountRequest.TransferDTO reqDTO, SessionUser sessionUser) {
        // 1. 출금계좌와 입금계좌가 동일하면 안됨
        if(reqDTO.getSenderNumber().equals(reqDTO.getReceiverNumber())){
            throw new Exception400("동일 계좌로 이체할 수 없습니다");
        }

        // 2. 0원 이하는 들어오면 안됨
        if(reqDTO.getAmount() <= 0){
            throw new Exception400("0원이하 금액은 이체할 수 없습니다");
        }

        // 3. 입금출 계좌 조회
        Account senderAccount = accountRepository.findByNumber(reqDTO.getSenderNumber())
                .orElseThrow(() -> new Exception404("출금 계좌가 존재하지 않습니다"));
        Account receiverAccount = accountRepository.findByNumber(reqDTO.getReceiverNumber())
                .orElseThrow(() -> new Exception404("입금 계좌가 존재하지 않습니다"));

        // 4. 출금 소유자 확인
        if(senderAccount.getUser().getId() != sessionUser.getId()){
            throw new Exception403("출금계좌의 주인이 아닙니다");
        }

        // 5. 출금 계좌 비번 확인 // 🚨🚨🚨🚨reqDTO
        if(!senderAccount.getPassword().equals(reqDTO.getSenderPassword())){
            throw new Exception403("출금 계좌 비밀번호가 틀렸습니다");
        }

        // 6. 잔액 검증
        senderAccount.lackCheck(reqDTO.getAmount());

        // 7. 계좌 상태 변경
        senderAccount.withdraw(reqDTO.getAmount());
        receiverAccount.deposit(reqDTO.getAmount());

        // 8. 계좌 이체 히스토리 기록 (히스토리 기록은 반드시 계좌 상태 변경 후에 해야함)
        History history = History.builder()
                .sender(senderAccount)
                .receiver(receiverAccount)
                .amount(reqDTO.getAmount())
                .senderBalance(senderAccount.getBalance())
                .receiverBalance(receiverAccount.getBalance())
                .build();

        historyRepository.save(history);
        return new AccountResponse.TransferDTO(history);
    }

    @Transactional
    public AccountResponse.CreateDTO 계좌생성(AccountRequest.CreateDTO reqDTO, SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new Exception404("유저가 존재하지 않습니다"));

        //계좌 번호 랜덤 생성
        Integer number=null;
        do {
            number = (int) (Math.random() * 9000) + 1000;
        }while(accountRepository.findByNumber(number).isPresent());

        //계좌 생성
        Account account = Account.builder()
                .user(user)
                .number(number)
                .password(reqDTO.getPassword())
                .balance(reqDTO.getBalance())
                .status(true)
                .build();

        Account createdAccount=accountRepository.save(account);
        return new AccountResponse.CreateDTO(createdAccount, user);
    }
}
