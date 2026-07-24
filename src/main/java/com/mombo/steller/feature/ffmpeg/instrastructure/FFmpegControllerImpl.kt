package com.mombo.steller.feature.ffmpeg.instrastructure

import android.text.TextUtils
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFprobeKit
import com.arthenica.ffmpegkit.ReturnCode
import com.mombo.steller.feature.ffmpeg.domain.FFmpegController
import org.json.JSONException
import timber.log.Timber
import kotlin.coroutines.suspendCoroutine

internal class FFmpegControllerImpl : FFmpegController {

    private val mutex = Mutex()

    override suspend fun execute(ffmpegCommand: String): Boolean = mutex.withLock {
        val resultSession = FFmpegKit.execute(ffmpegCommand)
        handleResult(resultSession.returnCode, resultSession.command)
    }

    override suspend fun executeWithArguments(arguments: Array<String>): Boolean = mutex.withLock {
        val resultSession = FFmpegKit.executeWithArguments(arguments)
        handleResult(resultSession.returnCode, resultSession.command)
    }

    override suspend fun executeAsync(ffmpegCommand: String): Boolean = mutex.withLock {
        suspendCoroutine {
            FFmpegKit.executeAsync(ffmpegCommand) { session ->
                it.resumeWith(Result.success(handleResult(session.returnCode, session.command)))
            }
        }
    }

    override suspend fun executeWithArgumentsAsync(arguments: Array<String>): Boolean = mutex.withLock {
        suspendCoroutine {
            FFmpegKit.executeWithArgumentsAsync(arguments) { session ->
                it.resumeWith(Result.success(handleResult(session.returnCode, session.command)))
            }
        }
    }

    private fun handleResult(returnCode: ReturnCode, command: String?): Boolean {
        return if (returnCode.isValueError) {
            Timber.e("Last FFMPEG command error code - %s", returnCode.value)
            if (!TextUtils.isEmpty(command)) {
                Timber.e(command)
            }
            false
        } else {
            true
        }
    }

    override fun getMetadataLocationUsingFFProbe(mediaPath: String): String? {
        val mediaSession = FFprobeKit.getMediaInformation(mediaPath) ?: return null

        val tags = mediaSession.mediaInformation.tags ?: return null

        if (tags.has(METADATA_KEY_LOCATION)) {
            try {
                return tags.getString(METADATA_KEY_LOCATION)
            } catch (e: JSONException) {
                // nothing to do - file does not have metadata for location
            }
        }

        return null
    }

    companion object{
        const val METADATA_KEY_LOCATION = "location"
    }

}