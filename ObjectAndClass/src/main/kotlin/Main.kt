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
    val comment: Comment,
    val comments: Comments,
    val copyright: Copyright? = null,
    val likes: Likes,
    val reposts: Reposts,
    val report: Report,
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

data class Note(
    var noteId: Int,
    val title: String,
    var text: String,
    var noteComments: List<NoteComment>,
)

data class NoteComment(
    var noteCommentId: Int,
    var message: String,
    val minLength: Int = 2,
    var isDeleted: Boolean = false
)

data class Comment(
    val id: Int = 0,
    val ownerId: Int = 0,
    val date: Int = 0,
    val text: String = ""
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

data class Copyright(
    val id: Int = 0,
    val link: String = "",
    val name: String = "",
    val type: String = ""
)

data class Geo(
    val type: String = "",
    val coordinate: String = "",
    val place: Place
)

data class Place(
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

data class PostSource(
    val type: String = "",
    val platform: String = "",
    val data: String = "",
    val url: String = ""
)

data class Views(
    var count: Int = 0
)

data class Reposts(
    var count: Int = 0,
    var userReposted: Boolean = false
)

data class Report(
    val ownerId: Int = 0,
    val commentId: Int = 0,
    val reason: Int = 0
)


class WallService {
    private var posts = emptyArray<Post>()
    private var postComments = emptyArray<Comment>()
    private var idPost = 0
    private var reposts = emptyArray<Report>()

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

    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (postId == post.id) {
                postComments += comment
                return comment
            }
        }
        return throw NotFoundIdException("Not found post with id $postId")
    }

    fun reportComment(report: Report): Report {
        if (report.reason !in 1..8) {
            return throw InvalidReasonException("Invalid reason for report")
        }

        for (post in posts) {
            if (report.ownerId != post.ownerId) {
                return throw InvalidOwnerIdException("Not found owner id")
            }
        }

        for (post in posts) {
            if (post.comment.id == report.commentId) {
                reposts += report
                return report
            }
        }
        return throw InvalidCommentIdException("Not found comment id")
    }
}

class NoteService {
    private var notes = mutableListOf<Note>()
    fun add(note: Note): Int {
        notes += note
        return note.noteId
    }

    fun createComment(noteId: Int, noteComment: NoteComment): Int {
        if (noteComment.message.length > noteComment.minLength) {
            if (notes.find { it.noteId == noteId } != null) {
                notes.find { it.noteId == noteId }!!.noteComments += noteComment
                noteComment.noteCommentId = notes[noteId].noteComments.indexOf(noteComment)
                return noteComment.noteCommentId
            }
        }
        return -1
    }

    fun delete(noteId: Int): Int {
        if (notes.find { it.noteId == noteId } != null) {
            for (noteComm in notes.find { it.noteId == noteId }!!.noteComments) {
                noteComm.isDeleted = true
            }
            notes.removeAt(noteId)
            return 1
        }
        return -1
    }

    fun deleteComment(noteId: Int, commentId: Int): Int {
        if (notes.find { it.noteId == noteId } != null) {
            for (noteComm in notes.find { it.noteId == noteId }!!.noteComments) {
                if (noteComm.noteCommentId == commentId) {
                    noteComm.isDeleted = true
                    return 1
                }
            }
        }
        return -1
    }

    fun edit(note: Note, newText: String): Int {
        note.text = newText
        return 1
    }

    fun editComment(noteId: Int, noteCommentId: Int, newMessage: String): Int {
        if (notes.find { it.noteId == noteId } != null) {
            if (newMessage.length > notes.find { it.noteId == noteId }!!.noteComments[noteCommentId].minLength) {
                notes.find { it.noteId == noteId }!!.noteComments[noteCommentId].message = newMessage
                return 1
            }
        }
        return -1
    }

    fun get(): List<Note> {
        return notes
    }

    fun getById(noteId: Int): Note {
        if (notes.find { it.noteId == noteId } == null) {
            return throw NotFoundIdException("Not found note with id $noteId")
        }
        return notes.find { it.noteId == noteId }!!
    }

    fun getComments(noteId: Int): List<NoteComment> {
        if (notes.find { it.noteId == noteId } == null) {
            return throw NotFoundIdException("Not found note with id $noteId")
        }
        return notes.find { it.noteId == noteId }!!.noteComments
    }

    fun restoreComment(noteId: Int, commentId: Int): Int {
        if (notes.find { it.noteId == noteId } != null) {
            if (notes.find { it.noteId == noteId }!!.noteComments[commentId].isDeleted) {
                notes[noteId].noteComments[commentId].isDeleted = false
                return 1
            }
        }
        return -1
    }
}

fun main() {
    val postService = WallService()
    postService.add(
        Post(
            id = 1,
            text = "Text1",
            comment = Comment(),
            comments = Comments(),
            likes = Likes(),
            copyright = Copyright(1),
            geo = Geo(place = Place()),
            attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
            reposts = Reposts(),
            report = Report(),
            views = Views(),
            postSource = PostSource()
        )
    )

    postService.add(
        Post(
            id = 3,
            text = "Text2",
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
        id = 1,
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

    postService.update(update)

    postService.createComment(1, Comment())

    postService.reportComment(Report(0, 0, 3))

//    NOTE SERVICE
    val noteService = NoteService()

    noteService.add(Note(0, "0", "", listOf()))
    noteService.add(Note(1, "1", "", listOf()))
    noteService.add(Note(2, "2", "", listOf()))

    noteService.createComment(0, NoteComment(2, "jkhfg"))
    println(noteService.get())

    noteService.delete(1)
    println(noteService.get())

    noteService.deleteComment(0, 0)
    println(noteService.get())

    val editedNote = Note(2, "3", "", listOf())
    println(editedNote)
    noteService.edit(editedNote, "new text")
    println(editedNote)

    println(noteService.getById(0))

    println(noteService.getComments(0))

    println(noteService.restoreComment(0, 0))
    println(noteService.get())


}