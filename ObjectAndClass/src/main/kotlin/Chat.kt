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
        val chat = chats.find { it.userId == chatId } ?: throw NotFoundIdException("Not found chat with this id")
        val chatMessage = chat.chatMessages.find { it.messageId == messageId }
            ?: throw NotFoundMessage("Not found message with this id")
        chatMessage.text = newText
    }

    fun deleteMessage(chatId: Int, messageId: Int): Boolean {
        val chat = chats.find { it.userId == chatId } ?: throw NotFoundIdException("Not found chat with this id")
        val chatMessage = chat.chatMessages.find { it.messageId == messageId }
            ?: throw NotFoundIdException("Not found message with this id")
        return chat.chatMessages.remove(chatMessage)
    }

    fun getUnreadChatsCount(): Int = chats.filter { chat -> chat.chatMessages.any { !it.isRead && it.isIncoming } }.size

    fun getLastMessagesOfChats() =
        chats.joinToString(separator = "\n") {
            it.chatMessages.lastOrNull()?.text ?: "Нет сообщений"
        }

    fun getListOfChatMessages(chatId: Int, startId: Int, messageCount: Int) =
        chats.find { it.userId == chatId }
            .let { it?.chatMessages ?: throw NotFoundIdException("Not found chat with id $chatId") }
            .asSequence()
            .drop(startId)
            .take(messageCount)
            .toList()
            .onEach { it.isRead = true }
}
