/**
 * HTML Link class
 *
 * @author Ozioma Paul
 *
 * Andrew ID: pozioma
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance.
 *
 */

public class HTMLLink
{
    private String url;
    private String label;
    private String baseUrl;
    
    public HTMLLink(String nLabel, String nUrl)
    {
        url = nUrl;
        label = nLabel;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public String getBase()
    {
        URLUtils newU = new URLUtils(url);
        return newU.getBase();   
    }
    
    public void setUrl(String newUrl)
    {
        url = newUrl;
    }
    
    public void setLabel(String newLabel)
    {
        label = newLabel;
    }
    
    public String getLabel()
    {
        return label;
    }
    
    public String formatLink()
    {
        return "<a href=" + url + ">" + label + "</a>";
    }
    
    public String toString()
    {
        return url;
    }
}
