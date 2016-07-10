import java.io.FileOutputStream
import java.io.ObjectOutputStream

fun main(args: Array<String>) {
    val oauth = Oauth("", "")
    val requester = PhotosRequester(oauth)
    val photos = requester.getAllPhotos("javaap")
    println(photos.size)
    val oos = ObjectOutputStream(FileOutputStream("c:\\temp\\500px.ser"))
    oos.writeObject(photos)
    oos.close()
}

