package com.example.hello;

import zipkin2.reporter.otel.brave.OtlpProtoV1Encoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HelloGrpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloGrpcApplication.class, args);
	}

	@Bean
	public OtlpProtoV1Encoder otlpProtoV1Encoder() {
		return OtlpProtoV1Encoder.create();
	}

}
