package com.shgupta.stickymarker.apigateway.service;

import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;

public class UserCreateAccountConfirmationService extends CloudService {

	public void confirmSignUp(String confirmationCode, String userName) {
		ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest().withClientId("")
				.withConfirmationCode(confirmationCode).withUsername(userName);
		ConfirmSignUpResult confirmSignUpResult = provider().confirmSignUp(confirmSignUpRequest);

		log("SignUp confirmed for user => " + confirmSignUpResult);

	}
}
