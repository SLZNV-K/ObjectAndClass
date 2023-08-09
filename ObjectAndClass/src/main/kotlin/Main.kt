fun main() {
//    POST SERVICE
//    val postService = WallService()
//    postService.add(
//        Post(
//            id = 1,
//            text = "Text1",
//            comment = Comment(),
//            comments = Comments(),
//            likes = Likes(),
//            copyright = Copyright(1),
//            geo = Geo(place = Place()),
//            attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
//            reposts = Reposts(),
//            report = Report(),
//            views = Views(),
//            postSource = PostSource()
//        )
//    )
//
//    postService.add(
//        Post(
//            id = 3,
//            text = "Text2",
//            comment = Comment(),
//            comments = Comments(),
//            likes = Likes(),
//            copyright = Copyright(1),
//            geo = Geo(place = Place()),
//            attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
//            report = Report(),
//            reposts = Reposts(),
//            views = Views(),
//            postSource = PostSource()
//        )
//    )
//    val update = Post(
//        id = 1,
//        text = "Update",
//        comment = Comment(),
//        comments = Comments(),
//        likes = Likes(67),
//        copyright = Copyright(1),
//        geo = Geo(place = Place()),
//        attachment = AudioAttachment(audio = Audio(id = 1), type = "audio"),
//        report = Report(),
//        reposts = Reposts(),
//        views = Views(),
//        postSource = PostSource()
//    )
//
//    postService.update(update)
//
//    postService.createComment(1, Comment())
//
//    postService.reportComment(Report(0, 0, 3))

//    NOTE SERVICE
//    val noteService = NoteService()
//
//    noteService.add(Note(0, "0", "", listOf()))
//    noteService.add(Note(1, "1", "", listOf()))
//    noteService.add(Note(2, "2", "", listOf()))
//
//    println("Все заметки:")
//    println(noteService.createComment(0, NoteComment(2, "jkhfg")))
//    println(noteService.get())
//
//    println("Удалена заметка с id = 0:")
//    println(noteService.delete(0))
//    println(noteService.get())
//
//    println("Удален коммент с заметки 0, id= 2:")
//    println(noteService.deleteComment(0, 2))
//    println(noteService.get())
//
//    println("Изменен текст заметки с id = 0:")
//    println(noteService.edit(0, "new text"))
//    println(noteService.get())
//
//    println("Заметка с id = 1:")
//    println(noteService.getById(1))
//
//    println("Все комментарии заметки с id = 1:")
//    println(noteService.getComments(1))
//
//    println("Удаленный комментарий восстановлен:")
//    println(noteService.restoreComment(0, 9))
//    println(noteService.get())

//    CHAT SERVICE
    val chatService = ChatService()
    chatService.createChat(
        Chat(
            0, mutableListOf
                (
                ChatMessage(0, "не последнее", true, isRead = false),
                ChatMessage(1, "не последнее", false, isRead = true),
                ChatMessage(2, "не последнее", false, isRead = true),
                ChatMessage(3, "не последнее", true, isRead = false),
                ChatMessage(4, "не последнее", false, isRead = false),
                ChatMessage(5, "последнее", false, isRead = true),
            )
        )
    )
    chatService.createChat(
        Chat(
            1, mutableListOf
                (
                ChatMessage(1, "не последнее", true, isRead = true),
                ChatMessage(2, "последнее", false, isRead = false)
            )
        )
    )
    chatService.createChat(
        Chat(
            2, mutableListOf()
        )
    )
//    println(chatService.getChats())
//    println(chatService.createMessage(0,ChatMessage(3, "", true, isRead = false)))
//    println(chatService.getChats())
//    println(chatService.deleteChat(2))
//    println(chatService.deleteMessage(0,19))
//    println(chatService.getChats())
//    println(chatService.editMessage(1,2, "NEW MESSAGE"))
//    println(chatService.getChats())

//    println(chatService.getUnreadChatsCount())
    println(chatService.getLastMessagesOfChats())

    println(chatService.getListOfChatMessages(0,3,3))
}