import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

/**
 * Test site index
 *
 * @author Ozioma Paul
 *
 * Andrew ID: pozioma
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance.
 *
 */

public class TestSiteIndex
{
    public static void main(String[] args) throws IOException
    {
        String htmlLink = "http://public.africa.local.cmu.edu/cbishop/pfun/index.html";
        String label = "04-330";       
        HTMLLink link = new HTMLLink(label, htmlLink);
        URLUtils url = new URLUtils(htmlLink);
        IndexGenerator index = new IndexGenerator(htmlLink);
        
        System.out.println("1. My secret link for Phase 4: http://public.africa.local.cmu.edu/cbishop/pfun/.phase4/poziomag3a/");
        System.out.println("2. The Collection class to use for implementing real back-buttons is: Stack");
                
    }

}
