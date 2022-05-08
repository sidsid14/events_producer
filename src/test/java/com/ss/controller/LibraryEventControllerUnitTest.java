package com.ss.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.controller.LibraryEventsController;
import com.ss.domain.Book;
import com.ss.domain.LibraryEvent;
import com.ss.producer.LibraryEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryEventsController.class)
@AutoConfigureMockMvc
public class LibraryEventControllerUnitTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    LibraryEventProducer libraryEventProducer;

    @Test
    void postLibraryEvent() throws Exception {
        //given
        Book book = Book.builder()
                .bookId(123)
                .bookName("test")
                .bookAuthor("author").build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book).build();

        String json = objectMapper.writeValueAsString(libraryEvent);
        doNothing().when(libraryEventProducer).sendLibraryEvent(isA(LibraryEvent.class));

        //when
        mockMvc.perform(post("/v1/libraryevent")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
