LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := slf4j-api
LOCAL_SRC_FILES := $(call all-java-files-under, src/main/java)

include $(BUILD_STATIC_JAVA_LIBRARY)
