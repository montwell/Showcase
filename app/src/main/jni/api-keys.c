#include <jni.h>

/*
 *  https://www.themoviedb.org/documentation/api
 */
JNIEXPORT jstring JNICALL
Java_com_montwell_showcase_api_TheMovieDbOrgService_00024Companion_getApiKey(
        JNIEnv *env,
        jobject thiz) {
            return (*env)->  NewStringUTF(env, "YOUR_API_KEY");
}