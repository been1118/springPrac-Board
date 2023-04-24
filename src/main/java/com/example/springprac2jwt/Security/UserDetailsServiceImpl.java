package com.example.springprac2jwt.Security;

import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 주어진 사용자명으로 사용자를 찾는다.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // UserDetailsImpl을 사용하여 Spring Security의 UserDetails 인터페이스를 구현하는 사용자 정보 객체를 반환한다.
        return new UserDetailsImpl(user, user.getUsername());
    }

}
