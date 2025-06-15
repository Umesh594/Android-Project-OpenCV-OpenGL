#include <jni.h>
#include <opencv2/opencv.hpp>
#include <GLES2/gl2.h>
#include <android/log.h>
GLuint textureId;
void uploadTexture(const cv::Mat& img) {
    if (textureId == 0) {
        glGenTextures(1, &textureId);
    }
    glBindTexture(GL_TEXTURE_2D, textureId);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, img.cols, img.rows, 0, GL_LUMINANCE,
                 GL_UNSIGNED_BYTE, img.data);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
}
extern "C" JNIEXPORT void JNICALL
Java_com_example_edgeviewer_GLRenderer_processFrame(JNIEnv* env,jobject,jbyteArray frameData,jint width,jint height){
    jbyte* yuv=env->GetByteArrayElements(frameData, nullptr);
    cv::Mat yuvImage(height + height / 2, width, CV_8UC1, reinterpret_cast<unsigned char*>(yuv));
    cv::Mat bgrImage;
    cv::cvtColor(yuvImage,bgrImage,cv::COLOR_YUV2BGR_NV21);
    cv::Mat gray;
    cv::cvtColor(bgrImage,gray,cv::COLOR_BGR2GRAY);
    cv::Mat edges;
    cv::Canny(gray,edges,50,150);
    uploadTexture(edges);
    env->ReleaseByteArrayElements(frameData,yuv,0);
}
