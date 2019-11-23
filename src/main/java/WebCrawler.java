import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;

/**
 * WebCrawler class scan site's source code to obtain a list of first names
 * Source URL: https://www.behindthename.com/names/list
 */
public class WebCrawler {

    private LinkedList<String> names;
    private LinkedList<String> letterLinks;

    private static final String URL = "https://www.behindthename.com/names/list";
    private static final String DIV_BROWSENAME = "div.browsename";
    private static final String DIV_LETTER_LINKS = "div.letterlinks";
    private static final String A_HREF = "a[href]";
    private static final String ABS_HREF = "abs:href";

    public WebCrawler() {
        this.names = new LinkedList<String>();
        this.letterLinks = new LinkedList<String>();
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

    public LinkedList<String> getNames() {
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
            currentName = namesOnPage.get(i).select(A_HREF).get(0).text();
            names.add(currentName);
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
        Elements linksOnPage = selectDivFromPage(URL, DIV_LETTER_LINKS);
        for (Element link : linksOnPage.select(A_HREF)) {
            letterLinks.add(link.attr(ABS_HREF));
        }
    }
}
