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
            comment = Comment(),
            comments = Comments(),
            likes = Likes(),
            copyright = Copyright(1),
            geo = Geo(place = Place()),
            attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
            report = Report(),
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
                comment = Comment(),
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                report = Report(),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )

        wallService.add(
            Post(
                id = 2,
                text = "Text1",
                comment = Comment(),
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                report = Report(),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )

        val update = Post(
            id = 2,
            text = "Update",
            comment = Comment(),
            comments = Comments(),
            likes = Likes(67),
            copyright = Copyright(1),
            geo = Geo(place = Place()),
            attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
            report = Report(),
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
                comment = Comment(),
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                report = Report(),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )

        wallService.add(
            Post(
                id = 2,
                text = "Text1",
                comment = Comment(),
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                report = Report(),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )

        val update = Post(
            id = 11,
            text = "Update",
            comment = Comment(),
            comments = Comments(),
            likes = Likes(67),
            copyright = Copyright(1),
            geo = Geo(place = Place()),
            attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
            report = Report(),
            reposts = Reposts(),
            views = Views(),
            postSource = PostSource()
        )
        val result = wallService.update(update)

        assertFalse(result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val wallService = WallService()
        wallService.add(
            Post(
                id = 1,
                text = "Text1",
                comment = Comment(),
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                report = Report(),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )
        wallService.createComment(194, Comment())
    }

    @Test
    fun shouldNotThrow() {
        val wallService = WallService()
        wallService.add(
            Post(
                id = 1,
                text = "Text1",
                comment = Comment(),
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                report = Report(),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )
        assertEquals(Comment(id = 0), wallService.createComment(1, Comment(id = 0)))
    }

    @Test(expected = InvalidCommentIdException::class)
    fun commentIdThrow() {
        val wallService = WallService()
        wallService.add(
            Post(
                id = 1,
                text = "Text1",
                comment = Comment(),
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                report = Report(),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )
        wallService.reportComment(Report(0, 6, 6))
    }

    @Test(expected = InvalidReasonException::class)
    fun reasonIdThrow() {
        val wallService = WallService()
        wallService.add(
            Post(
                id = 1,
                text = "Text1",
                comment = Comment(),
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                report = Report(),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )
        wallService.reportComment(Report(0, 0, 16))
    }

    @Test(expected = InvalidOwnerIdException::class)
    fun ownerIdThrow() {
        val wallService = WallService()
        wallService.add(
            Post(
                id = 1,
                text = "Text1",
                comment = Comment(),
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                report = Report(),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )
        wallService.reportComment(Report(5, 0, 6))
    }

    @Test
    fun reportNotThrow() {
        val wallService = WallService()
        wallService.add(
            Post(
                id = 1,
                text = "Text1",
                comment = Comment(),
                comments = Comments(),
                likes = Likes(),
                copyright = Copyright(1),
                geo = Geo(place = Place()),
                attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
                report = Report(reason = 1),
                reposts = Reposts(),
                views = Views(),
                postSource = PostSource()
            )
        )
        assertEquals(Report(reason = 1), wallService.reportComment(Report(reason = 1)))
    }
}