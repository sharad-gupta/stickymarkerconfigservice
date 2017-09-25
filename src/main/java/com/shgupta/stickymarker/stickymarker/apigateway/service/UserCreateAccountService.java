package com.shgupta.stickymarker.apigateway.service;


import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;


public class UserCreateAccountService extends CloudService {

	public void createAccount(String username, String password, String emailAddress) {

		SignUpRequest signUpRequest = new SignUpRequest().withClientId("").withPassword(password)
				.withUsername(username);
		List<AttributeType> attributeDataTypes = Arrays.asList(
				new AttributeType().withName("email").withValue(emailAddress));
		signUpRequest.setUserAttributes(attributeDataTypes);
		SignUpResult result = provider().signUp(signUpRequest);
		
		log("Response returned => " + result);
	}
}
