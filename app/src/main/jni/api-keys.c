#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_montwell_showcase_data_api_TheMovieDbOrgService_00024Companion_getApiKey(
        JNIEnv *env,
        jobject thiz) {
            return (*env)->  NewStringUTF(env, "YOUR_API_KEY");
}