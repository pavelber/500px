import org.apache.commons.io.IOUtils
import org.apache.http.HttpHost
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.impl.client.HttpClients
import java.io.StringWriter
import java.net.URI

class SignedGet(private val oauth: Oauth) {
    val client = HttpClients.createDefault()


    fun execute(url: String): String {

        val uri: URI = URI(url)


        val httpRequest: HttpRequestBase = HttpGet(uri)


        httpRequest.addHeader("content-type", "application/xml")
        httpRequest.addHeader("Accept", "application/xml")


        oauth.authorize(httpRequest)


        val target = HttpHost(uri.host, -1, uri.scheme)
        val httpResponse = client.execute(target, httpRequest)
        if (httpResponse.statusLine.statusCode != 200) {
            throw RuntimeException(httpResponse.statusLine.toString())
        }


        val inputStraem = httpResponse.entity.content

        val writer = StringWriter()
        IOUtils.copy(inputStraem, writer, "UTF-8")
        val output = writer.toString()
        httpResponse.close()
        return output

    }
}