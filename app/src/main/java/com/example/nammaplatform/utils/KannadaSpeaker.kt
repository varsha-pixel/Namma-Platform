
package com.example.nammaplatform.utils

import android.content.Context
import android.media.AudioManager
import android.speech.tts.TextToSpeech
import java.util.Locale

class KannadaSpeaker(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech =
        TextToSpeech(context, this)

    private var isReady = false

    private val audioManager =
        context.getSystemService(Context.AUDIO_SERVICE)
                as AudioManager

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {

            val result =
                tts.setLanguage(Locale("kn", "IN"))

            if (
                result != TextToSpeech.LANG_MISSING_DATA &&
                result != TextToSpeech.LANG_NOT_SUPPORTED
            ) {

                isReady = true

                // MAX VOLUME
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(
                        AudioManager.STREAM_MUSIC
                    ),
                    0
                )

                // CLEAR VOICE
                tts.setSpeechRate(0.70f)

                // NORMAL PITCH
                tts.setPitch(1.0f)
            }
        }
    }

    fun speak(
        trainName: String,
        platform: String
    ) {

        if (!isReady) return

        val message =
            "$trainName ರೈಲು, ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ಸಂಖ್ಯೆ $platform ಗೆ ಶೀಘ್ರದಲ್ಲೇ ಬರುತ್ತದೆ. ದಯವಿಟ್ಟು ಸುರಕ್ಷಿತವಾಗಿ ನಿಲ್ಲಿ."

        tts.speak(
            message,
            TextToSpeech.QUEUE_FLUSH,
            null,
            null
        )
    }

    fun shutdown() {

        tts.stop()
        tts.shutdown()
    }
}

