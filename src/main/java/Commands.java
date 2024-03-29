import java.util.*;

public class Commands {

    private HashSet<String> names;
    private HashMap<String, Integer> stringsMap;
    private HashSet<String> maxEntries;
    private HashMap.Entry<String, Integer> maxEntry;
    private HashMap<String, Integer> alphabetInstances;
    private String previousMaxCharacter;

    private static final int WORD_LENGTH = 5;
    private static final int N_GRAM_SIZE = 2;


    public Commands(HashSet<String> names) {
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
        for (String name : this.names) {
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
        updateStringsMap(length, true);
        printByStructure();
    }


    /**
     * updates the map of each substring (in a chosen size) and the number of
     * instances that they appeared
     *
     * @param length
     * @param capitalLettersCase two cases option
     */
    private void updateStringsMap(int length, boolean capitalLettersCase) {
        this.stringsMap = new HashMap<String, Integer>();
        for (String name : this.names) {
            countSubstringsInstances(name, length, capitalLettersCase);
        }
    }

    /**
     * counts all substring in the chosen size in a current string
     *
     * @param name               current string in names' list
     * @param size               chosen size
     * @param capitalLettersCase two cases option
     */
    private void countSubstringsInstances(String name, int size, boolean capitalLettersCase) {
        for (int i = 0; (i + size) <= name.length(); i++) {
            String sourceString = name.substring(i, i + size);
            String substring = capitalLettersCase ? sourceString : sourceString.toLowerCase();
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
    private void printByStructure() {
        for (String substring : this.stringsMap.keySet()) {
            System.out.println(substring + ':' + this.stringsMap.get(substring));
        }
    }

    /**
     * Command 3 - returns the n-size string that appears most frequently in all names
     * without reference to capital letters
     *
     * @param length chosen size for substrings
     */
    public void CountMaxString(int length) {
        updateStringsMap(length, false);
        this.maxEntries = new HashSet<String>();
        updateMaxInstances();
        printMaxSubstrings();
    }

    /**
     * updates max instances in names' list
     */
    private void updateMaxInstances() {
        this.maxEntry = null;
        for (HashMap.Entry<String, Integer> entry : this.stringsMap.entrySet()) {
            if (this.maxEntry == null || entry.getValue().compareTo(this.maxEntry.getValue()) > 0) {
                this.maxEntries = new HashSet<String>();
                this.maxEntry = entry;
            } else if (entry.getValue().compareTo(this.maxEntry.getValue()) == 0) {
                this.maxEntries.add(entry.getKey());
            }
        }
    }

    /**
     * prints all substrings with highest number of instances
     */
    private void printMaxSubstrings() {
        /* prints substring with highest number of instances */
        System.out.println(this.maxEntry.getKey());
        /* prints more substrings with same number of instances as the highest value */
        for (String substring : this.maxEntries) {
            System.out.println(substring);
        }
    }

    /**
     * Command 4 -
     * prints all names which are included in the input string without reference to capital letters
     *
     * @param string input string to check
     */
    public void AllIncludesString(String string) {
        String lowerInput = string.toLowerCase();
        for (String name : this.names) {
            String lowerCaseName = name.toLowerCase();
            if (lowerInput.contains(lowerCaseName)) {
                System.out.println(lowerCaseName);
            }
        }
    }

    /**
     * Command 5 -
     * generates a name under task's conditions
     */
    public void GenerateName() {
        String generatedWord = "";
        this.previousMaxCharacter = "";
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (i == 0) {
                this.previousMaxCharacter = iterateByFirstLetter();
            } else {
                this.previousMaxCharacter = getNextLetter();
            }
            generatedWord += this.previousMaxCharacter;
        }
        System.out.println(generatedWord);
    }

    /**
     * finds the next character with the max probability as described
     * @return next letter
     */
    private String getNextLetter() {
        updateStringsMap(N_GRAM_SIZE, true);
        String key = "";
        int keyInstances = 0;
        for (String name : this.stringsMap.keySet()) {
            if (String.valueOf(name.charAt(0)).equals(this.previousMaxCharacter)) {
                int currentValue = this.stringsMap.get(name);
                if (currentValue > keyInstances) {
                    keyInstances = currentValue;
                    key = name;
                }
            }
        }
        return String.valueOf(key.charAt(1));
    }

    /**
     * gets the first letter of the generated word
     * @return first letter of the word
     */
    private String iterateByFirstLetter() {
        this.alphabetInstances = new HashMap<>();
        for (String name : this.names) {
            String firstLetter = name.substring(0, 1);
            Integer count = this.alphabetInstances.get(firstLetter);
            if (count == null) {
                this.alphabetInstances.put(firstLetter, 1);
            } else {
                this.alphabetInstances.put(firstLetter, count + 1);
            }
        }
        return Collections.max(this.alphabetInstances.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }
}
