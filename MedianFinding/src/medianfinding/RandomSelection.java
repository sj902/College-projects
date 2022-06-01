/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medianfinding;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author shubhamjain
 */
public class RandomSelection {

    ArrayList array = new ArrayList<>();
    int rank;
    public int comparisons = 0;
    public long time;
    public int element;

    RandomSelection(ArrayList<Integer> argList, int argRank) {
        this.rank = argRank;
        this.array = argList;
        long startTime = System.currentTimeMillis();
        element = randomSelection(this.array, this.rank);
        long endTime = System.currentTimeMillis();
        this.time = endTime - startTime;
    }

    final int randomSelection(ArrayList<Integer> argList, int argRank) {

        //Get random element
        Random rand = new Random();
        int random = rand.nextInt(argList.size());

        //Declare arays
        ArrayList left = new ArrayList<>();
        ArrayList right = new ArrayList<>();

        //calculate rank of chosen element
        for (int i = 0; i < argList.size(); ++i) {
            if (i == random) {
                ++comparisons;
            } else if (argList.get(i) <= argList.get(random)) {
                ++comparisons;
                left.add(argList.get(i));
            } else if (argList.get(i) > argList.get(random)) {
                ++comparisons;
                right.add(argList.get(i));
            }
        }

        //Recurse
        if (left.size() == argRank - 1) {
            ++comparisons;
            return argList.get(random);
        } else if (left.size() > argRank - 1) {
            ++comparisons;
            return randomSelection(left, argRank);
        } else {
            return randomSelection(right, argRank - left.size() - 1);
        }
    }
}
