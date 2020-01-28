import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Site index
 *
 * @author Ozioma Paul
 *
 * Andrew ID: pozioma
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance.
 *
 */

public class SiteIndex
{
    public static void main(String[] args)
    {
        SiteIndexGUI gui = new SiteIndexGUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame();
                gui.addComponents(frame);
                frame.setPreferredSize(new Dimension(1500, 700));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
