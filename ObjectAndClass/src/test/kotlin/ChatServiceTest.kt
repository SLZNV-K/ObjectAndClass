import org.junit.Test
import com.github.stefanbirkner.systemlambda.SystemLambda.*
import org.junit.Assert.*

class ChatServiceTest {

    @Test
    fun createChat() {
        val chat = Chat(1, mutableListOf())
        val chatService = ChatService()
        val result = chatService.createChat(chat)

        assertTrue(result)
    }

    @Test
    fun deleteChatTrue() {
        val chat = Chat(1, mutableListOf())
        val chatService = ChatService()
        chatService.createChat(chat)
        val result = chatService.deleteChat(1)

        assertTrue(result)
    }

    @Test
    fun deleteChatFalse() {
        val chat = Chat(1, mutableListOf())
        val chatService = ChatService()
        chatService.createChat(chat)
        val result = chatService.deleteChat(10)

        assertFalse(result)
    }

    @Test
    fun getChats() {
        val chats = mutableListOf<Chat>()
        val chat = Chat(1, mutableListOf())
        val chatService = ChatService()
        chatService.createChat(chat)
        chats += chat
        val result = chatService.getChats()

        assertEquals(chats, result)
    }

    @Test
    fun createMessageTrue() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "1", true, isRead = false)
                )
            )
        )
        val result = chatService.createMessage(0, ChatMessage(9, "2", true, true))

        assertTrue(result)
    }

    @Test
    fun createMessageFalse() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "1", true, isRead = false)
                )
            )
        )
        val result = chatService.createMessage(10, ChatMessage(9, "2", true, true))

        assertFalse(result)
    }

    @Test
    fun editMessageTrue() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "1", true, isRead = false)
                )
            )
        )
        val result = chatService.editMessage(0, 0, "nt")

        assertTrue(result)
    }

    @Test
    fun editMessageFalse() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "1", true, isRead = false)
                )
            )
        )
        val result = chatService.editMessage(10, 0, "nt")

        assertFalse(result)
    }

    @Test
    fun deleteMessageTrue() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "1", true, isRead = false)
                )
            )
        )
        val result = chatService.deleteMessage(0, 0)

        assertTrue(result)
    }

    @Test
    fun deleteMessageFalse() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "1", true, isRead = false)
                )
            )
        )
        val result = chatService.deleteMessage(10, 0)

        assertFalse(result)
    }

    @Test
    fun getUnreadChatsCount() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "1", true, isRead = false)
                )
            )
        )
        val result = chatService.getUnreadChatsCount()

        assertEquals(1, result)
    }

    @Test
    fun getLastMessagesOfChats() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "1", true, isRead = false)
                )
            )
        )
        val result = chatService.getLastMessagesOfChats()

        assertEquals(listOf("1"), result)

    }

    @Test
    fun getListOfChatMessagesTrue() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "0", true, isRead = false),
                    ChatMessage(1, "1", false, isRead = true),
                    ChatMessage(2, "2", false, isRead = true),
                    ChatMessage(3, "3", true, isRead = false),
                    ChatMessage(4, "4", false, isRead = false),
                    ChatMessage(5, "5", false, isRead = true)
                )
            )
        )
        val output = tapSystemOut{chatService.getListOfChatMessages(0, 3, 1)}

        assertEquals(listOf(ChatMessage(3, "3", true, isRead = false)).toString(), output.trim())
    }

    @Test(expected = NotFoundIdException::class)
    fun getListOfChatMessagesFalse() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "0", true, isRead = false),
                    ChatMessage(1, "1", false, isRead = true),
                    ChatMessage(2, "2", false, isRead = true),
                    ChatMessage(3, "3", true, isRead = false),
                    ChatMessage(4, "4", false, isRead = false),
                    ChatMessage(5, "5", false, isRead = true)
                )
            )
        )
        chatService.getListOfChatMessages(10, 3, 1)
    }
}