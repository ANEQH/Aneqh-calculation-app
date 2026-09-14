package com.example.util

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.*
import kotlin.math.sin
import kotlin.random.Random

enum class AmbientSoundType(val label: String, val hindi: String) {
    NONE("Mute / Off", "म्यूट / बंद"),
    BINAURAL_ALPHA("Alpha Waves (432Hz)", "अल्फा तरंगे (एकाग्रता)"),
    RAIN_NOISE("Gentle Rain Noise", "वर्षा व व्हाइट नॉइज़"),
    ZEN_TICK("Zen Clock Ticking", "घड़ी की टिक-टिक")
}

class FocusSoundEngine {
    private var audioTrack: AudioTrack? = null
    private var soundJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var currentType: AmbientSoundType = AmbientSoundType.NONE
    private var isMuted = false

    fun playSound(type: AmbientSoundType) {
        if (type == currentType && audioTrack != null) return
        stopSound()
        currentType = type
        if (type == AmbientSoundType.NONE) return

        soundJob = scope.launch {
            try {
                val sampleRate = 44100
                val minBufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )
                val bufferSize = maxOf(minBufferSize, sampleRate / 2)

                audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(bufferSize * 2)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                audioTrack?.play()
                val buffer = ShortArray(bufferSize)

                when (type) {
                    AmbientSoundType.BINAURAL_ALPHA -> {
                        // 432Hz carrier wave with subtle 10Hz amplitude modulation (Alpha Brainwave)
                        var phaseCarrier = 0.0
                        var phaseMod = 0.0
                        val carrierFreq = 432.0
                        val modFreq = 10.0
                        val twoPi = 2.0 * Math.PI

                        while (isActive) {
                            for (i in buffer.indices) {
                                val mod = 0.7 + 0.3 * sin(phaseMod)
                                val sample = (sin(phaseCarrier) * mod * 0.18 * Short.MAX_VALUE).toInt().toShort()
                                buffer[i] = sample

                                phaseCarrier += twoPi * carrierFreq / sampleRate
                                if (phaseCarrier >= twoPi) phaseCarrier -= twoPi

                                phaseMod += twoPi * modFreq / sampleRate
                                if (phaseMod >= twoPi) phaseMod -= twoPi
                            }
                            audioTrack?.write(buffer, 0, buffer.size)
                        }
                    }

                    AmbientSoundType.RAIN_NOISE -> {
                        // Soft filtered pink/brown noise for rain ambience
                        var lastVal = 0.0
                        while (isActive) {
                            for (i in buffer.indices) {
                                val white = Random.nextDouble(-1.0, 1.0)
                                lastVal = (lastVal * 0.95) + (white * 0.05) // low pass filter for soft rain sound
                                val sample = (lastVal * 0.22 * Short.MAX_VALUE).toInt().toShort()
                                buffer[i] = sample
                            }
                            audioTrack?.write(buffer, 0, buffer.size)
                        }
                    }

                    AmbientSoundType.ZEN_TICK -> {
                        // Precise gentle click tick once per second
                        val silenceLen = sampleRate - 1200
                        val tickBuffer = ShortArray(1200)
                        for (i in tickBuffer.indices) {
                            val decay = (1.0 - i.toDouble() / tickBuffer.size)
                            val freq = 1200.0
                            val sample = (sin(2.0 * Math.PI * freq * i / sampleRate) * decay * 0.2 * Short.MAX_VALUE).toInt().toShort()
                            tickBuffer[i] = sample
                        }
                        val silenceBuffer = ShortArray(silenceLen)

                        while (isActive) {
                            audioTrack?.write(tickBuffer, 0, tickBuffer.size)
                            audioTrack?.write(silenceBuffer, 0, silenceBuffer.size)
                        }
                    }

                    AmbientSoundType.NONE -> {
                        // Silent
                    }
                }
            } catch (e: Exception) {
                // Fallback gracefully if device blocks audio stream
            }
        }
    }

    fun stopSound() {
        soundJob?.cancel()
        soundJob = null
        try {
            audioTrack?.let {
                it.stop()
                it.release()
            }
        } catch (e: Exception) {
            // Ignore
        }
        audioTrack = null
        currentType = AmbientSoundType.NONE
    }

    fun release() {
        stopSound()
        scope.cancel()
    }
}
