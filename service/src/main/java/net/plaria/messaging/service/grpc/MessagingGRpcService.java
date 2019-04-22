package net.plaria.messaging.service.grpc;

import io.grpc.stub.StreamObserver;
import net.plaria.messaging.proto.MessageServiceGrpc;
import net.plaria.messaging.proto.Messaging;
import net.plaria.messaging.service.message.Message;
import net.plaria.messaging.service.message.MessageRepository;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GRpcService
public class MessagingGRpcService extends MessageServiceGrpc.MessageServiceImplBase {

  private MessageRepository messageRepository;

  @Autowired
  private MessagingGRpcService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @Override
  public void findMessageById(
    Messaging.ProtoInt request, StreamObserver<Messaging.MessageResponse> responseObserver) {
    Optional<Message> message = this.messageRepository.findById((long) request.getValue());
    if (message.isPresent()) {
      responseObserver.onNext(
          Messaging.MessageResponse.newBuilder()
              .setErrorState(Messaging.ErrorState.SUCCESS)
              .setMessage(message.get().toProto())
              .build());
      responseObserver.onCompleted();
      return;
    }
    responseObserver.onNext(
        Messaging.MessageResponse.newBuilder().setErrorState(Messaging.ErrorState.ERROR).build());
    responseObserver.onCompleted();
  }

  @Override
  public void findMessageByKey(
      Messaging.ProtoString request, StreamObserver<Messaging.MessageResponse> responseObserver) {
    Optional<Message> message = this.messageRepository.findByKey(request.getValue());
    if (message.isPresent()) {
      responseObserver.onNext(
        Messaging.MessageResponse.newBuilder()
          .setErrorState(Messaging.ErrorState.SUCCESS)
          .setMessage(message.get().toProto())
          .build());
      responseObserver.onCompleted();
      return;
    }
    responseObserver.onNext(
      Messaging.MessageResponse.newBuilder().setErrorState(Messaging.ErrorState.ERROR).build());
    responseObserver.onCompleted();
  }
}
