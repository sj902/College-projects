/**This file returns multiple elements sampled from the tree**/

#include <stdio.h>
#include <pthread.h>
#include "bloom.h"
#include <stdlib.h>
#include "bloomTree.c"
#include "bestLevel.c"

#define HEADS 0
#define TAILS 1

extern int VECT_SIZE;
extern int levelThreshold;

struct bloom *mainFilter;
struct bloomTree *rooted;

struct arguments {
    struct bloom *k;
    struct bloomNode *r;
    int noOfSampledElements;
};

/**This returns Heads or tails
 * Parameter:
 * probability - probability of getting a HEADS**/
int coinToss(float probability) {

    double random = rand() % 1000000 + 1; //Generate a random number

    if (probability * 1000000 < random) return TAILS;
    else return HEADS;
}





/**This function will return samples from the Bloom Tree**/
int *sampleValues(void *arguments) {

    struct arguments *arg = arguments;

    struct bloom *k = arg->k;
    struct bloomNode *r = arg->r;
    int noOfSampledElements = arg->noOfSampledElements;

    /**if both the left and right children are empty,
     * then calculate **/
    if ((!r->lchild) && (!r->rchild)) {
        int i = 0, j;
        int numValues = r->end - r->start + 1;
        int *values = (int *) malloc(sizeof(int) * numValues);
        for (j = r->start; j <= r->end; j++) {
            if (is_in(j, k)) {
                values[i] = j;
                i++;
            }
        }

        /**if intersection is empty return -1**/
        if (i == 0) {
            free(values);
            int res = -1;
            return &res;
        }


        /**else find required random numbers and return them**/
        int *result = (int *) malloc(sizeof(int) * noOfSampledElements);
        for (int k = 0; k < noOfSampledElements; ++k) {
            int index = rand() % i;
            result[k] = values[index];
            //printf("\n%d,%d,%d",index, values[index], result[k]);
        }
        free(values);
        return result;
    }


        /**if any of the children is non-empty,
         * then choose the next leaf according to probability as stated in paper (Pt.3 of algo)**/
    else {

        double lElems = elemsIntersection(&(r->lchild->filter), k, r->lchild->nOnes, noOfSampledElements);
        double rElems = elemsIntersection(&(r->rchild->filter), k, r->rchild->nOnes, noOfSampledElements);

        int flagL = (lElems > 0) ? 1 : 0;
        int flagR = (rElems > 0) ? 1 : 0;

        if ((flagL) && (!flagR)) {
            struct arguments args;
            args.noOfSampledElements = noOfSampledElements;
            args.k = k;
            args.r = r->lchild;
            pthread_t tid;
            int *status;
            pthread_create(&tid, NULL, sampleValues, (void *) &args);
            pthread_join(tid, &status);
            return status;
            //if only left node has non-zero intersection
        }
        if ((!flagL) && (flagR)) {
            struct arguments args;
            args.noOfSampledElements = noOfSampledElements;
            args.k = k;
            args.r = r->rchild;
            pthread_t tid;
            int *status;
            pthread_create(&tid, NULL, sampleValues, (void *) &args);
            pthread_join(tid, &status);
            return status;
        }
        //if both have 0 intersection then return -1
        if ((!flagL) && (!flagR)) {
            int res = -1;
            return &res;
        }

        //refer to "Sampling multiple items" in 5.3 in paper
        double probability = lElems / (lElems + rElems);
        int noOfTails = 0;
        int *result;

        for (int i = 0; i < noOfSampledElements; ++i) {
            noOfTails += coinToss(probability);
        }

        int noOfHeads = noOfSampledElements - noOfTails;


        struct arguments largs;
        largs.noOfSampledElements = noOfHeads;
        largs.k = k;
        largs.r = r->lchild;
        int *lSample = sampleValues((void *) &largs);

        //sampleValues(k, r->lchild, noOfSampledElements - noOfTails);


        struct arguments rargs;
        rargs.noOfSampledElements = noOfTails;
        rargs.k = k;
        rargs.r = r->rchild;
        int *rSample = sampleValues((void *) &rargs);

        //if both return -1 then return -1
        if (*(lSample) == -1 && *rSample == -1) {
            int res = -1;
            return &res;
        } else if (*rSample == -1) {
            struct arguments largs;
            largs.noOfSampledElements = noOfTails;
            largs.k = k;
            largs.r = r->lchild;
            pthread_t tid;
            int *status;
            pthread_create(&tid, NULL, sampleValues, (void *) &largs);
            pthread_join(tid, &status);
            return status;
            //return sampleValues((void *) &largs);
        } else if (*(lSample) == -1) {
            struct arguments rargs;
            rargs.noOfSampledElements = noOfHeads;
            rargs.k = k;
            rargs.r = r->rchild;
            pthread_t tid;
            int *status;
            pthread_create(&tid, NULL, sampleValues, (void *) &rargs);
            pthread_join(tid, &status);
            return status;
        }
            //if both return some values, then concatenate and return the list of numbers
        else {
            result = (int *) malloc(noOfSampledElements * sizeof(int));
            for (int i = 0; i < noOfSampledElements - noOfTails; ++i)
                *(result + i) = *(lSample + i);
            for (int j = 0; j < noOfTails; ++j)
                *(result + j + noOfSampledElements - noOfTails) = *(rSample + j);
        }

        return result;
    }
}




