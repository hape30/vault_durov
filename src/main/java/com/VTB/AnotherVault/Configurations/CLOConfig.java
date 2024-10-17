package com.VTB.AnotherVault.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class CLOConfig {
    @Bean
    public S3Client cloClient(){
        return S3Client.builder()
                .endpointOverride(URI.create("https://storage.clo.ru"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("0X7YS1KA35NDPAFTRVSD", "WOupOYpL0lCU4TNs3ceDCOBtL2u2cc2wUIg8hH00")))
                .region(Region.of("default"))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build();
    }
}
