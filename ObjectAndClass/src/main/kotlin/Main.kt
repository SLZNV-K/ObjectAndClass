class Comments(
    var count: Int,
    var canPost: Boolean = true
) {
    override fun toString(): String {
        return "Comments(count: $count, canPost: $canPost)"
    }
}

class Likes(
    var count: Int,
    var userLikes: Boolean = false,
    var canLike: Boolean = true
) {
    override fun toString(): String {
        return "Likes(count: $count, userLikes: $userLikes, canLike: $canLike)"
    }
}

data class Post(
    val id: Int,
    val postType: String = "post",
    val date: Int = 0,
    val text: String,
    val friendsOnly: Boolean = false,
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val comments: Comments,
    val likes: Likes
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
            return if (post.id == updatedPost.id) {
                posts[index] = post.copy(
                    postType = updatedPost.postType,
                    date =updatedPost.date,
                    text = updatedPost.text,
                    friendsOnly = updatedPost.friendsOnly,
                    canPin = updatedPost.canPin,
                    canDelete = updatedPost.canDelete,
                    canEdit = updatedPost.canEdit,
                    likes = updatedPost.likes,
                    comments = updatedPost.comments
                )
                true
            } else false
        }
        return true
    }
}

fun main() {
    val wallService = WallService()
    wallService.add(Post(id = 1, text = "TEXT", comments = Comments(2), likes = Likes(0)))
    wallService.add(Post(id = 2, text = "TEXT", comments = Comments(3), likes = Likes(10)))
    val update = Post(id = 1, text = "UPDATE", comments = Comments(4), likes = Likes(2))
    wallService.update(update)

}