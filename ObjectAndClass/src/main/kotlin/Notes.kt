data class Note(
    val noteId: Int,
    val title: String,
    var text: String,
    var noteComments: List<NoteComment>,
)

data class NoteComment(
    val noteCommentId: Int,
    var message: String,
    val minLength: Int = 2,
    var isDeleted: Boolean = false
)
class NoteService {
    private var notes = mutableListOf<Note>()

    fun add(note: Note): Int {
        notes += note
        return note.noteId
    }

    fun createComment(noteId: Int, noteComment: NoteComment): Int {
        val note = notes.find { it.noteId == noteId }
        if (noteComment.message.length > noteComment.minLength) {
            if (note != null) {
                note.noteComments += noteComment
                return noteComment.noteCommentId
            }
        }
        return -1
    }

    fun delete(noteId: Int): Int {
        val note = notes.find { it.noteId == noteId }
        if (note != null) {
            for (noteComment in note.noteComments) {
                noteComment.isDeleted = true
            }
            notes.removeAt(noteId)
            return 1
        }
        return -1
    }

    fun deleteComment(noteId: Int, commentId: Int): Int {
        val note = notes.find { it.noteId == noteId }
        val noteComm = note?.noteComments?.find { it.noteCommentId == commentId }

        if (note != null && noteComm != null) {
            noteComm.isDeleted = true
            return 1
        }
        return -1
    }

    fun edit(noteId: Int, newText: String): Int {
        val note = notes.find { it.noteId == noteId }
        if (note != null) {
            note.text = newText
            return 1
        }
        return -1
    }

    fun editComment(noteId: Int, commentId: Int, newMessage: String): Int {
        val note = notes.find { it.noteId == noteId }
        val noteComm = note?.noteComments?.find { it.noteCommentId == commentId }

        if (note != null && noteComm != null) {
            if (newMessage.length > noteComm.minLength) {
                noteComm.message = newMessage
                return 1
            }
        }
        return -1
    }

    fun get(): List<Note> {
        return notes
    }

    fun getById(noteId: Int): Note {
        return notes.find { it.noteId == noteId }
            ?: return throw NotFoundIdException("Not found note with id $noteId")
    }

    fun getComments(noteId: Int): List<NoteComment> {
        val note =
            notes.find { it.noteId == noteId } ?: return throw NotFoundIdException("Not found note with id $noteId")
        return note.noteComments
    }

    fun restoreComment(noteId: Int, commentId: Int): Int {
        val note = notes.find { it.noteId == noteId }
        val noteComm = note?.noteComments?.find { it.noteCommentId == commentId }
        if (note != null && noteComm != null) {
            if (noteComm.isDeleted) {
                noteComm.isDeleted = false
                return 1
            }
        }
        return -1
    }
}
