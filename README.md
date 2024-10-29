# Demo Spring gRPC

```
brew install grpcurl
```

```
./mvnw spring-boot:run
```

```
$ grpcurl -plaintext -d '{"name":"World"}' -proto src/main/proto/hello.proto -rpc-header 'traceparent: 00-0af7651916cd43dd8448eb211c80319c-b9c7c989f97918e1-01' 127.0.0.1:8080 com.example.hello.Greeter.SayHello 
{
  "message": "Hello World!"
}
```

Go to http://localhost:9411/zipkin/traces/0af7651916cd43dd8448eb211c80319c

<img width="1024" alt="image" src="https://github.com/user-attachments/assets/d3dd48e9-6f4a-4150-8a32-54edacfe4f9f">

```
./mvnw test
```

Go to the zipkin and you'll see a trace including client spans like below:

<img width="1024" alt="image" src="https://github.com/user-attachments/assets/896433ba-5c28-4102-86f3-70390e98b2b5">
