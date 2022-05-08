package com.ss.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.domain.Book;
import com.ss.domain.LibraryEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryEventProducerUnitTest {
    @Mock
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    LibraryEventProducer eventProducer;

    @Test
    void sendLibraryEvent() throws ExecutionException, JsonProcessingException, InterruptedException, TimeoutException {
        Book book = Book.builder()
                .bookId(123)
                .bookName("test")
                .bookAuthor("author").build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book).build();

        SettableListenableFuture future = new SettableListenableFuture();
        future.setException(new RuntimeException("Exception calling Kafka"));
        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);

        assertThrows(Exception.class, ()->eventProducer.sendLibraryEventSyncWithTopic(libraryEvent));
    }
}
