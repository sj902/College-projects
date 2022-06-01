import java.util.HashMap;

public class Knapsack {

    static HashMap<String, Integer> hm = new HashMap<>();

    static int knapsack(int[] wt, int[] val, int maxWt, int idx){
        int size = wt.length;

        String key = maxWt+"-"+idx;

        if(hm.containsKey(key)){
            return hm.get(key);
        }
        if(idx >= size){
            return 0;
        }

        int a = knapsack(wt,val, maxWt, idx+1);
        if(wt[idx] <= maxWt){
            int b = knapsack(wt,val, maxWt-wt[idx], idx+1) + val[idx];
            int max =  Math.max(a, b);
            hm.put(key, max);
            return max;
        } else {
            hm.put(key, a);
            return a;
        }
    }

    public static void main(String[] args) {
        System.out.println("kp");
        int[] val = new int[] {10,15,40};
        int[] wt = new int[] {1,2,3};
        int max = 6;
        System.out.println(knapsack(wt,val,max,0));
    }
}
