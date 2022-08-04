package com.mombo.steller.feature.ffmpeg.instrastructure

import android.text.TextUtils
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.arthenica.mobileffmpeg.FFprobe
import com.mombo.steller.feature.ffmpeg.domain.FFmpegController
import org.json.JSONException
import timber.log.Timber
import kotlin.coroutines.suspendCoroutine

internal class FFmpegControllerImpl : FFmpegController {

    override fun execute(ffmpegCommand: String): Boolean {
        val resultCode = FFmpeg.execute(ffmpegCommand)

        return if (resultCode != Config.RETURN_CODE_SUCCESS) {
            val lastCommandOutput = Config.getLastCommandOutput()

            // Log last command output for error state - we need to track what's going on on these devices

            // Log last command output for error state - we need to track what's going on on these devices
            Timber.e("Last command error code - %s", resultCode)
            if (!TextUtils.isEmpty(lastCommandOutput)) {
                Timber.e(lastCommandOutput)
            }

            false
        }else{
            true
        }
    }

    override suspend fun executeAsync(ffmpegCommand: String): Boolean {
        val resultCode = suspendCoroutine<Int> {
            FFmpeg.executeAsync(
                ffmpegCommand
            ) { executionId, returnCode ->
                it.resumeWith(Result.success(returnCode))
            }
        }

        return resultCode == Config.RETURN_CODE_SUCCESS
    }

    override fun getMetadataLocationUsingFFProbe(mediaPath: String): String? {
        val mediaInformation = FFprobe.getMediaInformation(mediaPath) ?: return null

        val tags = mediaInformation.tags ?: return null

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