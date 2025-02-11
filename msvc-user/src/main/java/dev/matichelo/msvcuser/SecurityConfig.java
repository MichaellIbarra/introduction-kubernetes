package dev.matichelo.msvcuser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                (authorize) -> authorize
                        .requestMatchers("/authorized", "/login").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/").permitAll()
                        .requestMatchers(HttpMethod.GET,"/", "/{id}").hasAnyAuthority("SCOPE_read", "SCOPE_write")
                        .requestMatchers(HttpMethod.POST,"/").hasAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.PUT,"/{id}").hasAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.DELETE,"/{id}").hasAuthority("SCOPE_write")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login( oauth2 -> oauth2.loginPage("/oauth2/authorization/msvc-user-client") )
                .oauth2Client(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer( oauth2 -> oauth2.jwt(withDefaults()) );

        return http.build();
    }


}
