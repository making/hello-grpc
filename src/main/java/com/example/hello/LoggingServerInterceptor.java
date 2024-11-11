package com.example.hello;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.grpc.autoconfigure.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;

@Component
@GlobalServerInterceptor
public class LoggingServerInterceptor implements ServerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(LoggingServerInterceptor.class);

	@Override
	public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
			ServerCallHandler<ReqT, RespT> next) {
		logger.info("[Server] Calling {}", call.getMethodDescriptor().getFullMethodName());
		return next.startCall(call, headers);
	}

}
