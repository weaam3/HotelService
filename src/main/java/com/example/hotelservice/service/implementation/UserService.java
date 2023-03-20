package com.example.hotelservice.service.implementation;

import com.example.hotelservice.model.User;
import com.example.hotelservice.service.interfaces.IUserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService implements IUserService {

    private final WebClient authWebClient;

    public UserService(WebClient authWebClient) {
        this.authWebClient = authWebClient;
    }

    @Override
    public User save(User user) {
        return authWebClient.post()
                .uri("/user/signUp")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    @Override
    public User getByUserName(String username, String password) {
        return authWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/signIn")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new User(0, username, password, "", 0)), User.class)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}
