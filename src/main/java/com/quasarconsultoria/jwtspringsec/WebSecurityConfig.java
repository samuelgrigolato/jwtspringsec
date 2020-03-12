package com.quasarconsultoria.jwtspringsec;

import com.quasarconsultoria.jwtspringsec.login.JWTBasicAuthenticationFilter;
import com.quasarconsultoria.jwtspringsec.login.JWTUsernamePasswordAuthenticationFilter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .permitAll()
                    .and()
                .addFilter(jwtUsernamePasswordAuthenticationFilter())
                .addFilter(jwtBasicAuthenticationFilter())
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .logout()
                    .permitAll();
    }

    @Bean
    public JWTUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter() throws Exception {
        JWTUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter = new JWTUsernamePasswordAuthenticationFilter();
        jwtUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return jwtUsernamePasswordAuthenticationFilter;
    }

    @Bean
    public JWTBasicAuthenticationFilter jwtBasicAuthenticationFilter() throws Exception {
        return new JWTBasicAuthenticationFilter(authenticationManager());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(this.dataSource)
                .usersByUsernameQuery("select login, senha, 1 from usuarios where login = ?")
                .authoritiesByUsernameQuery("select ?, 'ROLE_USER';");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtils.sha1Hex(rawPassword + "algoaqui");
            }
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return DigestUtils.sha1Hex(rawPassword + "algoaqui").equals(encodedPassword);
            }
        };
    }
}
