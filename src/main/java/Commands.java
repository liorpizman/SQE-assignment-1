import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Commands {

    private LinkedList<String> names;
    private HashMap<String, Integer> stringsMap;

    public Commands(LinkedList<String> names) {
        this.names = names;
    }

    /**
     * Command 1 - counts the occurrences of a particular string in a list strings
     * (without repetitions in the same string and with a reference to capital letters
     *
     * @param substring string which was chosen for the scan
     */
    public void CountSpecificString(String substring) {
        int countInstances = 0;
        for (String name : names) {
            countInstances = name.contains(substring) ? countInstances + 1 : countInstances;
        }
        System.out.println(countInstances);
    }

    /**
     * Command 2 - counts all strings in a chosen size of n the names' list
     *
     * @param length - chosen size
     */
    public void CountAllStrings(int length) {
        this.stringsMap = new HashMap<String, Integer>();
        for (String name : names) {
            countSubstringsInstances(name, length);
        }
        printByStructure();
    }

    /**
     * counts all substring in the chosen size in a current string
     *
     * @param name current string in names' list
     * @param size chosen size
     */
    public void countSubstringsInstances(String name, int size) {
        for (int i = 0; (i + size) <= name.length(); i++) {
            String substring = name.substring(i, i + size);
            Integer count = this.stringsMap.get(substring);
            if (count == null) {
                this.stringsMap.put(substring, 1);
            } else {
                this.stringsMap.put(substring, count + 1);
            }
        }
    }

    /**
     * prints all substrings in names' list and the number of times they appeared
     */
    public void printByStructure() {
        for (String substring : this.stringsMap.keySet()) {
            System.out.println(substring + ':' + this.stringsMap.get(substring));
        }
    }

    /**
     * Command 3 -
     *
     * @param length
     */
    public void CountMaxString(int length) {
        System.out.println();
    }

    /**
     * Command 4 -
     *
     * @param str
     */
    public void AllIncludesString(String str) {
        System.out.println();
    }

    /**
     * Command 5 -
     */
    public void GenerateName() {
        System.out.println();
    }
}
