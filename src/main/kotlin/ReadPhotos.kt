import java.io.FileInputStream
import java.io.ObjectInputStream

fun main(args:Array<String>) {
    val ois = ObjectInputStream(FileInputStream("c:\\temp\\500px.ser"))
    val photos = ois.readObject() as List<Photo>
    println(photos.size)
   // val last = photos.sortedByDescending { it.votes_count }.subList(0, 20)
    //val last = photos.sortedByDescending { it.favorites_count }.subList(0, 20)
    val last = photos.sortedByDescending{ if (it.favorites_count==0) 0.0 else (1.0*it.favorites_count) / it.votes_count}.subList(0, 20)
    last.forEach { println("${it.created_at} - ${it.name} (${it.votes_count},${ it.favorites_count } ) ${it.url}") }
}