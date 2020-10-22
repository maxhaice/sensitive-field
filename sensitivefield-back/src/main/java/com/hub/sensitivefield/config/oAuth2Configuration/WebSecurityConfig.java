package com.hub.sensitivefield.config.oAuth2Configuration;

import com.hub.sensitivefield.jwt.JwtAuthEntryPoint;
import com.hub.sensitivefield.jwt.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDate;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String date = LocalDate.now().toString();
        String date1 = LocalDate.now().minusDays(1).toString();
        String date2 = LocalDate.now().minusDays(2).toString();
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers("/api/auth/signin").permitAll()
//
//                .antMatchers("/api/auth/addUser?role=user").hasAnyRole("SUPERUSER", "ADMIN")
//                .antMatchers("/api/auth/addUser?role=extendeduser").hasAnyRole("SUPERUSER", "ADMIN")
//                .antMatchers("/api/auth/addUser?role=admin").hasRole("SUPERUSER")
//
//                .antMatchers(HttpMethod.GET, "/api/history?date=" + date).authenticated()
//                .antMatchers(HttpMethod.GET, "/api/history?date=" + date1).authenticated()
//                .antMatchers(HttpMethod.GET, "/api/history?date=" + date2).authenticated()
//                .antMatchers(HttpMethod.GET, "/api/history?date=*").hasAnyRole("EXTENDER", "SUPERUSER", "ADMIN")
//
//                .antMatchers(HttpMethod.POST, "/api/audio-events").hasIpAddress("")//TODO IP server
//                .antMatchers(HttpMethod.GET, "/api/audio-events").authenticated()
//                .antMatchers(HttpMethod.GET, "/api/audio-sensors").authenticated()
//                .antMatchers(HttpMethod.POST, "api/**").hasAnyRole("SUPERUSER", "ADMIN")
//
//                .antMatchers(HttpMethod.GET, "/api/statistic*").hasAnyRole("EXTENDEDUSER", "SUPERUSER", "ADMIN")
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //1) Superuser — может абсолютно всё, в том числе создавать админов
        //2) Admin — может всё
        //3) ExtendedUser — может просматривать статистику и историю сработок
        //4) User — может следить за текущей ситуацией и просматривать историю максимум на два дня назад
    }
}
