package com.currency.app.currencyapp.security.service;

import com.currency.app.currencyapp.exception.CustomException;
import com.currency.app.currencyapp.security.dto.CreateUserDto;
import com.currency.app.currencyapp.security.dto.LoginDto;
import com.currency.app.currencyapp.security.dto.TokenDto;
import com.currency.app.currencyapp.security.entity.User;
import com.currency.app.currencyapp.security.enums.Role;
import com.currency.app.currencyapp.security.jwt.JwtProvider;
import com.currency.app.currencyapp.security.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public Mono<TokenDto> login(LoginDto loginDto) {

        Mono<User> block = userRepository.findByUsername(loginDto.getUsername())
                .doOnNext(user -> System.out.println("USERNAME: " + user))
                .doOnSuccess(user -> System.out.println("DOONSUCCESS: " + user))
                .log();

        log.debug("usuario: {}", block);

        return userRepository.findByUsername(loginDto.getUsername())
                .filter(user -> {
                    String val = "";
                    return passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
                })
                .map(user -> new TokenDto(jwtProvider.generateToken(user, new HashMap<>() {{
                    put("id", user.getId());
                }})))
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "bad credentials")));
    }

    public Mono<User> create(CreateUserDto createUserDto) {
        User user = User.builder()
                .username(createUserDto.getUsername())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .roles(Role.ROLE_ADMIN.name() + ", " + Role.ROLE_USER.name())
                .build();

        Mono<Boolean> userExists = userRepository.findByUsername(createUserDto.getUsername()).hasElement();
        return userExists.flatMap(myBool -> myBool
                ? Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "user already exists"))
                : userRepository.save(user));
    }

}
