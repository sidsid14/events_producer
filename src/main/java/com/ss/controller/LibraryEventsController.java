package com.ss.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ss.domain.LibraryEvent;
import com.ss.producer.LibraryEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@Slf4j
public class LibraryEventsController {

    @Autowired
    LibraryEventProducer libraryEventProducer;

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {
        // invoke kafka producer
        libraryEventProducer.sendLibraryEvent(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PostMapping("/v1/libraryevent-sync")
    public ResponseEntity<LibraryEvent> postLibraryEventSync(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        // invoke kafka producer
        SendResult<Integer, String> sendResult = libraryEventProducer.sendLibraryEventSync(libraryEvent);
        log.info("Send result is {}", sendResult.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
