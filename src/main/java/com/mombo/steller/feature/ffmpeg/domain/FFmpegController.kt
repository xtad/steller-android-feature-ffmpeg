package com.mombo.steller.feature.ffmpeg.domain


interface FFmpegController {

    suspend fun execute(ffmpegCommand: String): Boolean

    suspend fun executeWithArguments(arguments: Array<String>): Boolean

    suspend fun executeAsync(ffmpegCommand: String): Boolean

    suspend fun executeWithArgumentsAsync(arguments: Array<String>): Boolean

    fun getMetadataLocationUsingFFProbe(mediaPath: String): String?

}