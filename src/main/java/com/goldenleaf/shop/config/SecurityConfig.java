package com.goldenleaf.shop.config;

import org.springframework.security.config.Customizer;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Application-wide security configuration.
 * <p>
 * Defines beans used throughout the application for cryptographic operations,
 * primarily password hashing and verification.
 * </p>
 * <p>
 * Current implementation uses {@link BCryptPasswordEncoder} — the de-facto industry standard
 * in 2025 for password storage:
 * <ul>
 *   <li>Adaptive hashing algorithm with configurable work factor (strength)</li>
 *   <li>Built-in random salt generation (prevents rainbow table attacks)</li>
 *   <li>Recommended by OWASP, Spring Security, NIST, and used by GitHub, Dropbox, etc.</li>
 *   <li>Future-proof: strength can be increased without breaking existing hashes</li>
 * </ul>
 * </p>
 * <p>
 * Default strength = 10 (balanced between security and performance).
 * Can be increased to 12+ on powerful servers or decreased to 8–10 on low-end hardware.
 * </p>
 *
 * @author GoldenLeaf Team
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Provides a global {@link PasswordEncoder} bean.
     * <p>
     * Autowired wherever passwords need to be encoded or verified
     * (e.g., registration, login, admin user creation).
     * </p>
     * <p>
     * Using strength = 10 by default:
     * <ul>
     *   <li>~100–200 ms per hash on modern hardware — acceptable for login</li>
     *   <li>Strong enough against brute-force and GPU cracking in 2025</li>
     * </ul>
     * </p>
     *
     * @return BCryptPasswordEncoder instance with default strength
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // You can customize strength if needed:
        // return new BCryptPasswordEncoder(12); // stronger
        return new BCryptPasswordEncoder();
    }
    
    
   

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable())
//            .cors(Customizer.withDefaults())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // preflight
//                .requestMatchers("/api/customers/register", "/api/customers/login").permitAll()
//                .anyRequest().authenticated()
//            )
//            .formLogin(form -> form.disable()) // отключаем стандартную форму, если фронт сам логинит
//            .sessionManagement(session -> session
//                .maximumSessions(1) // можно ограничить 1 сессию на пользователя
//            )
//            .logout(logout -> logout.permitAll()); // разлогинивание разрешено всем
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())                    // CSRF не нужен для API
            .cors(Customizer.withDefaults())                 // CORS уже настроен отдельно

//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .requestMatchers("/api/customers/register", "/api/customers/login").permitAll()
//                .requestMatchers("/api/products/**").permitAll()
//                .requestMatchers("/api/cart/**").hasAnyRole("USER", "ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/cart/add").permitAll() 
//                .anyRequest().permitAll()                    // ← Вот главное изменение
//            )
            
//            .authorizeHttpRequests(auth -> auth
//            	    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//            	    .requestMatchers("/api/customers/register", "/api/customers/login").permitAll()
//            	    .requestMatchers("/api/products/**").permitAll()
//            	    .requestMatchers("/api/cart/add").permitAll() // аноним может добавить
//            	    .requestMatchers("/api/cart", "/api/cart/**").authenticated() // но видеть/менять — только залогиненный
//            	    .anyRequest().permitAll() // или permitAll(), если есть другие публичные эндпоинты
//            	)
            
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            	    .requestMatchers("/api/customers/register", "/api/customers/login").permitAll()
            	    .requestMatchers("/api/products/**").permitAll()
            	    
            	    // Разрешаем добавление анонимам
            	    .requestMatchers(HttpMethod.POST, "/api/cart/add").permitAll()
            	    
            	    // А чтение/изменение/удаление — только залогиненным
            	    .requestMatchers("/api/cart", "/api/cart/**").authenticated()
            	    
            	    .anyRequest().permitAll() // или authenticated(), как тебе нужно
            	)

            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
       //     .logout(logout -> logout.disable())

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
            );

//            .sessionManagement(session -> session
//            	    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//            	);

        return http.build();
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() { // allow CORS for frontend dev server
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .exposedHeaders("Set-Cookie")
                        .allowCredentials(true);
            }
            
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations("file:uploads/")
                        .addResourceLocations("file:/Users/dima/Downloads/uploads/")
                        .addResourceLocations("file:/Users/dima/Desktop/shop/uploads/")
                        .addResourceLocations("file:/Users/dima/Downloads/");
               
            }
        };
        
        
    }
    
   
    
    

}



