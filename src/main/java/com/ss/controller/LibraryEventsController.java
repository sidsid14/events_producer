package com.ss.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ss.domain.LibraryEvent;
import com.ss.domain.LibraryEventType;
import com.ss.producer.LibraryEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@Slf4j
public class LibraryEventsController {

    @Autowired
    LibraryEventProducer libraryEventProducer;

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException {
        // invoke kafka producer
        libraryEvent.setLibraryEventType(LibraryEventType.NEW);
        libraryEventProducer.sendLibraryEvent(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PostMapping("/v1/libraryevent-sync")
    public ResponseEntity<LibraryEvent> postLibraryEventSync(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        libraryEvent.setLibraryEventType(LibraryEventType.NEW);
        // invoke kafka producer

        //Produce message using default topic
        //SendResult<Integer, String> sendResult = libraryEventProducer.sendLibraryEventSync(libraryEvent);

        //Produce message using a topic
        SendResult<Integer, String> sendResult = libraryEventProducer.sendLibraryEventSyncWithTopic(libraryEvent);
        log.info("Send result is {}", sendResult.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PutMapping("/v1/libraryevent-sync")
    public ResponseEntity<?> putLibraryEventSync(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        if(libraryEvent.getLibraryEventId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the LibraryEventId.");
        }
        libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
        SendResult<Integer, String> sendResult = libraryEventProducer.sendLibraryEventSyncWithTopic(libraryEvent);
        log.info("Message sent to partition {} and Send result is {}", sendResult.toString(), sendResult.getRecordMetadata().partition());

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
