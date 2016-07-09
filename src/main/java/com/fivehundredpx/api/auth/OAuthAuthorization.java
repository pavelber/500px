package com.fivehundredpx.api.auth;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fivehundredpx.api.FiveHundredException;

public class OAuthAuthorization {

	private String consumerKey;
	private String consumerSecret;

	private String requestOauthToken;
	private String requestOauthSecret;

	private String url = "https://api.500px.com/v1";
	private String request_token_url = url+"/oauth/request_token";
	private String access_token_url = url+"/oauth/access_token";
	private String authorize_url = url+"/oauth/authorize";

	
	public static OAuthAuthorization build(String url, String k, String s) throws FiveHundredException {
		OAuthAuthorization.Builder builder = new OAuthAuthorization.Builder();

		OAuthAuthorization oauth = builder.url(url).consumerKey(k).consumerSecret(s)
				.build();
		
		return oauth;

	}

	private OAuthAuthorization() {

	}

	private void init() throws Exception {
		CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(
				this.consumerKey, this.consumerSecret);
		
		CommonsHttpOAuthProvider provider = new CommonsHttpOAuthProvider(
				request_token_url, access_token_url, authorize_url);

		final String urlString = provider.retrieveRequestToken(consumer, "");
		
		this.requestOauthToken = HttpParameterUtil.getUrlParamValue(urlString,
				"oauth_token");
		this.requestOauthSecret = consumer.getTokenSecret();

	}

	public AccessToken getAccessToken(OAuthProvider provider)
			throws FiveHundredException {
		try {
			final HttpPost post = new HttpPost(access_token_url);
			
			provider.setOAuthConsumer(consumerKey, consumerSecret);
			provider.setOAuthRequestToken(requestOauthToken, requestOauthSecret);
			
			provider.signForAccessToken(post);

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(post);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				throw new FiveHundredException(statusCode);
			}

			return new AccessToken(response);

		} catch (FiveHundredException e) {
			throw e;
		} catch (Exception e) {
			throw new FiveHundredException(e);
		}

	}


	@Override
	protected Object clone() throws CloneNotSupportedException {

		final OAuthAuthorization c = new OAuthAuthorization();
		c.consumerKey = this.consumerKey;
		c.consumerSecret = this.consumerSecret;

		return c;
	}

	public static class Builder {
		private OAuthAuthorization instance;

		public Builder() {
			this.instance = new OAuthAuthorization();
		}

		public Builder url(String url){
			this.instance.url = url;
			return this;
		}
		
		public Builder consumerKey(String consumerKey) {
			this.instance.consumerKey = consumerKey;
			return this;
		}

		public Builder consumerSecret(String s) {
			this.instance.consumerSecret = s;
			return this;
		}

		public OAuthAuthorization build() throws FiveHundredException {
			try {
				OAuthAuthorization i = (OAuthAuthorization) instance.clone();
				i.init();
				return i;
			} catch (CloneNotSupportedException e) {
				return null;
			} catch (Exception e) {
				throw new FiveHundredException(e);
			}
		}
	}
}
