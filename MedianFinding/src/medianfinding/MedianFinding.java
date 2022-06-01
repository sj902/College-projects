package medianfinding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author shubhamjain
 */
public class MedianFinding {

    public static void main(String[] args) {
        long randomTime = 0;
        int randomComparisons = 0;
        long definedTime = 0;
        int definedComparisons = 0;
        int randomMedian = 0;
        int definedMedian;

        ArrayList array;
        array = new ArrayList<Integer>();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                array.add(Integer.parseInt(line));
                line = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
            return;
        } catch (IOException ex) {
            System.out.println("IOexception");
            return;
        }

        int rank = Integer.parseInt(args[1]);

        if (rank > (array.size() + 1) || rank <= 0) {
            System.out.println("Rank Invalid");
            return;
        }

        for (int i = 0; i < 10; ++i) {
            medianfinding.RandomSelection rs = new medianfinding.RandomSelection(array, rank);
            randomComparisons += rs.comparisons;
            randomTime += rs.time;
            randomMedian = rs.element;
        }

        DefinedSelection ds = new DefinedSelection(array, rank);
        definedComparisons = ds.comparisons;
        definedTime = ds.time;
        definedMedian = ds.element;

        System.out.println("Random Selection Median:" + randomMedian);
        System.out.println("Random Selection Time:" + (randomTime / 10.0)+"µs");
        System.out.println("Random Selection Comparisons:" + (randomComparisons / 10.0));
        System.out.println("Defined Selection Median:" + definedMedian);
        System.out.println("Defined Selection Time:" + definedTime+"µs");
        System.out.println("Defined Selection Comparisons:" + definedComparisons);


    }

}
