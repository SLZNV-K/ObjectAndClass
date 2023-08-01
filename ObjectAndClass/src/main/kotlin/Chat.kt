data class Chat(
    val userId: Int,
    var chatMessages: MutableList<ChatMessage>,
)

data class ChatMessage(
    val messageId: Int,
    var text: String,
    val isIncoming: Boolean, // Входящее ли сообщение
    var isRead: Boolean // Прочитано ли сообщение
)

class ChatService {
    private var chats = mutableListOf<Chat>()

    fun createChat(chat: Chat): Boolean = chats.add(chat)


    fun deleteChat(id: Int): Boolean = chats.remove(chats.find { it.userId == id })

    fun getChats(): List<Chat> = chats

    fun createMessage(chatId: Int, chatMessage: ChatMessage): Boolean =
        chats.find { it.userId == chatId }?.chatMessages?.add(chatMessage) ?: false

    fun editMessage(chatId: Int, messageId: Int, newText: String) {

        chats.singleOrNull { it.userId == chatId }
            .let { it?.chatMessages ?: throw NotFoundIdException("Not found chat with id $chatId") }
            .let {
                chats.find { it.userId == chatId }?.chatMessages?.find { it.messageId == messageId }
                    ?: throw NotFoundMessage("Not found message with id $messageId")
            }.text = newText
    }

    fun deleteMessage(chatId: Int, messageId: Int): Boolean {
        val chat = chats.find { it.userId == chatId }
        val chatMessage = chats.find { it.userId == chatId }?.chatMessages?.find { it.messageId == messageId }
        if (chat == null || chatMessage == null) {
            return throw NotFoundIdException("Not found chat or message with this id")
        }
        chat.chatMessages.remove(chatMessage)
        return true
    }

    fun getUnreadChatsCount(): Int = chats.filter { chat -> chat.chatMessages.any { !it.isRead && it.isIncoming } }.size

    fun getLastMessagesOfChats() =
        chats.joinToString(separator = "\n") {
            if (it.chatMessages.isEmpty()) "Нет сообщений"
            else it.chatMessages.last().text
        }

    fun getListOfChatMessages(chatId: Int, startId: Int, messageCount: Int) =
        chats.find { it.userId == chatId }
            .let { it?.chatMessages ?: throw NotFoundIdException("Not found chat with id $chatId") }
            .asSequence()
            .drop(startId)
            .take(messageCount)
            .toList()
            .map {
                if (it.isIncoming) it.isRead = true
                it
            }
}
