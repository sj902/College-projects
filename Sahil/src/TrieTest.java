import java.util.HashMap;

class TrieNode {
   public char ch;public HashMap<Character, TrieNode> children;public boolean end;
   TrieNode(char ch){this.ch = ch; this.children = new HashMap<Character, TrieNode>();}
}
class Trie {
    public TrieNode root;
    Trie(){this.root = new TrieNode((char)0);}
    public void insert(String s){
        TrieNode curr = root;
        int n = s.length();
        for(int i = 0; i< n; ++i){
            char ch = s.charAt(i);
            if(root.children.containsKey(ch)) curr = root.children.get(ch);
            else{
                TrieNode t = new TrieNode(ch);
                root.children.put(ch, t);
                curr = t;
            }
        }
        curr.end = true;
    }
    public boolean getMatchingPrefix(String s){
        int n = s.length();
        TrieNode curr = root;
        for(int i = 0; i< n; ++i){
            char ch = s.charAt(i);
            if(root.children.containsKey(ch)) curr = root.children.get(ch);
            else return false;
        }
        return curr.end;
    }
}

// Testing class
public class TrieTest {
    public static void main(String[] args) {
        Trie dict = new Trie();
        dict.insert("are");
        dict.insert("area");
        dict.insert("base");
        dict.insert("cat");
        dict.insert("cater");
        dict.insert("basement");

        String input = "caterer";
        System.out.print(input + ": ");
        System.out.println(dict.getMatchingPrefix(input));

        input = "basement";
        System.out.print(input + ": ");
        System.out.println(dict.getMatchingPrefix(input));

        input = "are";
        System.out.print(input + ": ");
        System.out.println(dict.getMatchingPrefix(input));

        input = "area";
        System.out.print(input + ": ");
        System.out.println(dict.getMatchingPrefix(input));

        input = "basemexz";
        System.out.print(input + ": ");
        System.out.println(dict.getMatchingPrefix(input));

        input = "xyz";
        System.out.print(input + ": ");
        System.out.println(dict.getMatchingPrefix(input));
    }
}
