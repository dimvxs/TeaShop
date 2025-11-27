package com.goldenleaf.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the GoldenLeaf Spring Boot application.
 * <p>
 * This class contains the entry point of the application and is annotated with
 * {@link SpringBootApplication}, which enables component scanning, auto-configuration,
 * and property support.
 * </p>
 *
 * <p>Running this class will start the embedded server and launch the application context.</p>
 *
 * @see SpringBootApplication
 * @see SpringApplication
 */
@SpringBootApplication
public class GoldenLeafApplication {

    /**
     * The main method that serves as the entry point of the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     * @see SpringApplication#run(Class, String[])
     */
    public static void main(String[] args) {
        SpringApplication.run(GoldenLeafApplication.class, args);
    }

}