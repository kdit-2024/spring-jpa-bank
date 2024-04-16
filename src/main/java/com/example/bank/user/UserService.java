package com.example.bank.user;

import com.example.bank._core.errors.exception.Exception400;
import com.example.bank._core.errors.exception.Exception401;
import com.example.bank._core.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public String 로그인(String username, String password){
        User user = userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new Exception401("유저네임 혹은 패스워드가 틀렸습니다"));
        String jwt = JwtUtil.create(user);
        return jwt;
    }

    @Transactional
    public UserResponse.DTO 회원가입(UserRequest.JoinDTO reqDTO) {
        //유저네임 중복체크
       Optional<User> userOP = userRepository.findByUsername(reqDTO.getUsername());
       if (userOP.isPresent()){
           throw new Exception400("유저네임이 이미 존재합니다.");
       }
        //회원가입
        User user = userRepository.save(reqDTO.toEntity());
        return new UserResponse.DTO(user);

    }
}
