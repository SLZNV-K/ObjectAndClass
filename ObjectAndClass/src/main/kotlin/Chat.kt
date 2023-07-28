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

    fun createChat(chat: Chat): Boolean {
        return chats.add(chat)
    }

    fun deleteChat(id: Int): Boolean {
        chats.find { it.userId == id }?.chatMessages?.clear()
        return chats.remove(chats.find { it.userId == id })
    }

    fun getChats(): List<Chat> {
        return chats
    }

    fun createMessage(chatId: Int, chatMessage: ChatMessage): Boolean {
        return chats.find { it.userId == chatId }?.chatMessages?.add(chatMessage) ?: false
    }

    fun editMessage(chatId: Int, messageId: Int, newText: String): Boolean {
        val chat = chats.find { it.userId == chatId }
        val chatMessage = chats.find { it.userId == chatId }?.chatMessages?.find { it.messageId == messageId }
        if (chat == null || chatMessage == null) {
            return false
        }
        chatMessage.text = newText
        return true
    }

    fun deleteMessage(chatId: Int, messageId: Int): Boolean {
        val chat = chats.find { it.userId == chatId }
        val chatMessage = chats.find { it.userId == chatId }?.chatMessages?.find { it.messageId == messageId }
        if (chat == null || chatMessage == null) {
            return false
        }
        chat.chatMessages.remove(chatMessage)
        return true
    }

    fun getUnreadChatsCount(): Int {
        val unReadChat = chats.filter { chat -> chat.chatMessages.any { !it.isRead && it.isIncoming } }
        println(unReadChat)
        return unReadChat.size
    }

    fun getLastMessagesOfChats(): List<String> {
        val lastMessages = mutableListOf<String>()
        chats.forEach {
            if (it.chatMessages.isEmpty()) {
                println("Нет сообщений")
            } else {
                lastMessages += it.chatMessages.last().text
            }
        }
        return lastMessages
    }

    fun getListOfChatMessages(chatId: Int, messageId: Int, messageCount: Int) {
        val chat = chats.find { it.userId == chatId }
        val message = chat?.chatMessages?.find { it.messageId == messageId }
        if (chat == null || message == null) {
            return throw NotFoundIdException("Not found chat with id $chatId or not found message with id $messageId")
        }
        val idLastMessage = message.messageId
        println(chat.chatMessages.slice(idLastMessage until idLastMessage + messageCount))

        chat.chatMessages.map {
            if (it.isIncoming) {
                it.isRead = true
            }
        }
    }
}