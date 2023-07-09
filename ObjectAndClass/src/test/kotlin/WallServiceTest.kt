import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

class WallServiceTest {

    @Test
    fun add() {
        var idPost = 0
        val post = Post(
            id = 8,
            text = "Text1",
            comments = Comments(),
            likes = Likes(),
            copyright = Copyright(1),
            geo = Geo(place = Place()),
            attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
            reposts = Reposts(),
            views = Views(),
            postSource = PostSource()
        )
        var posts = emptyArray<Post>()
        idPost += 1
        posts += post.copy(id = idPost)
        val result = posts.last().id

        assertEquals(1, result)
    }

    @Test
    fun updateTrue() {
        val wallService = WallService()
        wallService.add(
            Post(
                id = 1,
                text = "Text1",
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )

        wallService.add(
            Post(
                id = 2,
                text = "Text1",
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )

        val update = Post(
            id = 2,
            text = "Update",
            comments = Comments(49),
            likes = Likes(67),
            copyright = Copyright(1),
            geo = Geo(place = Place()),
            attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
            reposts = Reposts(),
            views = Views(),
            postSource = PostSource()
        )
        val result = wallService.update(update)

        assertTrue(result)
    }

    @Test
    fun updateFalse() {
        val wallService = WallService()
        wallService.add(
            Post(
                id = 1,
                text = "Text1",
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )

        wallService.add(
            Post(
                id = 2,
                text = "Text1",
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )

        val update = Post(
            id = 11,
            text = "Update",
            comments = Comments(49),
            likes = Likes(67),
            copyright = Copyright(1),
            geo = Geo(place = Place()),
            attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
            reposts = Reposts(),
            views = Views(),
            postSource = PostSource()
        )
        val result = wallService.update(update)

        assertFalse(result)
    }
}