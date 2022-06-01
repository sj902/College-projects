import org.omg.PortableInterceptor.INACTIVE;

import java.awt.image.AreaAveragingScaleFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


class ListNode {
    public int val;
    public ListNode next;
    ListNode(int x) { val = x; next = null; }
}


public class Main {

    private static HashSet<Integer> numbers = new HashSet<Integer>();

    private static void subset(ArrayList<String> prefix, ArrayList<String> suffix, int n) {
        if (suffix.size() == n) {
            System.out.println("" + suffix);
        } else {
            String a = prefix.get(0);
            String abar = "not " + prefix.get(0);
            ArrayList<String> s = new ArrayList<String>(suffix);
            s.add(a);
            ArrayList<String> sbar = new ArrayList<String>(suffix);
            sbar.add(abar);
            subset(new ArrayList<String>(prefix.subList(1, prefix.size())), s, n);
            subset(new ArrayList<String>(prefix.subList(1, prefix.size())), sbar, n);
        }
    }

    private static void gen(ArrayList<String> prefix, ArrayList<String> suffix, int n, int max) {
        if (suffix.size() == n) {
            String num = "";
            for (String i : suffix) {
                num = num + i;
            }
            Integer number = Integer.parseInt(num);
            if (number < max)
                numbers.add(number);
        } else {
            for (int i = 0; i < prefix.size(); ++i) {
                String a = prefix.get(i);
                ArrayList<String> s = new ArrayList<String>(suffix);
                if (!(s.size() == 0 && a.equals("0"))) {
                    s.add(a);
                    if (s.size() == n) {
                        String num = "";
                        for (String ii : s) {
                            num = num + ii;
                        }
                        Integer number = Integer.parseInt(num);
                        if (number > max)
                            break;
                        else gen(prefix, s, n, max);
                    } else gen(prefix, s, n, max);
                }
            }
        }
    }

    private static double count(ArrayList<Integer> A, int B, int C) {
        if (A.size() == 0) {
            return 0;
        }
        String Btest = "1";
        for (int i = 1; i < B; ++i) {
            Btest = Btest + "0";
        }
        if (Integer.parseInt(Btest) > C) {
            return 0;
        }


        if (B == 1) {
            int i = 0;
            for (int elem : A) {
                if (elem < C)
                    ++i;
            }
            return i;
        }


        Btest = "";
        for (int i = 0; i < B; ++i) {
            Btest = Btest + "9";
        }
        if (Integer.parseInt(Btest) < C) {
            if (A.get(0) == 0)
                return Math.pow(A.size(), B - 1) * (A.size() - 1);
            else return Math.pow(A.size(), B);
        }


        double total = 0;
        for (int i = 0; i < B; ++i) {
            double prod = 0;
            String cs = C + "";
            char ch = cs.charAt(i);
            int cnum = Integer.parseInt(ch + "");
            int len = 0;

            for (int elem : A) {
                if (elem < cnum)
                    ++len;
            }

            if (i == 0) {
                if (A.get(0) == 0) {
                    prod = (len - 1) * Math.pow(A.size(), B - i - 1);
                } else {
                    prod = len * Math.pow(A.size(), B - i - 1);
                }
            } else {
                prod = len * Math.pow(A.size(), B - i - 1);
            }

            if (prod == 0)
                return total;
            total = total + prod;

        }

        return (int) total;
    }

    private static void arr2d(ArrayList<ArrayList<Integer>> A) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(0);
        temp.add(5);
        A.add(temp);

        A.get(1).set(0, 45);

