import java.awt.*; // import the AWT graphic classes
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * Link listener class
 *
 * @author Ozioma Paul
 *
 * Andrew ID: pozioma
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance.
 *
 */

public class LinkListener implements HyperlinkListener
{
    private JEditorPane callback;

    public LinkListener(JEditorPane site)
    {
        callback = site;
    }

    public void hyperlinkUpdate(HyperlinkEvent e)
    {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
        {
            try
            {
                callback.setPage(e.getURL());
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }
}
