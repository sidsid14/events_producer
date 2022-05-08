package com.ss.controller;

import com.ss.domain.Book;
import com.ss.domain.LibraryEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventsProducerApplicationTests {
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void postLibraryEvent() {
		//given
		Book book = Book.builder().bookId(123).bookName("test").bookAuthor("author").build();
		LibraryEvent libraryEvent = LibraryEvent.builder().libraryEventId(null).book(book).build();

		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", MediaType.APPLICATION_JSON.toString());
		HttpEntity<LibraryEvent> request = new HttpEntity<>(libraryEvent, headers);

		//when
		ResponseEntity<LibraryEvent> responseEntity = testRestTemplate.exchange("/v1/libraryevent-sync", HttpMethod.POST, request, LibraryEvent.class);

		//then
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

}
