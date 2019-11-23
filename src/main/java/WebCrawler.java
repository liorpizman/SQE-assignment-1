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
        runCrawler();
    }

    private void runCrawler() {
        updateLinks();
        updateAllNames();
    }

    public boolean isLetterLinksEmpty() {
        return this.letterLinks == null || this.letterLinks.size() == 0;
    }

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

    public void updateCurrentLetterNames(Elements namesOnPage) {
        String currentName;
        for (int i = 0; i < namesOnPage.size(); i++) {
            currentName = namesOnPage.get(i).select(A_HREF).get(0).text();
            names.add(currentName);
        }
    }

    public void updateAllNames() {
        if (!isLetterLinksEmpty()) {
            for (String letterLink : letterLinks) {
                Elements namesOnPage = selectDivFromPage(letterLink, DIV_BROWSENAME);
                updateCurrentLetterNames(namesOnPage);
            }
        }
    }

    public void updateLinks() {
        Elements linksOnPage = selectDivFromPage(URL, DIV_LETTER_LINKS);
        for (Element link : linksOnPage.select(A_HREF)) {
            letterLinks.add(link.attr(ABS_HREF));
        }
    }

    public static void main(String[] args) {
        System.out.println("-------------------------start------------------------------------------");
        WebCrawler webCrawl = new WebCrawler();
        System.out.println("-------------------------end------------------------------------------");
    }
}
