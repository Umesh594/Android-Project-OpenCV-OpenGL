cmake_minimum_required(VERSION 3.4.1)
set(OpenCV_DIR "C:/Users/Umesh/Downloads/opencv-4.5.5-android-sdk/OpenCV-android-sdk/sdk/native/jni")
find_package(OpenCV REQUIRED)
include_directories(${OpenCV_INCLUDE_DIRS})
add_library(native-lib SHARED
        src/main/cpp/native-lib.cpp
        )
find_library(log-lib log)
find_library(GLESv2-lib GLESv2)
target_link_libraries(native-lib
        ${OpenCV_LIBS}
        ${log-lib}
        ${GLESv2-lib}
        )