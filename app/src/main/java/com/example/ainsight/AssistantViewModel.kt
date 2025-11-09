package com.example.ainsight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runanywhere.sdk.public.RunAnywhere
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class AssistantViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun sendMessage(prompt: String) {
        if (prompt.isBlank()) return

        // Add user message
        val userMessage = ChatMessage(text = prompt, isUser = true)
        _messages.value = _messages.value + userMessage

        // Generate response
        generateResponse(prompt)
    }

    private fun generateResponse(prompt: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                _error.value = null

                // Initialize assistant response
                var assistantResponse = ""
                val assistantMessage = ChatMessage(text = "", isUser = false)
                _messages.value = _messages.value + assistantMessage

                // Stream response from RunAnywhere SDK
                RunAnywhere.generateStream(prompt).collect { token ->
                    assistantResponse += token

                    // Update the assistant message in real-time
                    val updatedMessages = _messages.value.toMutableList()
                    val lastIndex = updatedMessages.lastIndex
                    if (lastIndex >= 0 && !updatedMessages[lastIndex].isUser) {
                        updatedMessages[lastIndex] =
                            updatedMessages[lastIndex].copy(text = assistantResponse)
                        _messages.value = updatedMessages
                    }
                }

            } catch (e: Exception) {
                _error.value = "Failed to generate response: ${e.message}"

                // Remove the empty assistant message if there was an error
                val updatedMessages = _messages.value.toMutableList()
                if (updatedMessages.isNotEmpty() && !updatedMessages.last().isUser && updatedMessages.last().text.isEmpty()) {
                    updatedMessages.removeAt(updatedMessages.lastIndex)
                    _messages.value = updatedMessages
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessages() {
        _messages.value = emptyList()
        _error.value = null
    }

    fun clearError() {
        _error.value = null
    }
}