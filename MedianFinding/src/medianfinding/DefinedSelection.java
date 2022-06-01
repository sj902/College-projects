/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medianfinding;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author shubhamjain
 */
public class DefinedSelection {

    ArrayList array = new ArrayList<>();
    int rank;
    public int comparisons = 0;
    public long time;
    public int element;

    DefinedSelection(ArrayList<Integer> argList, int argRank) {
        this.rank = argRank;
        this.array = argList;
        long startTime = System.currentTimeMillis();
        element = definedSelection(this.array, this.rank);
        long endTime = System.currentTimeMillis();
        this.time = endTime - startTime;
    }

    final int definedSelection(ArrayList<Integer> argList, int argRank) {
        //Get pivot element
        int randomElement;

        if (argList.size() <= 5) {
            ArrayList temp = argList;
            Collections.sort(temp);
            comparisons+= temp.size()*temp.size();
            randomElement = (int) temp.get(temp.size() / 2);
        } else {
            randomElement = getPivot(argList);
        }

        int random = 0;
        for (int i = 0; i < argList.size(); ++i) {
            if (argList.get(i) == randomElement) {
                random = i;
                ++comparisons;
            }
        }

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
            return definedSelection(left, argRank);
        } else {
            return definedSelection(right, argRank - left.size() - 1);
        }
    }

    private int getPivot(ArrayList<Integer> argList) {

        int noOfPartitions = argList.size() / 5;
        if (argList.size() % 5 > 0) {
            ++noOfPartitions;
        }

        ArrayList medianList = new ArrayList<>();

        for (int i = 0; i < noOfPartitions; ++i) {

            ArrayList temp = new ArrayList<>();
            
            int start = i * 5;
            int finish = Math.min(argList.size() - start, 5);

            for (int j = 0; j < finish; ++j) {
                temp.add(argList.get(start + j));
            }

            Collections.sort(temp);
            comparisons+=25;
            if (temp.size() == 5) {
                medianList.add(temp.get(2));
            } else {
                medianList.add(temp.get(0));
            }
        }

        return definedSelection(medianList, medianList.size() / 2);
    }
}
