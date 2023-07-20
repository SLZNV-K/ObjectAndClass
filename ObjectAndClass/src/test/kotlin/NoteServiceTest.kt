import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun add() {
        val note = Note(2, "", "kdsbdfkeg", listOf())
        val noteService = NoteService()
        val result = noteService.add(note)

        assertEquals(0, result)
    }

    @Test
    fun createCommentTrue() {
        val note = Note(0, "", "kdsbdfkeg", listOf())
        val comm = NoteComment(1, "yf")
        val noteService = NoteService()
        noteService.add(note)
        val result = noteService.createComment(0, comm)

        assertEquals(-1, result)
    }

    @Test
    fun createCommentFalse() {
        val note = Note(0, "", "kdsbdfkeg", listOf())
        val noteService = NoteService()
        noteService.add(note)
        val result = noteService.createComment(0, NoteComment(1, "ygf"))

        assertEquals(0, result)
    }

    @Test
    fun delete() {
        val note = Note(
            0, "", "kdsbdfkeg", listOf(
                NoteComment(0, ""),
                NoteComment(2, "")
            )
        )
        val noteService = NoteService()
        noteService.add(note)
        val result = noteService.delete(0)

        assertEquals(1, result)
    }

    @Test
    fun deleteCommentTrue() {
        val note = Note(
            0, "", "kdsbdfkeg", listOf(
                NoteComment(0, ""),
                NoteComment(2, "")
            )
        )
        val noteService = NoteService()
        noteService.add(note)
        val result = noteService.deleteComment(0, 0)

        assertEquals(1, result)
    }

    @Test
    fun deleteCommentFalse() {
        val note = Note(
            0, "", "kdsbdfkeg", listOf(
                NoteComment(0, ""),
                NoteComment(2, "")
            )
        )
        val noteService = NoteService()
        noteService.add(note)
        val result = noteService.deleteComment(0, 10)

        assertEquals(-1, result)
    }

    @Test
    fun edit() {
        val note = Note(0, "", "text", listOf())
        val noteService = NoteService()
        noteService.add(note)
        val result = noteService.edit(note, "new text")

        assertEquals(1, result)
    }

    @Test
    fun editCommentTrue() {
        val note = Note(0, "", "text", listOf(NoteComment(0, "message")))
        val noteService = NoteService()
        noteService.add(note)
        val result = noteService.editComment(0, 0, "new message")

        assertEquals(1, result)
    }

    @Test
    fun editCommentFalse() {
        val note = Note(0, "", "text", listOf(NoteComment(0, "message")))
        val noteService = NoteService()
        noteService.add(note)
        val result = noteService.editComment(0, 0, "ne")

        assertEquals(-1, result)
    }

    @Test
    fun get() {
        val notes = mutableListOf(
            Note(0, "title", "text", listOf()),
            Note(1, "title", "text", listOf()),
            Note(2, "title", "text", listOf()),
            Note(3, "title", "text", listOf())
        )
        val noteService = NoteService()
        noteService.add(Note(0, "title", "text", listOf()))
        noteService.add(Note(1, "title", "text", listOf()))
        noteService.add(Note(2, "title", "text", listOf()))
        noteService.add(Note(3, "title", "text", listOf()))
        val result = noteService.get()

        assertEquals(notes, result)
    }

    @Test
    fun getById() {
        val notes = mutableListOf(
            Note(0, "title", "text", listOf()),
            Note(1, "title", "text", listOf())
        )
        val noteService = NoteService()
        noteService.add(Note(0, "title", "text", listOf()))
        noteService.add(Note(1, "title", "text", listOf()))
        val result = noteService.getById(1)

        assertEquals(notes[1], result)
    }

    @Test
    fun getComments() {
        val notes = mutableListOf<Note>(
            Note(
                0, "title", "text", mutableListOf(
                    NoteComment(0, ""),
                    NoteComment(1, "")
                )
            ),
            Note(1, "title", "text", listOf())
        )
        val noteService = NoteService()

        noteService.add(
            Note(
                0, "title", "text", mutableListOf(
                    NoteComment(0, ""),
                    NoteComment(1, "")
                )
            )
        )
        noteService.add(Note(1, "title", "text", listOf()))
        val result = noteService.getComments(0)

        assertEquals(notes[0].noteComments, result)
    }

    @Test
    fun restoreCommentTrue() {
        val noteService = NoteService()
        noteService.add(
            Note(
                0, "title", "text", mutableListOf(
                    NoteComment(0, "", isDeleted = true)
                )
            )
        )
        val result = noteService.restoreComment(0, 0)

        assertEquals(1, result)
    }

    @Test
    fun restoreCommentFalse() {
        val noteService = NoteService()
        noteService.add(
            Note(
                0, "title", "text", mutableListOf(
                    NoteComment(0, "", isDeleted = false)
                )
            )
        )
        val result = noteService.restoreComment(0, 0)

        assertEquals(-1, result)
    }
}