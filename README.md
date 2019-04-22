# messaging
Messaging library for handling messages stored in a mysql database using grpc.

The library uses the Spring Boot Framework to provide a microservice that acts as a Grpc server.
It receives requests from the client via protobuf using the proto3 syntax, which it then processes using 
the Spring Boot JPARepositories and sends a protobuf response. The client has an implementation that always
sends requests directly and one that uses the Guava Cache Library and uses a 2nd level cache that updates every ten minutes.
