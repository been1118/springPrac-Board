package com.example.springprac2jwt.jwt;

import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.springprac2jwt.dto.SecurityExceptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // JWT 토큰을 해석하여 추출
        String access_token = jwtUtil.resolveToken(request, jwtUtil.ACCESS_KEY);
        String refresh_token = jwtUtil.resolveToken(request, jwtUtil.REFRESH_KEY);

        System.out.println("1. refresh_token이 null이 아닌가? :" + refresh_token != null);
        System.out.println("2. refresh_token이 유효한가? :" + jwtUtil.refreshTokenValid(refresh_token));
        // 토큰이 존재하면 유효성 검사를 수행하고, 유효하지 않은 경우 예외 처리
        if (access_token != null) {
            if (jwtUtil.validateToken(access_token)) {
                System.out.println("3. access_token이 유효한가? :" + jwtUtil.validateToken(access_token));
                setAuthentication(jwtUtil.getUserInfoFromToken(access_token));
                System.out.println("4.  refresh_token !=null : " + refresh_token != null);
                System.out.println("5.  jwtUtil.refreshTokenValid(refresh_token) :" + jwtUtil.refreshTokenValid(refresh_token));
            } else if (refresh_token != null && jwtUtil.refreshTokenValid(refresh_token)) {
                System.out.println("6. refresh_token이 null이 아닌가? :" + refresh_token != null);
                System.out.println("7. refresh_token이 유효한가? :" + jwtUtil.refreshTokenValid(refresh_token));
                //Refresh토큰으로 유저명 가져오기
                String username = jwtUtil.getUserInfoFromToken(refresh_token);
                //유저명으로 유저 정보 가져오기
                User user = userRepository.findByUsername(username).get();
                //새로운 ACCESS TOKEN 발급
                String newAccessToken = jwtUtil.createToken(username, user.getRole(), "Access");
                //Header에 ACCESS TOKEN 추가
                jwtUtil.setHeaderAccessToken(response, newAccessToken);
                setAuthentication(username);
            } else if (refresh_token == null) {
                System.out.println("8. refresh_token 1 :" + refresh_token);
                jwtExceptionHandler(response, "AccessToken Expired.", HttpStatus.BAD_REQUEST.value());
                return;
            } else {
                System.out.println("9. refresh_token 2 : " + refresh_token);
                jwtExceptionHandler(response, "RefreshToken Expired.", HttpStatus.BAD_REQUEST.value());
                return;
            }
        }

        // 다음 필터로 요청과 응답을 전달하여 필터 체인 계속 실행
        filterChain.doFilter(request, response);
    }

    // 인증 객체를 생성하여 SecurityContext에 설정
    public void setAuthentication (String username){
        Authentication authentication = jwtUtil.createAuthentication(username);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // security가 만드는 securityContextHolder 안에 authentication을 넣음.
        // security가 sicuritycontxxtholder에서 인증 객체를 확인.
        // JwtAuthFilter에서 authentication을 넣어주면 UsernamePasswordAuthenticationFilter 내부에서 인증이 된 것을 확인하고 추가적인 작업을 진행하지 않음.
    }

    // JWT 예외 처리를 위한 응답 설정
    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            // 예외 정보를 JSON 형태로 변환하여 응답에 작성
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}


