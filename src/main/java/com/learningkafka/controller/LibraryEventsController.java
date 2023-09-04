package com.learningkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learningkafka.domain.LibraryEvent;
import com.learningkafka.domain.LibraryEventType;
import com.learningkafka.producer.LibraryEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class LibraryEventsController {

	@Autowired
	LibraryEventProducer libraryEventProducer;

	@PostMapping("/v1/library-event")
	public ResponseEntity<LibraryEvent> postLibrarryEvent(@RequestBody @Valid LibraryEvent eventListener)
			throws JsonProcessingException {
		// kafka producer
		log.info("before sendLibraryEvent");
		// Asysnc call
		// libraryEventProducer.sendLibraryEvent(eventListener);
		// Sync call
		// SendResult<Integer,String>
		// result=libraryEventProducer.sendLibraryEventSynchronous(eventListener);
		eventListener.setLibraryEventType(LibraryEventType.NEW);
		libraryEventProducer.sendLibraryEventSynchronousApproach3(eventListener);
		// log.info("SendResult is {}",result.toString());
		log.info("after sendLibraryEvent");
		return ResponseEntity.status(HttpStatus.CREATED).body(eventListener);
	}

	@PutMapping("/v1/library-event")
	public ResponseEntity<?> putLibrarryEvent(@RequestBody @Valid LibraryEvent eventListener)
			throws JsonProcessingException {
		// kafka producer
		log.info("Inside Library event put method");

		if (eventListener.getLibraryEventId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please send the proper Library event id");
		}
		eventListener.setLibraryEventType(LibraryEventType.UPDATE);
		libraryEventProducer.sendLibraryEventSynchronousApproach3(eventListener);

		return ResponseEntity.status(HttpStatus.OK).body(eventListener);
	}
}
