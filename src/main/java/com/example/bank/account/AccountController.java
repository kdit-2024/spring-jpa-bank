package com.example.bank.account;

import com.example.bank._core.utils.ApiUtil;
import com.example.bank.user.SessionUser;
import com.example.bank.user.UserRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;
    private final HttpSession session;

    @PutMapping("/api/transfer")
    public ResponseEntity<?> transfer(@RequestBody AccountRequest.TransferDTO reqDTO){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        AccountResponse.TransferDTO respDTO = accountService.계좌이체(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @GetMapping("/api/accounts/{number}")
    public ResponseEntity<?> accountDetail(@PathVariable Integer number){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        AccountResponse.DetailDTO respDTO = accountService.계좌상세보기(number, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @GetMapping("/api/accounts")
    public ResponseEntity<?> accountList(){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        AccountResponse.MainDTO respDTO = accountService.계좌목록보기(sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PostMapping("/api/accounts")
    public ResponseEntity<?> withdraw(@RequestBody AccountRequest.CreateDTO reqDTO){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        AccountResponse.CreateDTO respDTO = accountService.계좌생성(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

}
