/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: shubhamjain
 *
 * Created on January 30, 2018, 9:12 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
 * 
 */
int *result() {

    int *l = (int*) malloc(2 * sizeof (int));
    for (int i = 0; i < 2; ++i) {
        *(l + i) = i;
    }
    int *r = (int*) malloc(2 * sizeof (int));
    for (int i = 0; i < 2; ++i) {
        *(r + i) = i + 2;
    }

    for (int i = 0; i < 2; ++i) {
        printf("\n%d", *(l + i));
    }
    for (int i = 0; i < 2; ++i) {
        printf("\n%d", *(r + i));
    }
    int *result = (int*) malloc(4 * sizeof (int));
    for (int i = 0; i < 2; ++i)
        *(result + i) = *(l + i);
    for (int j = 0; j < 2; ++j)
        *(result + j + 2) = *(r + j);
    return result;
}

int main(int argc, char** argv) {

    printf("here3");
    int *res = result();
    // (res) = -1;
    printf("Hello, World %d", sizeof (res));

    
    for (int i = 0; i < 4; ++i) {
        printf("\n%d", *(res + i));
    }
    
    return (EXIT_SUCCESS);
}

