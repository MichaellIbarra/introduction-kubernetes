package dev.matichelo.msvc.auth.services;

import dev.matichelo.msvc.auth.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private WebClient.Builder webClient;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            User user = webClient
                    .build()
                    .get()
                    .uri("http://msvc-user/login", uri -> uri.queryParam("email", email).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(User.class).block();
            return new org.springframework.security.core.userdetails.User(email, user.getPassword(), true, true, true, true, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        } catch (RuntimeException e){
            String error = "Error: " + e.getMessage();
            throw new UsernameNotFoundException(error);
        }

    }
}
