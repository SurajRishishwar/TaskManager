package com.suraj.TaskManager.config;



        import org.springframework.beans.factory.annotation.Autowired;

        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.authentication.AuthenticationProvider;
        import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
        import org.springframework.security.config.Customizer;
        import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.http.SessionCreationPolicy;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.web.SecurityFilterChain;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
        import org.springframework.web.cors.CorsConfiguration;
        import org.springframework.web.cors.CorsConfigurationSource;
        import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

        import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .csrf(customizer -> customizer.disable())
//                .cors(customizer -> customizer.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("register", "login","unauthtask")
                        .permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("*")); // Allow all origins
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
//        configuration.setAllowCredentials(false); // Optional, disable credentials sharing
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); // Apply CORS to all endpoints
//        return source;
//    }
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    // Allow specific paths to use CORS
    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.setAllowedOrigins(List.of("https://www.programiz.com","http://localhost:3000","https://task-manager-react1.vercel.app")); // Replace with allowed origins
    configuration.setAllowedOriginPatterns(List.of("*"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Allowed methods
    configuration.setAllowedHeaders(List.of("*")); // Allowed headers
    configuration.setAllowCredentials(true); // Allow credentials

    // Disable CORS for other requests by excluding their mappings
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/register", configuration); // Apply CORS for `/register`
    source.registerCorsConfiguration("/login", configuration); // Apply CORS for `/login`
    source.registerCorsConfiguration("/tasks", configuration);// Apply CORS for `/login`
    source.registerCorsConfiguration("/tasksforuser", configuration);
    source.registerCorsConfiguration("/unauthtask",configuration);
    // Do not register CORS for other endpoints to effectively disable it

    return source;
}



    /*
     * @Bean public UserDetailsService userDetailsService() {
     *
     * UserDetails user=User .withDefaultPasswordEncoder() .username("navin")
     * .password("n@123") .roles("USER") .build();
     *
     * UserDetails admin=User .withDefaultPasswordEncoder() .username("admin")
     * .password("admin@789") .roles("ADMIN") .build();
     *
     * return new InMemoryUserDetailsManager(user,admin); }
     */


}
