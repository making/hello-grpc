package com.example.hello;

import java.time.Duration;

import com.example.hello.proto.GreeterGrpc;
import com.example.hello.proto.HelloReply;
import com.example.hello.proto.HelloRequest;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.grpc.client.GrpcChannelFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = "spring.docker.compose.skip.in-tests=false")
@AutoConfigureObservability
class HelloGrpcApplicationTests {

	GreeterGrpc.GreeterBlockingStub stub;

	@Autowired
	ObservationRegistry observationRegistry;

	@BeforeEach
	void setUp(@Autowired GrpcChannelFactory channels, @LocalServerPort int port) {
		this.stub = GreeterGrpc.newBlockingStub(channels.createChannel("localhost:" + port)
			.intercept(new LoggingClientInterceptor(), new ObservationGrpcClientInterceptor(observationRegistry))
			.build());
	}

	@Test
	void contextLoads() throws Exception {
		HelloReply reply = Observation.createNotStarted("grpc.client", this.observationRegistry)
			.observe(() -> this.stub.sayHello(HelloRequest.newBuilder().setName("World").build()));
		assertThat(reply).isNotNull();
		assertThat(reply.getMessage()).isEqualTo("Hello World!");
	}

}
