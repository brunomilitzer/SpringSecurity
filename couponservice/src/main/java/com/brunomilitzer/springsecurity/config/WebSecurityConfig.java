package com.brunomilitzer.springsecurity.config;

import com.brunomilitzer.springsecurity.security.UserDetailsServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().mvcMatchers(HttpMethod.GET, "/couponapi/coupons/{code:^[A-Z]*$}",
            "/index", "/showGetCoupon", "/couponDetails")
            .hasAnyRole("USER", "ADMIN")
            .mvcMatchers(HttpMethod.GET, "/showCreateCoupon", "/createCoupon", "/createResponse").hasRole("ADMIN")
            .mvcMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("USER", "ADMIN")
            .mvcMatchers(HttpMethod.POST, "/couponapi/coupons", "/saveCoupon", "/getCoupon").hasRole("ADMIN")
            .mvcMatchers("/", "/login", "/showReg", "/registerUser").permitAll()
            .anyRequest().denyAll().and().csrf().and().logout().logoutSuccessUrl("/");

        // Custom cors configuration
        /*http.cors(corsCustomizer -> {
            CorsConfigurationSource configurationSource = request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("localhost:3000"));
                corsConfiguration.setAllowedMethods(List.of("GET"));

                return corsConfiguration;
            };

            corsCustomizer.configurationSource(configurationSource);
        });*/

        /*http.csrf(csrfCustomizer-> {
            csrfCustomizer.ignoringAntMatchers("/couponapi/coupons/**");
            RequestMatcher requestMatchers = new RegexRequestMatcher("/couponapi/coupons/{code:^[A-Z]*$}", "POST");
            requestMatchers = new MvcRequestMatcher(new HandlerMappingIntrospector(), "/getCoupon");
            csrfCustomizer.ignoringRequestMatchers(requestMatchers);
        });*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
