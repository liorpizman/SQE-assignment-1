import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

/**
 * WebCrawler class scan site's source code to obtain a list of first names
 * Source URL: https://www.behindthename.com/names/usage/english
 */
public class WebCrawler {

    private HashSet<String> names;
    private HashSet<String> letterLinks;

    private static final String URL = "https://www.behindthename.com/names/usage/english";
    private static final String DIV_BROWSENAME = "div.browsename";
    private static final String NAV_PAGINATION = "nav.pagination";
    private static final String A_HREF = "a[href]";
    private static final String ABS_HREF = "abs:href";
    private static final String SPACE = " ";
    private static final String EMPTY_STRING = "";
    private static final String ENGLISH_LETTERS = "^[ a-zA-Z]*$";

    public WebCrawler() {
        this.names = new HashSet<String>();
        this.letterLinks = new HashSet<String>();
        runParser();
    }

    /**
     * The parser running by two steps: first updates all letter links to a list
     * and then updates all names to another list
     */
    private void runParser() {
        updateLinks();
        updateAllNames();
    }

    public HashSet<String> getNames() {
        return this.names;
    }

    public boolean isLetterLinksEmpty() {
        return this.letterLinks == null || this.letterLinks.size() == 0;
    }

    /**
     * Returns all elements which where selected by a css query
     *
     * @param url       - page to select from
     * @param selectDiv - css query
     * @return selected elements
     */
    public Elements selectDivFromPage(String url, String selectDiv) {
        Elements selectedElements = null;
        try {
            Document page = Jsoup.connect(url).get();
            selectedElements = page.select(selectDiv);
        } catch (IOException e) {
            System.err.println("URL : " + url + " , Error: " + e.getMessage());
        }
        return selectedElements;
    }

    /**
     * Updates all names for a specific letter
     *
     * @param namesOnPage - page's elements to scan
     */
    public void updateCurrentLetterNames(Elements namesOnPage) {
        String currentName;
        for (int i = 0; i < namesOnPage.size(); i++) {
            Object childNode = namesOnPage.get(i).select(A_HREF).first().childNodes().get(0);
            currentName = childNode.toString();
            String updatedName = getValidName(currentName.trim());
            if (!updatedName.equals(EMPTY_STRING)) {
                names.add(updatedName);
            }
        }
    }

    /**
     * Updates all names for all the letters
     */
    public void updateAllNames() {
        if (!isLetterLinksEmpty()) {
            for (String letterLink : letterLinks) {
                Elements namesOnPage = selectDivFromPage(letterLink, DIV_BROWSENAME);
                updateCurrentLetterNames(namesOnPage);
            }
        }
    }

    /**
     * Updates all links to all letters
     */
    public void updateLinks() {
        Elements linksOnPage = selectDivFromPage(URL, NAV_PAGINATION);
        for (Element link : linksOnPage.select(A_HREF)) {
            letterLinks.add(link.attr(ABS_HREF));
        }
        letterLinks.add(URL);
    }

    /**
     * checks if a given word consists only english letters
     *
     * @param word input word
     * @return result if valid or not
     */
    public boolean isStringOnlyAlphabet(String word) {
        return ((!word.equals(EMPTY_STRING))
                && (word != null)
                && (word.matches(ENGLISH_LETTERS)));
    }

    /**
     * a validation of rules according to the demands
     *
     * @param name given name to test
     * @return valid output name
     */
    public String getValidName(String name) {
        if (!isStringOnlyAlphabet(name)) {
            return EMPTY_STRING;
        }
        return handleCapitalLetters(name);
    }

    /**
     * returns name in a chosen structure, example: RAFI COHEN ---> Rafi Cohen
     *
     * @param name input
     * @return name in the right structure
     */
    public String capitalizeName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    /**
     * checks cases with spaces and capital letters
     *
     * @param name input
     * @return valid output name
     */
    public String handleCapitalLetters(String name) {
        if (name == null || name.equals(EMPTY_STRING)) {
            return EMPTY_STRING;
        }
        if (name.contains(SPACE)) {
            return handleMultipleNames(name);
        }
        return capitalizeName(name);
    }

    /**
     * handles a case when the name consists more than one word
     *
     * @param nameWithSpace input with spaces
     * @return valid name
     */
    public String handleMultipleNames(String nameWithSpace) {
        String[] names = nameWithSpace.split(SPACE);
        String correctName = EMPTY_STRING;
        for (String name : names) {
            correctName += capitalizeName(name) + SPACE;
        }
        return correctName.substring(0, correctName.length() - 1);
    }

}
