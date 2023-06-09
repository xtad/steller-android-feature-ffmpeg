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

    override fun execute(ffmpegCommand: String): Boolean {
        val resultSession = FFmpegKit.execute(ffmpegCommand)

        return if (resultSession.returnCode.isValueError) {
            val lastCommandOutput = resultSession.command

            // Log last command output for error state - we need to track what's going on on these devices

            // Log last command output for error state - we need to track what's going on on these devices
            Timber.e("Last command error code - %s", resultSession.returnCode.value)
            if (!TextUtils.isEmpty(lastCommandOutput)) {
                Timber.e(lastCommandOutput)
            }

            false
        }else{
            true
        }
    }

    override suspend fun executeAsync(ffmpegCommand: String): Boolean {
        val resultCode = suspendCoroutine<ReturnCode> {
            FFmpegKit.executeAsync(
                ffmpegCommand
            ) { session ->
                it.resumeWith(Result.success(session.returnCode))
            }
        }

        return resultCode.isValueSuccess
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