package net.plaria.messaging.service.message;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import net.plaria.messaging.proto.Messaging;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message {

  @Id
  @GenericGenerator(name = "increment", strategy = "increment")
  @GeneratedValue(generator = "increment")
  private long id;

  @Column(name = "`key`")
  private String key;

  @Column(name = "message")
  @Lob
  private String message;

  private Message() {}

  private Message(String key, String message) {
    this.key = key;
    this.message = message;
  }

  public long getId() {
    return id;
  }

  public String getKey() {
    return key;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("key", key)
        .add("message", message)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Message message1 = (Message) o;
    return id == message1.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, message);
  }

  public Messaging.Message toProto() {
    return Messaging.Message.newBuilder()
        .setId(this.id)
        .setKey(this.key)
        .setMessage(this.message)
        .build();
  }

  public static Message create(String key, String value) {
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(value);
    return new Message(key, value);
  }
}
