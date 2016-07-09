package com.fivehundredpx.api.auth;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;


import com.fivehundredpx.api.FiveHundredException;

public class AccessToken  {

	private String token;
	private String tokenSecret;

	public AccessToken(String token, String tokenSecret) {
		this.token = token;
		this.tokenSecret = tokenSecret;
	}
	
	AccessToken(HttpResponse response) throws FiveHundredException {
		try {
			final String responseString = EntityUtils.toString(response
					.getEntity());

			this.tokenSecret = HttpParameterUtil.getUrlParamValue(
					responseString, "oauth_token_secret");
			this.token = HttpParameterUtil.getUrlParamValue(responseString,
					"oauth_token");

		} catch (ParseException e) {
			throw new FiveHundredException(e);
		} catch (IOException e) {
			throw new FiveHundredException(e);
		}

	}

	public String getToken() {
		return token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}



}
