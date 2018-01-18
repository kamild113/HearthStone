package hearthstone;

import static hearthstone.Strings.languages;
import java.awt.FlowLayout;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.apache.sling.commons.json.JSONException;

public class SelectLanguage extends JFrame implements Strings {

    JComboBox langList;

    public SelectLanguage() {
        String Slanguages[] = new String[languages.length];
        for(int i=0; i<languages.length; i++)
            Slanguages[i] = languages[i].getClass().getSimpleName();
        
        setTitle("Select language");
        setResizable(false);
        ImageIcon img = new ImageIcon(getClass().getResource("/pack.png"));
        setIconImage(img.getImage());
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        JLabel select = new JLabel(Sselectlanguage);
        select.setHorizontalAlignment(JLabel.CENTER);
        add(select);
        langList = new JComboBox(Slanguages);
        langList.setAlignmentX(CENTER_ALIGNMENT);
        langList.setSelectedIndex(0);
        add(langList);
        JButton ok = new JButton("Ok");
        add(ok);
        
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                   
                
                System.out.println("Making file...");
                try {
                    JSONHandling.makeFile(languages[langList.getSelectedIndex()].getClass().getSimpleName());
                } catch (JSONException ex) {
                    Logger.getLogger(HearthStone.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("File done.");
                HearthStone nowy = new HearthStone(languages[langList.getSelectedIndex()]);
                try {
                    nowy.displayGUI();
                } catch (IOException ex) {
                    Logger.getLogger(SelectLanguage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FontFormatException ex) {
                    Logger.getLogger(SelectLanguage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(SelectLanguage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(SelectLanguage.class.getName()).log(Level.SEVERE, null, ex);
                }
                setVisible(false);
                dispose();
            }
        });
        
        setSize(200,200);
        setVisible(true);
    }
}
