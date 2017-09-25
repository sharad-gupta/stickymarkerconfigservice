package com.shgupta.stickymarker.apigateway.service;

import com.amazonaws.services.cognitoidp.model.GlobalSignOutRequest;
import com.amazonaws.services.cognitoidp.model.GlobalSignOutResult;

public class UserAccountLogoutService extends CloudService {
	
	public void logout(String accessToken) {
		GlobalSignOutRequest globalSignOutRequest = new GlobalSignOutRequest()
				.withAccessToken(accessToken);
		GlobalSignOutResult ret = provider().globalSignOut(globalSignOutRequest);
		log("User logout " + ret);
		
	}
}
