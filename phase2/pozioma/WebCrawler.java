import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;

/**
 * WebCrawler Class
 *
 * @author Ozioma Paul
 *
 * Andrew ID: pozioma
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance.
 *
 */

public class WebCrawler
{
    private TreeMap<String, HTMLLink> doneList;
    private TreeMap<String, HTMLLink> undoneList;
    private TreeMap<String, HTMLLink> exampleFileList;
    private TreeMap<String, ArrayList<HTMLLink>> wordsIndex;
    private int numOfErrors;
    private ArrayList<String> excludeWords;

    public WebCrawler()
    {
        doneList = new TreeMap<String, HTMLLink>();
        undoneList = new TreeMap<String, HTMLLink>();
        exampleFileList = new TreeMap<String, HTMLLink>();
        wordsIndex = new TreeMap<String, ArrayList<HTMLLink>>();
        numOfErrors = 0;
        excludeWords = new ArrayList<String>();
    }


    public void crawl(HTMLLink fullUrl) throws IOException
    {
        String baseUrl;
        URL site;
        String web;
        String label;
        String url;
        String [] splitter1;
        String [] splitter2;
        String [] links;
        BufferedReader webPage;
        
        readIn();

        try
        {
            baseUrl = fullUrl.getBase();
            site = new URL(fullUrl.getUrl());
            web = "";
            label = "";
            url = "";
            splitter1 = null;

            webPage = new BufferedReader(new InputStreamReader(site.openStream()));
            web = webPage.readLine();

            while (web != null)
            {
                getWords(web, fullUrl);

                if (web.contains("href") && web.contains(".html"))
                {
                    links = web.split("href=");
                    if (links.length > 1)
                    {
                        splitter1 = links[1].split(">");
                        splitter2 = links[1].split(">");

                        if (splitter2.length > 1)
                        {
                            splitter2 = splitter2[1].split("<");
                            label = splitter2[0];
                            url = splitter1[0].replace("\"", "");

                            if (inCurrentSite(url))
                            {
                                url = baseUrl + url;
                                HTMLLink html = new HTMLLink(label, url);
                                addLink(html);
                            }
                        }
                    }
                }

                if (web.contains(".") && !web.contains("com") && !web.contains("html") && !web.contains("htm"))
                {
                    if (web.contains(".java") || web.contains(".cpp") || web.contains(".c") || web.contains(".h"))
                    {
                        splitter1 = web.split("href=");
                        if (splitter1.length > 1)
                        {
                            splitter1 = splitter1[1].split(">");
                            url = baseUrl + splitter1[0];
                            if (splitter1.length > 1)
                            {
                                splitter1 = splitter1[1].split("<");
                                label = splitter1[0];
                                HTMLLink html = new HTMLLink(label, url);
                                if (!exampleFileList.containsKey(html.getUrl()))
                                    exampleFileList.putIfAbsent(html.getUrl(), html);
                            }
                        }
                    }
                }
                web = webPage.readLine();
            }
            webPage.close();
            doneList.putIfAbsent(fullUrl.getUrl(), fullUrl);
        }

        catch (IOException e)
        {
            numOfErrors++;
        }
    }

    private boolean inCurrentSite(String url)
    {
        if (url.startsWith("http"))
            return false;

        return true;
    }

    public void readIn()
    {
        String inputName = "";
        InputDataFile file = new InputDataFile("excludeWords.txt");
        
        file.open();
        
        if (!file.isOpen())
            System.out.println("Can't view exclude words");

        else
        {
            inputName = file.readString();
            while (inputName != null && !excludeWords.contains(inputName.toLowerCase()))
            {
                excludeWords.add(inputName.toLowerCase());
                inputName = file.readString();
            }
        }
        file.close();
    }

    public void crawlNext()
    {
        HTMLLink object;
        String key;
        
        while (!undoneList.isEmpty())
        {
            object = undoneList.pollFirstEntry().getValue();
            key = object.getUrl();
            doneList.put(key, object);
            try
            {
                crawl(object);
            }

            catch (IOException e)
            {
                numOfErrors++;
            }
        }
    }

    public void getWords(String line, HTMLLink page)
    {
        String[] test;
        ArrayList<HTMLLink> link;

        line = line.toLowerCase().replaceAll("<.*?>", " ");
        line = line.replaceAll("[\\p{Punct}]", " ");
        line = line.replaceAll("\\d+", "");
        test = line.split(" ");
        
        if (test != null)
        {
            for (int i = 0; i < test.length; i++)
            {
                test[i] = test[i].trim();
                if(test[i].length() <= 2)
                    continue;
                
                if(excludeWords.contains(test[i].toLowerCase()))
                    continue;
                    
                if (test[i].trim().isEmpty())
                    continue;

                if (!wordsIndex.containsKey(test[i]))
                {
                    link = new ArrayList<HTMLLink>();
                    link.add(page);
                    wordsIndex.put(test[i], link);
                }

                else
                {
                    link = wordsIndex.get(test[i]);
                    if (!link.contains(page))
                        link.add(page);
                }
            }
        }
    }

    public void addLink(HTMLLink html)
    {
        if (doneList.containsKey(html.getUrl()))
            return;

        if (undoneList.containsKey(html.getUrl()))
            return;

        undoneList.putIfAbsent(html.getUrl(), html);
    }

    public void writeExampleFile(String exampleIndexFile)
    {
        OutputDataFile file = new OutputDataFile(exampleIndexFile);
        file.open();
        for (Map.Entry<String, HTMLLink> entry : exampleFileList.entrySet())
            file.println("<p style=\"font-size:15px\">" + entry.getValue().formatLink());
        file.close();
    }

    public void writeWordsFile(String wordsIndexFile)
    {
        ArrayList<HTMLLink> array;
        OutputDataFile file = new OutputDataFile(wordsIndexFile);
        file.open();
        for (String entry : wordsIndex.keySet())
        {
            file.println("<p style=\"font-size:20px\"><b>" + entry + "</b>");
            array = wordsIndex.get(entry);
            for (int i = 0; i < array.size(); i++)
                file.println("<p style=\"font-size:15px\">" + array.get(i).formatLink());
        }
        file.close();
    }
}