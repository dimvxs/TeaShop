package com.goldenleaf.shop.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.goldenleaf.shop.AppMapper;

@Configuration
public class MapperConfig {

    @Bean
    public AppMapper appMapper() {
        return Mappers.getMapper(AppMapper.class);
    }
}
