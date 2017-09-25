package com.shgupta.stickymarker.service.urlnote;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shgupta.stickymarker.domain.UrlMetadataResponseError;
import com.shgupta.stickymarker.domain.error.DuplicateUrlExist;

@ControllerAdvice
public class UrlNoteErrorHandler {

	@ExceptionHandler(DuplicateUrlExist.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ResponseEntity<UrlMetadataResponseError> exception(DuplicateUrlExist exception) {

		UrlMetadataResponseError urlError = new UrlMetadataResponseError();
		urlError.setMessage(exception.getMessage());
		urlError.setStatus(HttpStatus.CONFLICT.value());
		urlError.setReason(HttpStatus.CONFLICT.name());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<>(urlError, httpHeaders, HttpStatus.CONFLICT);
	}
}
