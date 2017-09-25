package com.shgupta.stickymarker.apigateway.service;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;

public abstract class CloudService {

	protected AWSCognitoIdentityProvider provider() {
		AWSCognitoIdentityProvider provider = AWSCognitoIdentityProviderClient.builder()
				.withCredentials(new AWSCredentialsProvider() {

					@Override
					public void refresh() {

					}

					@Override
					public AWSCredentials getCredentials() {
						return new AWSCredentials() {

							@Override
							public String getAWSSecretKey() {
								return "";
							}

							@Override
							public String getAWSAccessKeyId() {
								return "";
							}
						};
					}
				}).build();
		return provider;
	}

	public void log(String msg) {
		System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + " Thread "
				+ Thread.currentThread().getName() + " - " + msg);
	}
}
