package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.LoginRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.dto.SignupRequestDto;
import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.entity.UserRole;
import com.example.springprac2jwt.exception.CustomException;
import com.example.springprac2jwt.jwt.JwtUtil;
import com.example.springprac2jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.example.springprac2jwt.exception.ErrorCode.CANNOT_FOUND_USER;
import static com.example.springprac2jwt.exception.ErrorCode.NOT_MATCH_ADMIN_TOKEN;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public ResponseDto<?> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String adminToken = signupRequestDto.getAdminToken();
        //어드민 토큰 확인
        if(!adminToken.equals("")) {
            signupRequestDto.setAdmin(true);
        }

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()) {
            return ResponseDto.set(false, 409, "중복된 사용자가 있습니다.");
        }

        //관리자 확인
         UserRole role = UserRole.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomException(NOT_MATCH_ADMIN_TOKEN);
            }
            role = UserRole.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
        return ResponseDto.setSuccess(username);
    }
    //로그인
    @Transactional
    public ResponseDto<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(CANNOT_FOUND_USER)
        );
        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            return ResponseDto.set(false, 401, "비밀번호가 일치하지 않습니다.");
        }

        //아이디 정보로 토큰 생성
        TokenDto tokenDto = jwtUtil.creatAllToken(username, user.getRole());

        //Refresh토큰 있는지 확인
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(username);

        /*
        Refresh토큰 존재O -> 새로 발급후 업데이트
        Refresh토큰 존재X -> 새로 생성후 DB에 저장
         */
        if(refreshToken.isPresent()){
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), username);
            refreshTokenRepository.save(newToken);
        }
        //response 헤더에 AccessToken / RefreshToken
        setHeader(response, tokenDto);
        //response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return ResponseDto.setSuccess(user);
    }
}