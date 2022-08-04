package com.mombo.steller.feature.ffmpeg.domain


interface FFmpegController {

    fun execute(ffmpegCommand: String): Boolean

    suspend fun executeAsync(ffmpegCommand: String): Boolean

    fun getMetadataLocationUsingFFProbe(mediaPath: String): String?

}