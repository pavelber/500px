import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.ByteArrayInputStream

class PhotosRequester(oauth: Oauth) {

    val signedGet: SignedGet = SignedGet(oauth)
    val parser = Parser()


    fun getAllPhotos(userName: String): List<*> {
        var page = 1
        var pages: Int
        val photos = mutableListOf<JsonObject>()
        do {
            val url = "https://api.500px.com/v1/photos?feature=user&username=$userName&include_states=voted&page=$page"
            val content: String = signedGet.execute(url)

            val obj = parser.parse(ByteArrayInputStream(content.toByteArray())) as JsonObject
            pages = obj.map["total_pages"] as Int
            photos.addAll((obj.map["photos"] as JsonArray<JsonObject>).value)
            println(page)
            page ++
        } while (page <= pages)

        return photos.map { js -> Photo(js) }
    }

}

