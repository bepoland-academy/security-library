package pl.betse.beontime.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROOT_ENDPOINT = "/**";

    private final JwtService jwtService;

    List<String> publicUrls;

    @Autowired
    public WebSecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override

    protected void configure(HttpSecurity http) throws Exception {
        configurePublicUrls(http);
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, ROOT_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    private void configurePublicUrls(HttpSecurity http) {
        publicUrls.forEach(url -> configurePublicUrl(http, url));
    }

    private void configurePublicUrl(HttpSecurity http, String url) {
        try {
            http.authorizeRequests().antMatchers(url).permitAll();
        } catch (Exception e) {
            log.error("Error configuring public URLs", e);
        }
    }

    @Value("${authentication.publicUrls}")
    public void setPublicUrls(String[] publicUrls) {
        this.publicUrls = Arrays.asList(publicUrls);
    }
}
