package com.example.hello;

import com.example.hello.proto.GreeterGrpc;
import com.example.hello.proto.HelloReply;
import com.example.hello.proto.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class GreeterService extends GreeterGrpc.GreeterImplBase {

	private final Logger logger = LoggerFactory.getLogger(GreeterService.class);

	@Override
	public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
		logger.info("sayHello request: {}", request.toString().trim());
		HelloReply reply = HelloReply.newBuilder().setMessage("Hello %s!".formatted(request.getName())).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

}
