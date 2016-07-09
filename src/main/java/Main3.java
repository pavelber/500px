import com.fivehundredpx.api.FiveHundredException;
import com.fivehundredpx.api.auth.AccessToken;
import com.fivehundredpx.api.auth.OAuthAuthorization;
import com.fivehundredpx.api.auth.XAuthProvider;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

public class Main3 {

    private static  OAuthConsumer oAuthConsumer;

    public static void main(String args[]) throws FiveHundredException {
        String consumerKey = "3beLs5IIHxGzgLLtupz2M04OVVdJLVeV8To9GcqH";
        String consumerSecret = "o5c06ZuS8cHzsO4BmNhh5a2pvEeV6uNz1f1WjEbB";
        final OAuthAuthorization oauth = new OAuthAuthorization.Builder()
                .consumerKey(consumerKey)
                .consumerSecret(consumerSecret)
                .build();

        final AccessToken accessToken = oauth
                .getAccessToken(new XAuthProvider("javaap", "zhopa23"));

        setupContext(consumerKey, consumerSecret, accessToken.getToken(), accessToken.getTokenSecret());
        executeGetRequest("https://api.500px.com/v1/photos?feature=user&username=javaap&sort=created_at&image_size=3&include_store=store_download&include_states=voted");
    }

    public static void setupContext(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        oAuthConsumer.setTokenWithSecret(accessToken, accessTokenSecret);
        oAuthConsumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
    }

    public static void authorize(HttpRequestBase httpRequest) {
        try {
            oAuthConsumer.sign(httpRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeGetRequest(String customURIString) {
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        HttpRequestBase httpRequest = null;
        URI uri = null;

        try {
            uri = new URI(customURIString);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        httpRequest = new HttpGet(uri);


        httpRequest.addHeader("content-type", "application/xml");
        httpRequest.addHeader("Accept", "application/xml");


        authorize(httpRequest);

        HttpResponse httpResponse = null;
        try {
            HttpHost target = new HttpHost(uri.getHost(), -1, uri.getScheme());
            httpResponse = client.execute(target, httpRequest);
            System.out.println("Connection status : " + httpResponse.getStatusLine());

            InputStream inputStraem = httpResponse.getEntity().getContent();

            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStraem, writer, "UTF-8");
            String output = writer.toString();

            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
