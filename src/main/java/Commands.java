import java.util.LinkedList;

public class Commands {

    private LinkedList<String> names;

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
     * Command 2 -
     *
     * @param length
     */
    public void CountAllStrings(int length) {
        System.out.println();
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
