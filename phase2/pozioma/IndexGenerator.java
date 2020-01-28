import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Index generator
 *
 * @author Ozioma Paul
 *
 * Andrew ID: pozioma
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance.
 *
 */

public class IndexGenerator
{
    private String url;
    private String wordsIndexFile;
    private String exampleIndexFile;
    private WebCrawler crawler;
    
    public IndexGenerator()
    {
        crawler = new WebCrawler();
        exampleIndexFile = "examplesPage.html";
        wordsIndexFile = "wordsPage.html";
    }

    public IndexGenerator(String nUrl)
    {
        crawler = new WebCrawler();
        String[] http;
        http = nUrl.split("//");

        String[] urlPaths;
        urlPaths = http[1].split("/");

        url = http[0] + "//" + urlPaths[0] + "/" + urlPaths[1] + "/" + urlPaths[2] + "/";

        wordsIndexFile = urlPaths[3];
        exampleIndexFile = "examplesPage.html";
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String newUrl)
    {
        String[] http;
        http = newUrl.split("//");

        String[] urlPaths;
        urlPaths = http[1].split("/");

        url = http[0] + "//" + urlPaths[0] + "/" + urlPaths[1] + "/" + urlPaths[2] + "/";

        wordsIndexFile = urlPaths[3];
        exampleIndexFile = "examplesPage.html";
    }

    public String getWordsIndexFile()
    {
        return wordsIndexFile;
    }

    public String getExamplesIndexFile()
    {
        return exampleIndexFile;
    }

    public String toString()
    {
        return "URL: " + getUrl() + " " + "wordsIndexFile: " + getWordsIndexFile() + " " + "exampleIndexFile: "
                + exampleIndexFile;
    }

    public void processSites(HTMLLink website)
    {
        crawler = new WebCrawler();
        
        try
        {
            crawler.crawl(website);
            crawler.crawlNext();
            crawler.writeExampleFile(exampleIndexFile);
            crawler.writeWordsFile(wordsIndexFile);
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
