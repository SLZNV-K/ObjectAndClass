data class Post(
    val id: Int,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val date: Int = 0,
    val text: String,
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    var friendsOnly: Boolean = false,
    val comments: Comments,
    val copyright: Copyright? = null,
    val likes: Likes,
    val reposts: Reposts,
    val views: Views,
    val postType: String = "post",
    val postSource: PostSource? = null,
    val geo: Geo,
    val signerId: Int = 0,
    var canPin: Boolean = true,
    var canDelete: Boolean = true,
    var canEdit: Boolean = true,
    var isPinned: Boolean = false,
    var markedAsAds: Boolean = false,
    var isFavorite: Boolean = false,
    val postponedId: Int = 0,
    val attachment: Attachment,
    val attachments: Array<Attachment> = emptyArray()
)

data class Comments(
    var count: Int = 0,
    var canPost: Boolean = true
)

data class Likes(
    var count: Int = 0,
    var userLikes: Boolean = false,
    var canLike: Boolean = true
)

class Copyright(
    val id: Int = 0,
    val link: String = "",
    val name: String = "",
    val type: String = ""
)

class Geo(
    val type: String = "",
    val coordinate: String = "",
    val place: Place
)

class Place(
    val id: Int = 0,
    val title: String = "",
    val latitude: Int = 0,
    val longitude: Int = 0,
    val created: Int = 0,
    val icon: String = "",
    val checkins: Int = 0,
    val updated: Int = 0,
    val type: Int = 0,
    val country: Int = 0,
    val city: Int = 0,
    val address: String = ""
)

class PostSource(
    val type: String = "",
    val platform: String = "",
    val data: String = "",
    val url: String = ""
)

class Views(
    var count: Int = 0
)

class Reposts(
    var count: Int = 0,
    var userReposted: Boolean = false
)


class WallService {
    private var posts = emptyArray<Post>()
    private var idPost = 0

    fun add(post: Post): Post {
        idPost += 1
        posts += post.copy(id = idPost)
        return posts.last()
    }

    fun update(updatedPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.id == updatedPost.id) {
                posts[index] = post.copy(
                    postType = updatedPost.postType,
                    date = updatedPost.date,
                    text = updatedPost.text,
                    friendsOnly = updatedPost.friendsOnly,
                    canPin = updatedPost.canPin,
                    canDelete = updatedPost.canDelete,
                    canEdit = updatedPost.canEdit,
                    likes = updatedPost.likes.copy(),
                    comments = updatedPost.comments.copy()
                )
                return true
            }
        }
        return false
    }
}

fun main() {
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
            id = 3,
            text = "Text2",
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
        id = 1,
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

    wallService.update(update)

}