        System.out.println("" + A);
    }

    private static int titleToNumber(String A) {
        int total = 0;
        for (int i = 0; i < A.length(); ++i) {
            char c = A.charAt(i);
            int ascii = (int) c;

            System.out.println(ascii);
        }
        return total;
    }

    private static ArrayList<Integer> searchRange(final ArrayList<Integer> a, int b) {
        int small = 0;
        int large = a.size() - 1;
        int mid = 0;
        ArrayList<Integer> res = new ArrayList<Integer>();
        while (small <= large) {
            mid = (small + large) / 2;
            if (a.get(mid) == b) {
                System.out.println("" + mid);

                int b1 = b - 1;
                int flag = 0;
                int small1 = 0;
                int large1 = mid - 1;
                int mid1;
                while (small1 <= large1) {
                    mid1 = (small1 + large1) / 2;
                    if (a.get(mid1) == b1) {
                        while (a.get(mid1) != b) {
                            ++mid1;
                        }
                        res.add(mid1);
                        flag = 1;
                        break;
                    } else if (a.get(mid1) < b1) {
                        small1 = mid1 + 1;
                    } else {
                        large1 = mid1 - 1;
                    }
                }
                if (flag == 0) {
                    res.add(small1);
                }


                b1 = b + 1;
                flag = 0;
                small1 = mid + 1;
                large1 = a.size() - 1;
                while (small1 <= large1) {
                    mid1 = (small1 + large1) / 2;
                    if (a.get(mid1) == b1) {
                        while (a.get(mid1) != b) {
                            --mid1;
                        }
                        res.add(mid1);
                        flag = 1;
                        break;
                    } else if (a.get(mid1) < b1) {
                        small1 = mid1 + 1;
                    } else {
                        large1 = mid1 - 1;
                    }
                }
                if (flag == 0) {
                    res.add(large1);
                }
                return res;
            } else if (a.get(mid) < b) {
                small = mid + 1;
            } else {
                large = mid - 1;
            }
        }
        System.out.println("" + small);
        System.out.println("" + large);
        res.add(-1);
        res.add(-1);
        return res;
    }

    private static int pow(int x, int n, int d) {
        if (x < 0) x = x + d;

        if (n == 0) return 1;

        if (x == 0 || x == 1) return x;

        long res = 1;
        long sq = 1;

        x = x % d;

        if (n % 2 == 0) {
            long l = pow(x, n / 2, d);
            res = ((l % d) * (l % d)) % d;
            if (res < 0) return (int) res + d;
            return (int) res;
        } else {
            long l = pow(x, n - 1, d);
            res = ((x % d) * (l % d)) % d;
            if (res < 0) return (int) res + d;
            return (int) res;
        }
    }

    private static String countAndSay(int A) {
        String s = "1";

        for (int j = 0; j < A - 1; ++j) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); ) {
                int count = 0;
                int inte = Integer.parseInt(s.substring(i, i + 1));
                while (i < s.length() && Integer.parseInt(s.substring(i, i + 1)) == inte) {
                    ++count;
                    ++i;
                }
                sb.append(count + "");
                sb.append(inte + "");
            }
            s = sb.toString();
        }
        return s;
    }

    private static String reverseWords(String a) {
        StringBuilder sb = new StringBuilder();
        sb.append(' ');
        for (int i = 0; i < a.length(); ++i) {
            sb.append(a.charAt(a.length() - i - 1));
        }

        StringBuilder res = new StringBuilder();
        int k = 0;
        while (k < a.length()) {
            ++k;
            int start = k;
            while (k < sb.length() && sb.charAt(k) != ' ') {
                System.out.println("" + sb.charAt(k));
                ++k;
            }
            int end = k;
            StringBuilder ss = new StringBuilder();
            for (int i = end - 1; i >= start; --i) {
                ss.append(sb.charAt(i));
            }
            res.append(ss);
            res.append(' ');
        }
        return res.toString().trim();
    }

    private static String longestPalindrome(String A) {
        String max = "";

        for (int i = 0; i < A.length(); ++i) {
            String temp = "" + A.charAt(i);
            int start = i - 1;
            int end = i + 1;
            while (start >= 0 && end < A.length() && A.charAt(start) == A.charAt(end)) {
                temp = A.charAt(start) + temp + A.charAt(end);
                if (temp.length() > max.length()) {
                    max = temp;
                }
                ++end;
                --start;
            }


            if (i + 1 < A.length() && A.charAt(i) == A.charAt(i + 1)) {
                temp = "" + A.charAt(i) + "" + A.charAt(i + 1);
                if (temp.length() > max.length()) {
                    max = temp;
                }
                start = i - 1;
                end = i + 2;
                while (start >= 0 && end < A.length() && A.charAt(start) == A.charAt(end)) {
                    temp = A.charAt(start) + temp + A.charAt(end);
                    if (temp.length() > max.length()) {
                        max = temp;
                    }
                    ++end;
                    --start;
                }

            }


            if (i - 1 >= 0 && A.charAt(i - 1) == A.charAt(i)) {
                temp = "" + A.charAt(i - 1) + "" + A.charAt(i);
                if (temp.length() > max.length()) {
                    max = temp;
                }
                start = i - 2;
                end = i + 1;
                while (start >= 0 && end < A.length() && A.charAt(start) == A.charAt(end)) {
                    temp = A.charAt(start) + temp + A.charAt(end);
                    if (temp.length() > max.length()) {
                        max = temp;
                    }
                    ++end;
                    --start;
                }

            }

        }

        return max;
    }

    private static boolean isPalindrome(String a) {
        for (int i = 0; i < a.length() / 2; ++i) {
            if (a.charAt(i) != a.charAt(a.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    private static int romanToInt(String A) {
        int res = 0;
        for (int i = 0; i < A.length();) {
            char ch = A.charAt(i);
            switch (ch) {
                case 'M':
                    res = res + 1000;
                    ++i;
                    break;

                case 'D':
                    res = res + 500;
                    ++i;
                    break;

                case 'L':
                    res = res + 50;
                    ++i;
                    break;

                case 'C':
                    if (i + 1 < A.length() && A.charAt(i + 1) == 'D') {
                        res = res + 400;
                        i = i + 2;
                    } else if (i + 1 < A.length() && A.charAt(i + 1) == 'M') {
                        res = res + 900;
                        i = i + 2;
                    } else {
                        res = res + 100;
                        ++i;
                    }
                    break;


                case 'X':
                    if (i + 1 < A.length() && A.charAt(i + 1) == 'L') {
                        res = res + 40;
                        i = i + 2;
                    } else if (i + 1 < A.length() && A.charAt(i + 1) == 'C') {
                        res = res + 90;
                        i = i + 2;
                    } else {
                        res = res + 10;
                        ++i;
                    }
                    break;

                case 'I':
                    if (i + 1 < A.length() && A.charAt(i + 1) == 'X') {
                        res = res + 9;
                        i = i + 2;
                    } else if (i + 1 < A.length() && A.charAt(i + 1) == 'V') {
                        res = res + 4;
                        i = i + 2;
                    } else {
                        res = res + 1;
                        ++i;
                    }
                    break;

                case 'V':
                    res = res + 5;
                    ++i;
                    break;
            }
        }
        return res;
    }

    private static ArrayList<String> prettyJSON(String A) {
        ArrayList<String> res = new ArrayList<String>();

        int tab = 0;
        int newline = 0;

        for(int i = 0; i<A.length(); ++i){
            char ch = A.charAt(i);
            StringBuilder s = new StringBuilder();
            switch(ch)
            {
                case '{':
                case '[':
                    for(int j = 0; j< tab; ++j){
                        s=s.append("\t");
                    }
                    s=s.append(ch);
                    res.add(s.toString());
                    tab++;
                    break;
                case '}':
                case ']':
                    --tab;
                    for(int j = 0; j< tab; ++j){
                        s=s.append("\t");
                    }
                    s=s.append(ch);
                    res.add(s.toString());
                    break;
                default:
                    System.out.println("here : "+ch);
                    String str = res.get(res.size()-1);
                    if((str.charAt(str.length()-1) == '}' || str.charAt(str.length()-1) == ']' )&&(ch == ',')){
                        str = str+ch;
                        res.set(res.size()-1,str);
                    }
                    else if(str.charAt(str.length()-1) == ',' ||
                            str.charAt(str.length()-1) == '{' || str.charAt(str.length()-1) == '}'||
                            str.charAt(str.length()-1) == '[' || str.charAt(str.length()-1) == ']') {
                        for(int j = 0; j< tab; ++j){
                            s=s.append("\t");
                        }
                        s.append(ch);
                        res.add(s.toString());
                    }
                    else{
                     str = str+ch;
                        res.set(res.size()-1,str);
                    }
                    break;
            }
        }
        return res;
    }

    private static int atoi(final String A) {
        int res = 0;
        int i = 0;
        int sign  = 1;
        if(A.charAt(0) == '-') {
            sign = -1;
            ++i;
        }
        if(A.charAt(0) == '+') {
            sign = 1;
            ++i;
        }
        for(; i<A.length(); ++i){
            char ch = A.charAt(i);
            switch (ch)
            {
                case '0':
                    if(Integer.MAX_VALUE/10<=res+0)
                    {
                        if(sign == -1){
                            return Integer.MIN_VALUE;
                        }
                        else return Integer.MAX_VALUE;
                    }
                    res = res*10+0;
                    break;
                case '1':
                    if(Integer.MAX_VALUE/10<=res+1) return Integer.MAX_VALUE;
                    res = res*10+1;
                    break;
                case '2':
                    if(Integer.MAX_VALUE/10<=res+2) return Integer.MAX_VALUE;
                    res = res*10+2;
                    break;
                case '3':
                    if(Integer.MAX_VALUE/10<=res+3) return Integer.MAX_VALUE;
                    res = res*10+3;
                    break;
                case '4':
                    if(Integer.MAX_VALUE/10<=res+4) return Integer.MAX_VALUE;
                    res = res*10+4;
                    break;
                case '5':
                    if(Integer.MAX_VALUE/10<=res+5) return Integer.MAX_VALUE;
                    res = res*10+5;
                    break;
                case '6':
                    if(Integer.MAX_VALUE/10<=res+6) return Integer.MAX_VALUE;
                    res = res*10+6;
                    break;
                case '7':
                    if(Integer.MAX_VALUE/10<=res+7) return Integer.MAX_VALUE;
                    res = res*10+7;
                    break;
                case '8':
                    if(Integer.MAX_VALUE/10<=res+8) return Integer.MAX_VALUE;
                    res = res*10+8;
                    break;
                case '9':
                    if(Integer.MAX_VALUE/10<=res+9) return Integer.MAX_VALUE;
                    res = res*10+9;
                    break;

                default:
                    return res*sign;
            }
        }
        return res*sign;
    }

    private static int findMinXor(ArrayList<Integer> A) {
        int res = Integer.MAX_VALUE;
        Collections.sort(A);
        for(int i = 0; i<A.size()-1; ++i){
            int j=i+1;
                if((A.get(i)^A.get(j))<res)
                {
                    res = A.get(i)^A.get(j);
                }
        }
        return res;
    }

    private static int singleNumber(final List<Integer> A) {
        int temp = 1;
        int res = 0;
        int INT_SIZE = 32;

        for(int i = 0; i<INT_SIZE; ++i){
            int sum = 0;
            temp = (1<<i);
            for(int j = 0; j< A.size(); ++j){
                if((A.get(j)&temp) > 0)
                    sum++;
            }
            sum = sum%3;
            //System.out.println(""+sum);
            res = res+(int)(Math.pow(2,i)*sum);
        }

        return res;
    }

    private static int divide(int N, int D) {

        long n = Math.abs(N);
        long d = Math.abs(D);
        long mask = 1;
        long res = 0;
        int q=0;
        while(n>d)
        {
            System.out.println("here");
            d = (d<<1);
            mask = (mask<<1);
        }

        System.out.println("mask:::"+mask);

        do {
            if(n>=d)
            {
                n = n-d;
                res = res+mask;
                System.out.println("res::"+res);
            }
            mask = (mask>>1);
            d = (d>>1);
        }while (mask!=0);

        if (n == Integer.MIN_VALUE && d == -1)
            return Integer.MAX_VALUE;

        if ((n > 0 && d > 0) || (d < 0 && n < 0))
            return (int)res;
        else
            return (int)-res;

    }

    private static int cntBits(ArrayList<Integer> A) {
        long res = 0;
        int in = 1;

        for(int j = 0; j< 32; ++j){
            int count = 0;
            for(int i = 0; i<A.size(); ++i){
                if((A.get(i)&in)>0)
                {
                    count++;
                }
            }
            res = res + (count*(A.size()-count)*2);
            in = (in<<1);
        }
        return (int)(res%1000000007);
    }

    private static int solve(ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Integer> C) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int res = Integer.MAX_VALUE;
        int a = 0 ,b = 0 ,c = 0;
        while (a<A.size() && b<B.size() && c<C.size())
        {
            max = Integer.max(A.get(a), Integer.max(B.get(b),C.get(c)));
            min = Integer.min(A.get(a), Integer.min(B.get(b),C.get(c)));
            if(Math.abs(max - min) < res )
            {
                res = Math.abs(max - min);
                if(res == 0)
                    break;
            }
            if(A.get(a) == min) a++;
            else if(B.get(b) == min){b++;}
            else c++;
        }

        return res;
    }

    private static boolean hasNoDuplicate(long res){
        String s = res+"";

        for(int i = 0; i< s.length(); ++i){
            for(int j = i+1; j< s.length(); ++j){
                if(s.charAt(i) == s.charAt(j))
                    return false;
            }
        }

        return true;
    }

    private static long q3(){
        long res = Long.parseLong("9876543210");
        while(res > 0)
        {
            if(res%11 == 0 && hasNoDuplicate(res))
                return res;
            else res --;
        }
        return res;
    }

    private static void printLinkedList(ListNode head){
        ListNode curr = head;
        while(curr != null)
        {
            System.out.print(""+curr.val+"->");
            curr = curr.next;
        }
        System.out.println("");
    }

    private static int lPalin(ListNode A) {

        printLinkedList(A);

        ListNode slow = A;
        ListNode head = A;
        ListNode headprev = A;

        while (slow != null && slow.next != null) {
            headprev = head;
            head = head.next;
            slow = slow.next.next;
        }


        if(slow!=null)
        {
            headprev = head;
            head = head.next;
        }

        ListNode curr = head;
        ListNode p = null;
        ListNode next;

        while (curr != null) {
            next = curr.next;
            curr.next = p;
            p = curr;
            curr = next;
        }
        head = p;

        headprev.next = head;

        printLinkedList(A);

        ListNode first = A;

        while (head != null) {
            if (first.val != head.val) return 0;
            first = first.next;
            head = head.next;
        }
        return 1;
    }

    private static  int largestRectangleArea(ArrayList<Integer> A) {
        int res = 0;
        Stack<Integer> position = new Stack<>();
        Stack<Integer> height = new Stack<>();
        int counter = 0;
        counter++;
        height.push(A.get(0));
        position.push(0);
        while(counter<A.size())
        {
            int top = height.peek();
            if(top <= A.get(counter))
            {
                height.push(A.get(counter));
                position.push(counter);
            }
            else
            {
                int b = counter;
                while (height.size()>0 && A.get(counter) < height.peek() )
                {
                    int h = height.pop();
                    b = position.pop();
                    int c = counter-b;
                    if(h*c > res)
                    {
                        res = h*c;
                    }
                }
                height.push(A.get(counter));
                position.push(b);
            }
            counter++;
        }
        while (!height.empty())
        {
            int h = height.pop();
            int b = counter-position.pop();
            if(h*b > res)
            {
                res = h*b;
            }
        }
        return res;
    }

    private static ListNode revll(ListNode head){
        ListNode p,c,n;
        p = null;
        c = head;
        n = head;
        while(c != null){
            n = c.next;
            c.next = p;
            p = c;
            c = n;
        }
        return p;
    }

    private static ListNode subt(ListNode head){
        ListNode mid = head;
        ListNode fast = head;
        ListNode prev = head;
        while(fast!=null && fast.next!=null)
        {
            prev = mid;
            mid = mid.next;
            fast = fast.next.next;
        }

        if(fast != null)
        {
            prev = mid;
            mid = mid.next;
        }

        ListNode p,c,n;
        p = null;
        c = mid;
        n = mid;
        while(c != null){
            n = c.next;
            c.next = p;
            p = c;
            c = n;
        }


        prev.next = p;

        //if fast is null - even
        //else odd

        printLinkedList(head);

        c = head;
        mid = p;

        while(mid!=null)
        {
            c.val = mid.val-c.val;
            mid = mid.next;
            c = c.next;
        }


        mid = p;

        p = null;
        c = mid;
        n = mid;
        while(c != null){
            n = c.next;
            c.next = p;
            p = c;
            c = n;
        }


        prev.next = p;

        //printLinkedList(head);


        return head;
    }

    private static void subsets_temp(ArrayList<Integer> prefix, ArrayList<Integer> suffix, ArrayList<ArrayList<Integer>> res) {

        res.add(prefix);

        if(suffix.size() == 0)
            return;


        for(int i = 0; i< suffix.size(); ++i){
            ArrayList<Integer> temp = new ArrayList<Integer>(prefix);
            temp.add(suffix.get(i));
            subsets_temp(temp, new ArrayList<Integer>(suffix.subList(i+1,suffix.size())), res);
        }
    }

    private static ArrayList<ArrayList<Integer>> subsets(ArrayList<Integer> A) {
        Collections.sort(A);
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        subsets_temp(new ArrayList<Integer>(), A, res);
        return res;
    }

    private static void combinations_temp(ArrayList<Integer> prefix, ArrayList<Integer> suffix, ArrayList<ArrayList<Integer>> res, int k) {

        if(prefix.size() == k) {
            res.add(prefix);
            return;
        }

        if(suffix.size() == 0)
            return;


        for(int i = 0; i< suffix.size(); ++i){
            ArrayList<Integer> temp = new ArrayList<Integer>(prefix);
            temp.add(suffix.get(i));
            combinations_temp(temp, new ArrayList<Integer>(suffix.subList(i+1,suffix.size())), res, k);
        }
    }

    private static ArrayList<ArrayList<Integer>> combine(int n, int k) {

        ArrayList<Integer> A = new ArrayList<Integer>();
        for(int i = 0; i< n; ++i){
            A.add(i+1);
        }

        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        combinations_temp(new ArrayList<Integer>(), A, res, k);
        return res;
    }

    private static void combinationsum_temp(ArrayList<Integer> prefix, ArrayList<Integer> suffix, ArrayList<ArrayList<Integer>> res, int k) {
        int sum = 0;
        for(int i = 0; i<prefix.size(); ++i){
           sum = sum+ prefix.get(i);
        }

        if(sum>k) return;

        if(suffix.size() == 0)
            return;


        for(int i = 0; i< suffix.size(); ++i) {
            ArrayList<Integer> temp = new ArrayList<Integer>(prefix);
            temp.add(suffix.get(i));
            if (sum + suffix.get(i) == k) res.add(temp);
            else if(sum + suffix.get(i) < k){
                combinationsum_temp(temp, new ArrayList<Integer>(suffix.subList(i+1, suffix.size())), res, k);
            }
        }
    }

    private static ArrayList<ArrayList<Integer>> combinationSum(ArrayList<Integer> A, int B) {
        A = new ArrayList<>(new HashSet<Integer>(A));
        Collections.sort(A);
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        combinationsum_temp(new ArrayList<Integer>(), A, res, B);
        return res;
    }

    private static void combinationsum_temp2(ArrayList<Integer> prefix, ArrayList<Integer> suffix, ArrayList<ArrayList<Integer>> res, int k) {
        int sum = 0;
        for(int i = 0; i<prefix.size(); ++i){
            sum = sum+ prefix.get(i);
        }

        if(sum>k) return;

        if(suffix.size() == 0)
            return;


        for(int i = 0; i< suffix.size(); ++i) {
            ArrayList<Integer> temp = new ArrayList<Integer>(prefix);
            temp.add(suffix.get(i));
            if (sum + suffix.get(i) == k) {
                if(i>0 && suffix.get(i).equals(suffix.get(i-1))){
                }
                else {
                    res.add(temp);
                }

            }
            else if(sum + suffix.get(i) < k){
                if(i>0 && suffix.get(i).equals(suffix.get(i-1))){
                }
                else{
                    combinationsum_temp2(temp, new ArrayList<Integer>(suffix.subList(i+1, suffix.size())), res, k);
                }
            }
        }
    }

    private static ArrayList<ArrayList<Integer>> combinationSum2(ArrayList<Integer> A, int B) {
        Collections.sort(A);
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        combinationsum_temp2(new ArrayList<Integer>(), A, res, B);
        return res;
    }

    private static void permute_temp(ArrayList<Integer> prefix, ArrayList<Integer> suffix,ArrayList<ArrayList<Integer>> res) {

        if(suffix.size() == 0) {res.add(prefix);return;}

        for(int i = 0; i< suffix.size(); ++i){
            ArrayList<Integer> p = new ArrayList<Integer>(prefix);
            p.add(suffix.get(i));
            ArrayList<Integer> b = new ArrayList<Integer>(suffix.subList(0, i));
            b.addAll(new ArrayList<Integer>(suffix.subList(i+1, suffix.size())));

            permute_temp(p, b, res);
        }

    }

    private static ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> A) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        permute_temp(new ArrayList<Integer>(), A, res);
        return res;
    }

    private static void letterCombinations_temp(StringBuilder prefix, StringBuilder suffix, List<String> res) {
        if(suffix.length()==0) {res.add(prefix.toString());return;}
        StringBuilder s, p;
        String s2 = "abc";
        String s3 = "def";
        String s4 = "ghi";
        String s5 = "jkl";
        String s6 = "mno";
        String s7 = "pqrs";
        String s8 = "tuv";
        String s9 = "wxyz";
        char ch = suffix.charAt(0);
        switch(ch){
            case '0':p = new StringBuilder(prefix);
                p.append('0');
                s = new StringBuilder(suffix);
                s.deleteCharAt(0);
                letterCombinations_temp(p, s, res);
                break;
            case '1':p = new StringBuilder(prefix);
                p.append('1');
                s = new StringBuilder(suffix);
                s.deleteCharAt(0);
                letterCombinations_temp(p, s, res);
                break;
            case '2':
                for(int i = 0; i< s2.length(); ++i){
                    p = new StringBuilder(prefix);
                    p.append(s2.charAt(i));
                    s = new StringBuilder(suffix);
                    s.deleteCharAt(0);
                    letterCombinations_temp(p, s, res);
                }
                break;
            case '3':
                for(int i = 0; i< s3.length(); ++i){
                    p = new StringBuilder(prefix);
                    p.append(s3.charAt(i));
                    s = new StringBuilder(suffix);
                    s.deleteCharAt(0);
                    letterCombinations_temp(p, s, res);
                }
                break;
            case '4':
                for(int i = 0; i< s4.length(); ++i){
                    p = new StringBuilder(prefix);
                    p.append(s4.charAt(i));
                    s = new StringBuilder(suffix);
                    s.deleteCharAt(0);
                    letterCombinations_temp(p, s, res);
                }
                break;
            case '5':
                for(int i = 0; i< s5.length(); ++i){
                    p = new StringBuilder(prefix);
                    p.append(s5.charAt(i));
                    s = new StringBuilder(suffix);
                    s.deleteCharAt(0);
                    letterCombinations_temp(p, s, res);
                }
                break;
            case '6':
                for(int i = 0; i< s6.length(); ++i){
                    p = new StringBuilder(prefix);
                    p.append(s6.charAt(i));
                    s = new StringBuilder(suffix);
                    s.deleteCharAt(0);
                    letterCombinations_temp(p, s, res);
                }
                break;
            case '7':
                for(int i = 0; i< s7.length(); ++i){
                    p = new StringBuilder(prefix);
                    p.append(s7.charAt(i));
                    s = new StringBuilder(suffix);
                    s.deleteCharAt(0);
                    letterCombinations_temp(p, s, res);
                }
                break;
            case '8':
                for(int i = 0; i< s8.length(); ++i){
                    p = new StringBuilder(prefix);
                    p.append(s8.charAt(i));
                    s = new StringBuilder(suffix);
                    s.deleteCharAt(0);
                    letterCombinations_temp(p, s, res);
                }
                break;
            case '9':
                for(int i = 0; i< s9.length(); ++i){
                    p = new StringBuilder(prefix);
                    p.append(s9.charAt(i));
                    s = new StringBuilder(suffix);
                    s.deleteCharAt(0);
                    letterCombinations_temp(p, s, res);
                }
                break;

        }
    }

    private static ArrayList<String> letterCombinations(String A) {
        ArrayList<String> res = new ArrayList<String>();
        letterCombinations_temp(new StringBuilder(""), new StringBuilder(A),  res);
        return res;
    }

    private static void genSub(String A, int start, ArrayList<String> part, ArrayList<ArrayList<String>> res){
        if(start == A.length())
        {
            ArrayList<String> temp = new ArrayList<String>(part);
            res.add(temp);
            return;
        }

        for(int i = start+1; i<=A.length(); ++i){
            String s = A.substring(start, i);
            if(isPalindrome(s))
            {
                part.add(s);
                genSub(A,i,part,res);
                part.remove(part.size() - 1);
            }
        }
    }

    private static ArrayList<ArrayList<String>> partition(String a) {
        ArrayList<ArrayList<String>>res = new ArrayList<ArrayList<String>>();
        genSub(a, 0, new ArrayList<String> (), res);
        return res;
    }

    private static void generateParenthesis_temp(String s, int left, int right, ArrayList<String> res) {
            if(left>right){
                return;
            }
            if(right == 0 && left == right)
            {
                res.add(s);
                return;
            }
            if(left>0)
            {
                generateParenthesis_temp(s+"(", left-1, right, res);
            }
            if(right>0)
            {
                generateParenthesis_temp(s+")", left, right-1, res);
            }
        }

    private static ArrayList<String> generateParenthesis(int A) {
        ArrayList<String> res = new ArrayList<String>();
        generateParenthesis_temp("", A, A, res);
        return res;
    }

    private static void perm_temp(String prefix, String suffix, ArrayList<String> res) {

        int l = prefix.length();
        int r = suffix.length();

        if  (suffix.length()==0)
        {
            res.add(prefix);
            return;
        }


        for(int i = 0; i< suffix.length(); ++i){

            String temp = prefix+suffix.charAt(i);
            String temp2 = suffix.substring(0,i)+suffix.substring(i+1);
            perm_temp(temp, temp2, res);
        }

    }

    private static String perm(int A, int B) {
        ArrayList<String> res = new ArrayList<String>();
        StringBuilder s = new StringBuilder();

        for(int i = 1; i<= A; ++i){
            s = s.append(i);
        }
        perm_temp("",s.toString(),  res);
        return res.get(B);
    }

    private static long fact(int n)
    {
        if(n == 0 || n==1) return 1;
        long mult = 1;
        for(int i = 2; i<= n; ++i){
            mult *= i;
        }
        return mult;
    }

    private static String getPermutation(int A, int B) {
        B--;
        long fact = fact(A);
        String res="";
        int pos;

        StringBuilder s = new StringBuilder();

        for(int i = 1; i<= A; ++i){
            s = s.append(i);
        }

        System.out.println(""+s);

        for(int i = 0; i< A; ++i){
            fact = fact/(A-i);
            pos = B/(int)fact;
            B = B%(int)fact;
            res = res + s.charAt(pos);
            s.deleteCharAt(pos);
        }

        return res;
    }


    private static int colorful(int A) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        HashSet<Integer> m = new HashSet<>();
        int digits = 0;
        while(A>0)
        {
            numbers.add(A%10);
            A /= 10;
            ++digits;
        }
        ArrayList<ArrayList<String>> f = new ArrayList<ArrayList<String>>();
        for(int len = 0; len< digits; ++len){
            ArrayList<Integer> temp = new ArrayList<Integer>();
            for(int i = 0; i< numbers.size()-len; ++i){
                int mul = 1;
                int k = 0;
                while(k<=len)
                {
                    mul = mul*numbers.get(i+k);
                    ++k;
                }
                if(m.contains(mul)) return 0;
                else{m.add(mul);}
            }
        }
        return 1;
    }


    private static int nchoc(int A, ArrayList<Integer> B) {
        //TreeMap<Integer, Integer> heap = new TreeMap<Integer, Integer>();
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(B.size(), Collections.reverseOrder());


        for(int i = 0; i< B.size(); ++i){
            heap.add(B.get(i));
        }

        int sum = 0;

        for(int i = 0; i< A; ++i){

            int del = heap.remove();

            sum = (sum + del%(1000000000+7))%(1000000000+7);

            heap.add(del/2);

        }

        return (int)sum%(1000000000+7);
    }

    private static Date getDate(String date){



        Date date1=new Date();
        try{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            date1 = format.parse(date);
            System.out.println("date1"+String.valueOf(date1));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return date1;
    }


    private static int merge(ArrayList<Integer> A, int l, int h){
        if(l>=h) return 0;
        int m = (l+h)/2;
        int a = merge(A, l, m);
        int b = merge(A,m+1, h);
        int c = mergeSort(A,l,m,h);
        return a+b+c;
    }

    private static int mergeSort(ArrayList<Integer> A,  int l, int m, int h){

        int swap = 0;
        int p = l;
        int q = m+1;

        while(p<=m && q<=h)
        {
            if(A.get(p)<=A.get(q))
            {
                ++p;
            }
            else
            {
                int a = A.get(q);
                A.set(q,A.get(p));
                A.set(p,a);
                ++q;
                swap = swap + m - p + 1;
            }
        }
        while(p<=m)
        {
            ++p;
        }
        while(q<h){
            ++q;
        }
        return swap;
    }

    private static int countInversions(ArrayList<Integer> A) {
        return merge(A,0,A.size()-1);
    }


    public static void partition(int n) {
        partition(n, n, "");
    }
    public static void partition(int n, int max, String prefix) {
        if (n == 0) {
            System.out.println(prefix);
            return;
        }

        for (int i = Math.min(max, n); i >= 1; i--) {
            partition(n-i, i, prefix + " " + i);
        }
    }


    public static void main(String[] args) {

        ArrayList<Integer> a = new ArrayList<>();

        a.add(0);
        a.add(1);
        a.add(2);
        a.add(4);
        a.add(5);


        for(int i = 0; i< a.size(); ++i){
            System.out.print("  "+a.get(i));
        }

        System.out.println("");
        System.out.println("#########");


        a.add(3,10);


        for(int i = 0; i< a.size(); ++i){
            System.out.print("  "+a.get(i));
        }

        System.out.println("");
        System.out.println("#########");

        a.remove(3);

        for(int i = 0; i< a.size(); ++i){
            System.out.print("  "+a.get(i));
        }

        System.out.println("");
        System.out.println("#########");

    }
}
