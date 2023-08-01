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
    fun editMessage() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "1", true, isRead = false)
                )
            )
        )
        chatService.editMessage(0, 0, "nt")

        assertEquals("nt", chatService.getChats()[0].chatMessages[0].text)
    }
    @Test(expected = NotFoundMessage::class)
    fun editMessageThrow() {
        val chatService = ChatService()
        chatService.createChat(
            Chat(
                0, mutableListOf
                    (
                    ChatMessage(0, "1", true, isRead = false)
                )
            )
        )
        chatService.editMessage(0, 10, "nt")

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

    @Test(expected = NotFoundIdException::class)
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

        assertEquals("1", result)

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
        val result = chatService.getListOfChatMessages(0, 3, 1)

        assertEquals(listOf(ChatMessage(3, "3", true, isRead = true)),result )
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