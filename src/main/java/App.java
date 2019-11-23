import java.util.LinkedList;

public class App {

    private WebCrawler webCrawler;
    private Commands commands;

    public App() {
        this.webCrawler = new WebCrawler();
        this.commands = new Commands(this.webCrawler.getNames());
        /* test cases */
//        LinkedList<String> test = new LinkedList<String>();
//        test.add("Assaf");
//        test.add("Yarden");
//        this.commands = new Commands(test);
    }

    public static void main(String[] args) {
        App app = new App();
        //app.commands.CountSpecificString("write-your-substring");
        //app.commands.CountAllStrings(2);
        app.commands.CountMaxString(1);
    }
}
