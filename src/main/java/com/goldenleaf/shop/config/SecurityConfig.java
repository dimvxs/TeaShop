package com.goldenleaf.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}