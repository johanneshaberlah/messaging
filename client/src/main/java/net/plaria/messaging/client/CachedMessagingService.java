package net.plaria.messaging.client;

import com.google.common.base.Preconditions;
import com.google.common.cache.*;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import net.plaria.messaging.proto.Messaging;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CachedMessagingService implements MessagingService {

  private final ListeningExecutorService listeningExecutorService =
      MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

  private MessagingService delegate;
  private LoadingCache<String, String> loadingCache;

  private CachedMessagingService(MessagingService delegate) {
    this.delegate = delegate;
    this.loadingCache =
        CacheBuilder.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(
                new CacheLoader<String, String>() {
                  @Override
                  public String load(String s) throws Exception {
                    return findMessageByKey(s).get().getMessage().getMessage();
                  }
                });
  }

  @Override
  public ListenableFuture<Messaging.MessageResponse> findMessageByKey(String key) {
    return this.delegate.findMessageByKey(key);
  }

  @Override
  public ListenableFuture<String> getMessage(String key, String... args) {
    return this.listeningExecutorService.submit(() -> {
      String message = this.loadingCache.get(key);
      for (int i = 0; i < args.length; i++) {
        message = message.replace("{" + i + "}", args[i]);
      }
      return message;
    });
  }

  @Override
  public String getMessageBlocking(String key, String... args) throws ExecutionException, InterruptedException {
    return this.getMessage(key, args).get();
  }

  @Override
  public void shutdown() {
    this.delegate.shutdown();
  }

  public static CachedMessagingService create(MessagingService delegate) {
    Preconditions.checkNotNull(delegate);
    return new CachedMessagingService(delegate);
  }
}
