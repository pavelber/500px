import com.fivehundredpx.api.auth.OAuthAuthorization
import com.fivehundredpx.api.auth.XAuthProvider
import oauth.signpost.OAuthConsumer
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy
import org.apache.http.HttpRequest

class Oauth(userName: String, password: String) {
    private val consumerKey = ""
    private val consumerSecret = ""
    private var oAuthConsumer: OAuthConsumer = setupContext(userName,password)


    fun authorize(request: HttpRequest) {
        oAuthConsumer.sign(request)
    }


    private fun setupContext(userName: String, password: String): OAuthConsumer {

        val oauth = OAuthAuthorization.Builder().consumerKey(consumerKey).consumerSecret(consumerSecret).build()

        val accessToken = oauth.getAccessToken(XAuthProvider(userName, password))
        oAuthConsumer = CommonsHttpOAuthConsumer(consumerKey, consumerSecret)
        oAuthConsumer.setTokenWithSecret(accessToken.token, accessToken.tokenSecret)
        oAuthConsumer.setSigningStrategy(AuthorizationHeaderSigningStrategy())
        return oAuthConsumer
    }

}