# AInSight - AI-Powered Mobile Assistant

AInSight is a privacy-first, on-device AI assistant for Android that combines text chat, voice
transcription, and team collaboration features using the RunAnywhere SDK.

## Features

### 🤖 AI Assistant

- **On-device LLM inference** using RunAnywhere SDK
- **Real-time streaming responses** for natural conversations
- **Multiple model support** with automatic CPU optimization
- **Privacy-first** - all processing happens locally on your device

### 🎙️ Voice Notes

- **Voice-to-text transcription** with RunAnywhere's speech recognition
- **Task extraction** from voice recordings using AI
- **Permission-based recording** with intuitive UI
- **History management** for all voice notes

### 👥 Team Chat (Coming Soon)

- **Collaborative AI conversations** with team members
- **Shared voice notes** and task management
- **Team model preferences** and settings
- **Real-time messaging** integration

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **AI SDK**: RunAnywhere SDK v0.1.3-alpha
- **Architecture**: MVVM with StateFlow
- **Navigation**: Jetpack Navigation Compose
- **Permissions**: Accompanist Permissions

## Setup Instructions

### Prerequisites

- Android Studio (latest version)
- JDK 17+
- Minimum Android SDK 24 (Android 7.0)
- Device with at least 2GB RAM (recommended 4GB+ for larger models)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ainsight
   ```

2. **Open in Android Studio**
    - Open Android Studio
    - Select "Open an existing project"
    - Navigate to the project directory

3. **Sync the project**
    - Android Studio will automatically prompt to sync
    - Click "Sync Now" to download dependencies
    - Wait for the RunAnywhere SDK to be fetched (first build takes 2-3 minutes)

4. **Run the app**
    - Connect an Android device or start an emulator
    - Click "Run" in Android Studio

### First Launch

1. **Grant permissions** when prompted (microphone for voice features)
2. **Download a model** - the app will register small models automatically:
    - SmolLM2 360M (119 MB) - Fast, basic responses
    - Qwen 2.5 0.5B (374 MB) - Better quality conversations
3. **Start chatting** with your on-device AI assistant!

## Project Structure

```
app/src/main/java/com/example/ainsight/
├── MainActivity.kt              # Main activity with navigation
├── MainApplication.kt           # Application class with SDK initialization
├── AssistantScreen.kt           # LLM chat interface
├── AssistantViewModel.kt        # Chat logic and state management
├── VoiceNoteScreen.kt          # Voice recording interface
├── VoiceNoteViewModel.kt       # Voice processing logic
├── TeamChatScreen.kt           # Team collaboration (placeholder)
└── ui/theme/                   # Material 3 theming
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

## SDK Integration

### RunAnywhere SDK Setup

The app uses the RunAnywhere SDK for on-device AI capabilities:

```kotlin
// SDK Initialization in MainApplication.kt
RunAnywhere.initialize(
    context = this,
    apiKey = "dev",
    environment = SDKEnvironment.DEVELOPMENT
)

// Register LLM provider
LlamaCppServiceProvider.register()

// Register models
addModelFromURL(
    url = "https://huggingface.co/...",
    name = "Model Name",
    type = "LLM"
)
```

### Available Models

- **SmolLM2 360M Q8_0** (119 MB) - Quick testing and basic Q&A
- **Qwen 2.5 0.5B Instruct Q6_K** (374 MB) - Balanced performance and quality
- **Llama 3.2 1B Instruct Q6_K** (815 MB) - Higher quality conversations

## Key Features Implementation

### Streaming Chat

```kotlin
// Real-time response streaming
RunAnywhere.generateStream(prompt).collect { token ->
    assistantResponse += token
    // Update UI in real-time
}
```

### Voice Permissions

```kotlin
// Permission handling with Accompanist
val audioPermissionState = rememberPermissionState(
    android.Manifest.permission.RECORD_AUDIO
)
```

### Navigation

```kotlin
// Bottom navigation with 3 tabs
sealed class Screen(val route: String, val title: String, val icon: ImageVector)
```

## Development

### Building the Project

```bash
./gradlew assembleDebug
```

### Running Tests

```bash
./gradlew test
```

### Code Style

The project follows standard Kotlin coding conventions with:

- MVVM architecture pattern
- StateFlow for reactive state management
- Jetpack Compose for declarative UI

## Performance Considerations

### Model Selection

- **SmolLM2 360M**: Fastest inference, suitable for basic tasks
- **Qwen 2.5 0.5B**: Good balance of speed and quality
- **Larger models**: Better quality but require more RAM and processing time

### Device Requirements

- **Minimum**: Android 7.0, 2GB RAM
- **Recommended**: Android 10+, 4GB+ RAM
- **Optimal**: Android 12+, 6GB+ RAM with dynamic colors

## Privacy & Security

- **On-device processing**: All AI inference happens locally
- **No data transmission**: Conversations never leave your device
- **Local model storage**: Models are downloaded and cached locally
- **Permission-based**: Only requests necessary permissions

## Troubleshooting

### Common Issues

1. **Model download fails**
    - Check internet connection
    - Verify sufficient storage space
    - Try a different model

2. **App crashes during inference**
    - Close other apps to free memory
    - Try a smaller model (SmolLM2 360M)
    - Restart the device

3. **Voice recording not working**
    - Grant microphone permission in Settings
    - Check device microphone functionality
    - Restart the app

### Performance Optimization

- Enable "Large Heap" in manifest (already configured)
- Close background apps during AI inference
- Use Wi-Fi for model downloads to avoid cellular data charges

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- **RunAnywhere AI** for the on-device AI SDK
- **Google** for Jetpack Compose and Material Design
- **Hugging Face** for hosting the AI models
- **Open source community** for various libraries and tools

## Support

For support, please:

1. Check the troubleshooting section above
2. Review RunAnywhere SDK documentation
3. Open an issue in this repository
4. Contact the development team

---

**Built with ❤️ for privacy-conscious AI users**