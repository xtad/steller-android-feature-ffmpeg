# Steller Android FFMPEG module

This is our submodule for integrating FFMPEG wrapper into our project. `ffmpeg-kit` is wrapping [FFMPEG project](https://ffmpeg.org/) and making it accessible from Android code. 
This submodule is included into [main Steller project](https://github.com/xtad/steller-android-1) using `git submodule` system.

# Problems
Because `ffmpeg-kit` [library](https://github.com/arthenica/ffmpeg-kit) is deprecated now, we have [forked it](https://github.com/xtad/ffmpeg-kit). 
You can read more about reasons for deprecation [here](https://tanersener.medium.com/saying-goodbye-to-ffmpegkit-33ae939767e1) (2025)

We are hosting built library on our [Maven server](https://reposilite.dashboards.steller.co/#/) . Check it out for instructions how to include this library as a dependency.
