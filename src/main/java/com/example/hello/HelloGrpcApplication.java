package com.example.hello;

import java.util.List;

import io.grpc.BindableService;
import io.grpc.ServerInterceptor;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.grpc.servlet.jakarta.ServletServerBuilder;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcServerInterceptor;
import io.micrometer.observation.ObservationRegistry;
import zipkin2.reporter.otel.brave.OtlpProtoV1Encoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.server.ServerBuilderCustomizer;

@SpringBootApplication
public class HelloGrpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloGrpcApplication.class, args);
	}

	@Bean
	public BindableService serverReflection() {
		return ProtoReflectionService.newInstance();
	}

	@Bean
	public ObservationGrpcServerInterceptor observationGrpcServerInterceptor(ObservationRegistry observationRegistry) {
		return new ObservationGrpcServerInterceptor(observationRegistry);
	}

	@Bean
	public ServerBuilderCustomizer<ServletServerBuilder> servletServerBuilder(List<ServerInterceptor> interceptors) {
		return builder -> interceptors.forEach(builder::intercept);
	}

	@Bean
	public OtlpProtoV1Encoder otlpProtoV1Encoder() {
		return OtlpProtoV1Encoder.create();
	}

}
