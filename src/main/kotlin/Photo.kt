import com.beust.klaxon.JsonObject
import java.io.Serializable
import java.text.SimpleDateFormat

import java.util.*

class Photo(js: JsonObject) : Serializable {
    val id =  js["id"] as Int
    val user_id =  js["user_id"]as Int
    val name =  js["name"]as String
    val description =  js["description"]as String?
    val camera =  js["camera"]as String?
    val lens =  js["lens"] as String?
    val focal_length  =  js["focal_length"] as String?
    val iso  =  js["iso"] as String?
    val shutter_speed =  js["shutter_speed"] as String?
    val aperture =  js["aperture"] as String?
    val times_viewed =  js["times_viewed"] as Int
    val rating =  js["rating"] as Double
    val status =  js["status"] as Int
    val created_at =  parseDate(js["created_at"])
    val category =  js["category"] as Int
    val location =  js["location"] as String?
    val latitude =  js["latitude"] as Double?
    val longitude =  js["longitude"] as Double?
    val taken_at =  parseDate(js["taken_at"])
    val hi_res_uploaded =  js["hi_res_uploaded"] as Int
    val for_sale =  js["for_sale"] as Boolean
    val width =  js["width"] as Int
    val height =  js["height"] as Int
    val votes_count =  js["votes_count"] as Int
    val favorites_count =  js["favorites_count"] as Int
    val comments_count =  js["comments_count"] as Int
    val nsfw =  js["nsfw"] as Boolean
    val sales_count =  js["sales_count"] as Int
    val for_sale_date =  parseDate(js["for_sale_date"])
    val highest_rating =  js["highest_rating"] as Double
    val highest_rating_date =  parseDate(js["highest_rating_date"])
    val license_type =  js["license_type"] as Int
    val converted =  js["converted"] as Int
    val collections_count =  js["collections_count"] as Int
    val crop_version =  js["crop_version"] as Int
    val privacy =  js["privacy"] as Boolean
    val profile =  js["profile"] as Boolean
    val image_url =  js["image_url"] as String
   // val images =  js["images"]
    val url =  js["url"]as String
    val positive_votes_count =  js["positive_votes_count"] as Int
    val converted_bits =  js["converted_bits"] as Int
    val voted =  js["voted"] as Boolean
    val liked =  js["liked"] as Boolean
    val disliked =  js["disliked"] as Boolean
    val favorited =  js["favorited"] as Boolean
    val purchased =  js["purchased"] as Boolean
    val watermark =  js["watermark"] as Boolean
    val image_format =  js["image_format"] as String
    // val user =  js["user"]
    val licensing_requested =  js["licensing_requested"] as Boolean
    val licensing_suggested = js["licensing_suggested"] as Boolean

    private fun  parseDate(str: Any?): Date? {
        if (str == null) {
            return null
        }

        return format.parse(str as String)

    }

    companion object  {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
    }
}
