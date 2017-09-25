package com.shgupta.stickymarker.apigateway;

import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeResult;
import com.shgupta.stickymarker.apigateway.auth.AWSCognitoSession;
import com.shgupta.stickymarker.apigateway.auth.AWSCryptoSettings;

public class UserSignIn {

	public static final String USER_NAME_ID = "";
	public static final String CLIENT_ID = "";
	public static final String PASSWORD = "";
	public static final String EMAIL = "";
	public static final String USER_NAME = "";
	public static final String USER_POOL_ID = "";

	public static void main(String[] args) {
		AWSCredentials awsCredentials = new AWSCredentials() {

			@Override
			public String getAWSSecretKey() {
				return "";
			}

			@Override
			public String getAWSAccessKeyId() {
				// TODO Auto-generated method stub
				return "";
			}
		};

		AWSCognitoSession session = new AWSCognitoSession(new AWSCryptoSettings(), USER_NAME, PASSWORD, USER_POOL_ID);
		Map<String, String> authParameters = null;//session.step1();

//		AWSCognitoIdentityProvider provider = AWSCognitoIdentityProviderClientBuilder.standard()
//				.withCredentials(new AWSCredentialsProvider() {
//					
//					@Override
//					public void refresh() {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public AWSCredentials getCredentials() {
//						// TODO Auto-generated method stub
//						return null;
//					}
//				});
		AWSCognitoIdentityProviderClient client = new AWSCognitoIdentityProviderClient(awsCredentials);
		client.setRegion(Region.getRegion(Regions.US_EAST_1));
		InitiateAuthRequest authRequest = new InitiateAuthRequest().withAuthFlow(AuthFlowType.USER_SRP_AUTH)
				.withClientId(CLIENT_ID).withAuthParameters(authParameters);
		InitiateAuthResult authResult = client.initiateAuth(authRequest);
		System.out.println(authResult);
		Map<String, String> params = authResult.getChallengeParameters();
		Map<String, String> srpAuthResponses = null;//session.step2(params); // step2()
																		// return
																		// also
																		// Map<String,
																		// String>
																		// with
																		// formatted
																		// parameters.

		RespondToAuthChallengeRequest respondToAuthChallengeRequest = new RespondToAuthChallengeRequest()
				.withChallengeName(authResult.getChallengeName()).withClientId(CLIENT_ID)
				.withChallengeResponses(srpAuthResponses);

		RespondToAuthChallengeResult respondToAuthChallengeResult = client
				.respondToAuthChallenge(respondToAuthChallengeRequest);
//
//		Map<String, String> authParametersv1 = new HashMap<>();
//		authParametersv1.put("NEW_PASSWORD", "BobbY@2017");
//		authParametersv1.put("USERNAME", "shgupta");
//
//		RespondToAuthChallengeRequest respondToAuthChallengeRequestv1 = new RespondToAuthChallengeRequest()
//				.withSession(respondToAuthChallengeResult.getSession())
//				.withChallengeName(respondToAuthChallengeResult.getChallengeName()).withClientId(CLIENT_ID)
//				.withChallengeResponses(authParametersv1);
//
//		RespondToAuthChallengeResult respondToAuthChallengeRequestv2 = client
//				.respondToAuthChallenge(respondToAuthChallengeRequestv1);
		System.out.println(respondToAuthChallengeResult);
//		ChangePasswordResult ret = client.changePassword(new ChangePasswordRequest().withPreviousPassword(PASSWORD)
//				.withAccessToken(respondToAuthChallengeResult.getAuthenticationResult().getAccessToken())
//				.withProposedPassword("BobbY@2017"));

		// debug
		//System.out.println(ret);
		// System.out.println(respondToAuthChallengeResult.getChallengeParameters());
		// System.out.println(respondToAuthChallengeResult.getAuthenticationResult());

	}
}
