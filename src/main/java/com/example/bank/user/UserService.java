package com.example.bank.user;

import com.example.bank._core.errors.exception.Exception400;
import com.example.bank._core.errors.exception.Exception401;
import com.example.bank._core.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public String 로그인(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new Exception401("유저네임 혹은 패스워드가 틀렸습니다"));
        String jwt = JwtUtil.create(user);
        return jwt;
    }

    @Transactional
    public UserResponse.JoinDTO 회원가입(UserRequest.JoinDTO reqDTO) {
        // 1. username 중복 검사
        if (userRepository.findByUsername(reqDTO.getUsername()).isPresent()) {
            throw new Exception400("중복된 username이 존재합니다.");
        }
        // 2. username 유효성 검사
        if (reqDTO.getUsername().length() < 4 || reqDTO.getUsername().length() > 20) {
            throw new Exception400("username은 4자 이상 20자 이내이어야 합니다.");
        }
        // 2. email 중복 검사
        if (userRepository.findByEmail(reqDTO.getEmail()).isPresent()) {
            throw new Exception400("중복된 email이 존재합니다.");
        }
        // 3. password 유효성 검사
        if (reqDTO.getPassword().length() < 4 || reqDTO.getPassword().length() > 20) {
            throw new Exception400("password는 4자이상 20자 이내여야 합니다.");
        }

        User savedUser = userRepository.save(User.builder()
                .username(reqDTO.getUsername())
                .password(reqDTO.getPassword())
                .email(reqDTO.getEmail())
                .fullname(reqDTO.getFullname())
                .build());
        String jwt = JwtUtil.create(savedUser);
        UserResponse.JoinDTO respDTO = new UserResponse.JoinDTO(savedUser, jwt);
        return respDTO;
    }
}
