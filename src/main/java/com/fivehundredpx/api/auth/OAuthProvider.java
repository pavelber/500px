package com.fivehundredpx.api.auth;

import org.apache.http.client.methods.HttpPost;

import com.fivehundredpx.api.FiveHundredException;

import java.io.UnsupportedEncodingException;

public interface OAuthProvider {
	void signForAccessToken(HttpPost req) throws FiveHundredException, UnsupportedEncodingException;
	
	void setOAuthConsumer(String consumerKey, String consumerSecret);
	void setOAuthRequestToken(String requestTokenKey, String requestTokenSecret);
}