package com.shgupta.stickymarker.apigateway.service;

import java.util.Map;

import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeResult;
import com.shgupta.stickymarker.apigateway.auth.AWSCognitoSession;
import com.shgupta.stickymarker.apigateway.auth.AWSCryptoSettings;

public class UserAccountLoginService extends CloudService {

	public void login(String username, String password) {
		AWSCognitoSession session = new AWSCognitoSession(new AWSCryptoSettings(), username, password, "");
		Map<String, String> authParameters = session.buildAuthParameter();

		InitiateAuthRequest authRequest = new InitiateAuthRequest().withAuthFlow(AuthFlowType.USER_SRP_AUTH)
				.withClientId("").withAuthParameters(authParameters);
		InitiateAuthResult authResult = provider().initiateAuth(authRequest);
		log("Auth returned => " + authResult);

		Map<String, String> params = authResult.getChallengeParameters();
		Map<String, String> srpAuthResponses = session.buildChallengeParamter(params);

		RespondToAuthChallengeRequest respondToAuthChallengeRequest = new RespondToAuthChallengeRequest()
				.withChallengeName(authResult.getChallengeName()).withClientId("")
				.withChallengeResponses(srpAuthResponses);

		RespondToAuthChallengeResult respondToAuthChallengeResult = provider()
				.respondToAuthChallenge(respondToAuthChallengeRequest);
		log("JWT ID Token => " + respondToAuthChallengeResult.getAuthenticationResult().getIdToken());
		log("JWT Access Token => " + respondToAuthChallengeResult.getAuthenticationResult().getAccessToken());

	}
}
