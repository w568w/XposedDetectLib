cmake_minimum_required(VERSION 3.4.1)

IF (${CMAKE_HOST_SYSTEM_NAME} MATCHES "Windows")
    ADD_DEFINITIONS(-DWindows)
ELSE (${CMAKE_HOST_SYSTEM_NAME} MATCHES "Linux")
    ADD_DEFINITIONS(-DLinux)
ENDIF ()
add_library(
        xposed_check
        SHARED
        ../library/src/main/cpp/xposed_check.cpp
        ../library/src/main/cpp/pmparser.c)
include_directories(../app/src/main/cpp)
find_library(
        log-lib
        log)
target_link_libraries(
        xposed_check
        ${log-lib})