package com.mombo.steller.feature.ffmpeg

import com.mombo.steller.feature.ffmpeg.domain.FFmpegController
import com.mombo.steller.feature.ffmpeg.instrastructure.FFmpegControllerImpl
import org.koin.dsl.module


private val ffmpegModule = module {

    // infrastructure


    // data


    // domain

    single { FFmpegControllerImpl() as FFmpegController }

}

// let's do it always this way so we are ready to separate into multiple modules even inside single feature module
val featureFFmpegModules = listOf(ffmpegModule)
