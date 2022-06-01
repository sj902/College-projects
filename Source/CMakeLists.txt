cmake_minimum_required(VERSION 3.9)
project(Source)

set(CMAKE_CXX_STANDARD 11)

add_executable(Source
        bloom.c
        bloom.h
        sampleMultipleFromTree.c)
