package com.example.hello;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.MethodDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingClientInterceptor implements ClientInterceptor {

	private final Logger logger = LoggerFactory.getLogger(LoggingClientInterceptor.class);

	@Override
	public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
			CallOptions callOptions, Channel next) {
		logger.info("[Client] Calling {}", method.getFullMethodName());
		return next.newCall(method, callOptions);
	}

}
