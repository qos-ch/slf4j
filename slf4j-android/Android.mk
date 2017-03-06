LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := slf4j-android

api_src := $(call all-java-files-under, ../slf4j-api/src/main/java)
api_impl := $(call all-java-files-under, ../slf4j-api/src/main/java/org/slf4j/impl)
api_src_no_impl := $(filter-out $(api_impl), $(api_src))

LOCAL_SRC_FILES := $(api_src_no_impl) $(call all-java-files-under, src/main/java)

include $(BUILD_STATIC_JAVA_LIBRARY)
