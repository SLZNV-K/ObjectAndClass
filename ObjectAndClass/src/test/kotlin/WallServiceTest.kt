import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

class WallServiceTest {

    @Test
    fun add() {
        var idPost = 0
        val post = Post(id = 8, text = "TEXT", comments = Comments(0), likes = Likes(0))
        var posts = emptyArray<Post>()
        idPost += 1
        posts += post.copy(id = idPost)
        val result = posts.last().id

        assertEquals(1, result)
    }

    @Test
    fun updateTrue() {
        val wallService = WallService()
        wallService.add(Post(id = 1, text = "TEXT", comments = Comments(2), likes = Likes(0)))
        wallService.add(Post(id = 2, text = "TEXT", comments = Comments(3), likes = Likes(10)))
        val update = Post(id = 1, text = "UPDATE", comments = Comments(4), likes = Likes(2))
        val result = wallService.update(update)

        assertTrue(result)
    }

    @Test
    fun updateFalse() {
        val wallService = WallService()
        wallService.add(Post(id = 1, text = "TEXT", comments = Comments(2), likes = Likes(0)))
        wallService.add(Post(id = 2, text = "TEXT", comments = Comments(3), likes = Likes(10)))
        val update = Post(id = 11, text = "UPDATE", comments = Comments(4), likes = Likes(2))
        val result = wallService.update(update)

        assertFalse(result)
    }
}