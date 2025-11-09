package com.example.ainsight

import android.app.Application
import android.util.Log
import com.runanywhere.sdk.public.RunAnywhere
import com.runanywhere.sdk.data.models.SDKEnvironment
import com.runanywhere.sdk.public.extensions.addModelFromURL
import com.runanywhere.sdk.llm.llamacpp.LlamaCppServiceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize SDK asynchronously
        GlobalScope.launch(Dispatchers.IO) {
            initializeSDK()
        }
    }

    private suspend fun initializeSDK() {
        try {
            // Step 1: Initialize SDK
            RunAnywhere.initialize(
                context = this@MainApplication,
                apiKey = "dev",  // Development API key
                environment = SDKEnvironment.DEVELOPMENT
            )

            // Step 2: Register LLM Service Provider
            LlamaCppServiceProvider.register()

            // Step 3: Register Models
            registerModels()

            // Step 4: Scan for previously downloaded models
            RunAnywhere.scanForDownloadedModels()

            Log.i("AInSight", "RunAnywhere SDK initialized successfully")

        } catch (e: Exception) {
            Log.e("AInSight", "SDK initialization failed: ${e.message}", e)
        }
    }

    private suspend fun registerModels() {
        try {
            // Smallest model - great for testing (119 MB)
            addModelFromURL(
                url = "https://huggingface.co/prithivMLmods/SmolLM2-360M-GGUF/resolve/main/SmolLM2-360M.Q8_0.gguf",
                name = "SmolLM2 360M Q8_0",
                type = "LLM"
            )

            // Medium-sized model - better quality (374 MB)
            addModelFromURL(
                url = "https://huggingface.co/Triangle104/Qwen2.5-0.5B-Instruct-Q6_K-GGUF/resolve/main/qwen2.5-0.5b-instruct-q6_k.gguf",
                name = "Qwen 2.5 0.5B Instruct Q6_K",
                type = "LLM"
            )

            Log.i("AInSight", "Models registered successfully")

        } catch (e: Exception) {
            Log.e("AInSight", "Model registration failed: ${e.message}", e)
        }
    }
}