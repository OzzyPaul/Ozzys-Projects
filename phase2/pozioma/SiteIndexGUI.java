import java.awt.*; // import the AWT graphic classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*; // import the Swing classes
import javax.swing.event.HyperlinkEvent;

/**
 * Site Index GUI
 *
 * @author Ozioma Paul
 *
 * Andrew ID: pozioma
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance.
 *
 */

public class SiteIndexGUI extends LayoutGUI
{
    private URLUtils url;
    private JEditorPane wordsEditPane;
    private JEditorPane examplesEditPane;
    private JTextField enteredURL;
    private JButton generateButton;
    private JButton returnExample;
    private JButton returnWords;
    private IndexGenerator indexGenerator;

    public void addComponents(JFrame theFrame)
    {
        Container c = theFrame.getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.white);
        JLabel label = new JLabel("Enter URL: ");
        generateButton = new JButton("Generate Index");
        generateButton.addActionListener(new CrawlListener(this));
        returnExample = new JButton("Return to Examples Index");
        returnWords = new JButton("Return to Words Index");
        returnExample.setEnabled(false);
        returnWords.setEnabled(false);
        returnExample.addActionListener(new FileListenerExample());
        returnWords.addActionListener(new WordListenerExample());
        enteredURL = new JTextField("");
        enteredURL.setText("http://public.africa.local.cmu.edu/cbishop/pfun/index.html#now");
        enteredURL.setPreferredSize(new Dimension(500, 30));
        wordsEditPane = new JEditorPane();
        examplesEditPane = new JEditorPane();
        wordsEditPane.setEditable(false);
        examplesEditPane.setEditable(false);
        wordsEditPane.addHyperlinkListener(new LinkListener(wordsEditPane));
        examplesEditPane.addHyperlinkListener(new LinkListener(examplesEditPane));
        JScrollPane scrollNotes1 = new JScrollPane(wordsEditPane);
        JScrollPane scrollNotes2 = new JScrollPane(examplesEditPane);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(label);
        topPanel.add(enteredURL);
        topPanel.add(generateButton);
        topPanel.add(returnExample);
        topPanel.add(returnWords);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(scrollNotes1);
        bottomPanel.add(scrollNotes2);

        c.add(topPanel, BorderLayout.NORTH);
        c.add(bottomPanel, BorderLayout.CENTER);
        c.setSize(theFrame.getSize());
        c.setVisible(true);
    }

    public void showExampleFile()
    {
        String filename;
        File file;
        try
        {
            filename = indexGenerator.getExamplesIndexFile();
            file = new File(filename);
            examplesEditPane.setPage(file.toURI().toURL());
        }
        catch (IOException e)
        {
            System.out.println("Couldn't find file");
        }
    }

    public void showWordsFile()
    {
        String filename;
        File file;
        try
        {
            filename = indexGenerator.getWordsIndexFile();
            file = new File(filename);
            wordsEditPane.setPage(file.toURI().toURL());
            
        }
        catch (IOException e)
        {
            System.out.println("Couldn't find file");
        }
    }

    class FileListenerExample implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            showExampleFile();
        }
    }

    class WordListenerExample implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            showWordsFile();
        }
    }

    class CrawlListener implements ActionListener
    {
        SiteIndexGUI callback;

        public CrawlListener(SiteIndexGUI gui)
        {
            callback = gui;
        }

        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            try
            {
                url = new URLUtils(enteredURL.getText());
                wordsEditPane.setPage(enteredURL.getText());
                callback.generateIndex();
                returnExample.setEnabled(true);
                returnWords.setEnabled(true);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void generateIndex()
    {
        indexGenerator = new IndexGenerator();
        String website = enteredURL.getText();
        HTMLLink link = new HTMLLink("Label", website);
        indexGenerator.processSites(link);
        showExampleFile();
        showWordsFile();
    }
}