//Pick up here



int *samplesFromTree(struct bloom *k, struct bloomTree *r, int noOfSampledElements) {

    double lElems = elemsIntersection(&(r->left->filter), k, r->left->nOnes, noOfSampledElements);
    double rElems = elemsIntersection(&(r->right->filter), k, r->right->nOnes, noOfSampledElements);

    int flagL = (lElems > 0) ? 1 : 0;
    int flagR = (rElems > 0) ? 1 : 0;


    if ((flagL) && (!flagR)) {
        struct arguments args;
        args.noOfSampledElements = noOfSampledElements;
        args.k = k;
        args.r = r->left;
        return sampleValues((void *) &args);
        //if only left node has non-zero intersection
    }
    if ((!flagL) && (flagR)) {
        struct arguments args;
        args.noOfSampledElements = noOfSampledElements;
        args.k = k;
        args.r = r->right;
        return sampleValues((void *) &args);
    }
    //if both have 0 intersection then return -1
    if ((!flagL) && (!flagR)) {
        int res = -1;
        return &res;
    }

    //refer to "Sampling multiple items" in 5.3 in paper
    double probability = lElems / (lElems + rElems);
    int noOfTails = 0;
    int *result;

    for (int i = 0; i < noOfSampledElements; ++i) {
        noOfTails += coinToss(probability);
    }

    int noOfHeads = noOfSampledElements - noOfTails;

    struct arguments largs;
    largs.noOfSampledElements = noOfHeads;
    largs.k = k;
    largs.r = r->left;
    int *lSample = sampleValues((void *) &largs);


    struct arguments rargs;
    rargs.noOfSampledElements = noOfTails;
    rargs.k = k;
    rargs.r = r->right;
    int *rSample = sampleValues((void *) &rargs);


    //if both return -1 then return -1
    if (*(lSample) == -1 && *rSample == -1) {
        int res = -1;
        return &res;
    } else if (*rSample == -1) {
        struct arguments largs;
        largs.noOfSampledElements = noOfTails;
        largs.k = k;
        largs.r = r->left;
        return sampleValues((void *) &largs);
    } else if (*(lSample) == -1) {
        struct arguments rargs;
        rargs.noOfSampledElements = noOfHeads;
        rargs.k = k;
        rargs.r = r->right;
        return sampleValues((void *) &rargs);
    }
        //if both return some values, then concatenate and return the list of numbers
    else {
        result = (int *) malloc(noOfSampledElements * sizeof(int));
        for (int i = 0; i < noOfHeads; ++i) {
            *(result + i) = *(lSample + i);
        }
        for (int j = 0; j < noOfTails; ++j) {
            *(result + j + noOfHeads) = *(rSample + j);
        }
    }

    return result;
}







int main(int argc, char *argv[]) {

    if (argc < 5) {
        printf("Usage: ./sampleMultipleFromTree m n M numberOfSamples");
        exit(0);
    }
    int m = atoi(argv[1]);
    int n = atoi(argv[2]);

    VECT_SIZE = m;

    long int M = atol(argv[3]);
    int numSamples = atoi(argv[4]);

    levelThreshold = bestLevel(m, n, M);

    seiveInitial();

    mainFilter = (struct bloom *) malloc(sizeof(struct bloom));
    mainFilter->bloom_vector = (int *) malloc(sizeof(int) * (m / NUM_BITS + 1));

    int i;
    for (i = 0; i < n; i++) {
        int v = rand() % M;
        insert(v, mainFilter);
        v = rand() % M;
    }

    rooted = getBloomTree(1, M);

    clock_t start, end;
    double cpu_time_used;

    start = clock();
    int *result = samplesFromTree(mainFilter, rooted, numSamples);
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;


    printf("\nCost:%f", cpu_time_used);

    printf("\nResults:");
    for (int i = 0; i < numSamples; ++i)
        printf("\n%d", *(result + i));

}