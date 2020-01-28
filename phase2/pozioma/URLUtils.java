/**
 * URL Utils
 *
 * @author Ozioma Paul
 *
 * Andrew ID: pozioma
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance.
 *
 */

public class URLUtils
{
    private String url;
    private String base;

    public URLUtils()
    {
        url = "http://public.africa.cmu.edu/cbishop/pfun/.phase2/pozioma5dy/";
        base = getBaseUrl(url);
    }

    public URLUtils(String urll)
    {
        url = urll;
        base = getBaseUrl(url);
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String newUrl)
    {
        url = newUrl;
    }

    public void setBase(String basee)
    {
        base = basee;
    }
    
    public String getBase()
    {
        return base;
    }

    public static String getBaseUrl(String urlLink)
    {
        String [] http;
        http = urlLink.split("//");

        String [] urlPaths;
        urlPaths = http[1].split("/");
        
        return http[0] + "//" + urlPaths[0] + "/" + urlPaths[1] + "/" + urlPaths[2] + "/";       
    }

    public String getUrlTitle(String urlLink)
    {
        String[] urlPaths;
        urlPaths = urlLink.split("/");

        if (urlPaths.length == 0)
            return "";

        return urlPaths[urlPaths.length - 2];
    }

    public String toString()
    {
        return url;
    }
}
