package hearthstone;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;


public class PreviewFrame extends JPanel implements Strings{
    
    JLabel labels[][] = new JLabel[left_bar_titles.length][top_bar_titles.length];
    private Jezyk jezyk;
    private JPanel contentPane;  
    Singleton singleton = Singleton.getInstance();

    private void makeBars(JLabel bg) throws JSONException, IOException {
        JLabel tab[];
        JPanel bar;
        for(int j=0; j<left_bar_titles.length; j++) {
            tab = new JLabel[top_bar_titles.length];
            tab[0] = new JLabel(jezyk.getText(left_bar_titles[j]));
            tab[0].setForeground(colors[j]);
            if(j%2 == 0)
                tab[0].setFont(new Font("Serif", Font.BOLD, 16));
            else
                tab[0].setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 16));
               
            for(int i=1; i<top_bar_titles.length; i++){
                tab[i] = new JLabel();
                tab[i].setForeground(colors[j]);
                if(j%2 == 0)
                    tab[i].setFont(new Font("Serif", Font.BOLD, 20));
                else
                    tab[i].setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
            }

            bar = new JPanel();
            bar.setLayout(new GridLayout(0, top_bar_titles.length));
            bar.add(tab[0]);
            for(int i=1; i<top_bar_titles.length; i++) {
                tab[i].setHorizontalAlignment(SwingConstants.CENTER);
                bar.add(tab[i]);
            }
            labels[j] = tab;
            bar.setOpaque(false);
            bg.add(bar);
        }
        loadData();
    }
    
    private void parseJson(JSONObject obj) throws JSONException {
        for(int i=1; i<top_bar_titles.length; i++) {
            for(int j=0; j<left_bar_titles.length; j++) {
                String label = obj.getJSONObject(top_bar_titles[i]).get(left_bar_titles[j]).toString();
                labels[j][i].setText(label);
            }
        }
    }
    
    private void loadData() throws JSONException, IOException {
        String text = new String(Files.readAllBytes(Paths.get(Sfilename)), StandardCharsets.UTF_8);
        JSONObject jo = new JSONObject(text); 
        parseJson(jo);
    }
    
    
    public PreviewFrame(JPanel panel) throws JSONException, IOException {
        contentPane = panel;
        jezyk = HearthStone.jezyk;

        setLayout(new BorderLayout());
	JLabel background=new JLabel(new ImageIcon(getClass().getResource("/bg_tab.jpg")));
	add(background);
        background.setLayout(new GridLayout(left_bar_titles.length+1,0));

     
        JPanel top_bar = new JPanel();
        top_bar.setOpaque(false);
        top_bar.setLayout(new GridLayout(0, top_bar_titles.length));
        JButton przycisk = new JButton("<< "+jezyk.getText(Sback)+" <<");
        przycisk.setBorderPainted(false);
        przycisk.setFocusPainted(false);
        przycisk.setContentAreaFilled(false);
        przycisk.setForeground(Color.yellow);
        top_bar.add(przycisk);
        for(int i=1; i<top_bar_titles.length; i++){
            String title = top_bar_titles[i];
            JButton tmpButton = new JButton(jezyk.getText(title));
            tmpButton.setForeground(Color.yellow);
            tmpButton.setBorderPainted(false);
            tmpButton.setFocusPainted(false);
            tmpButton.setContentAreaFilled(false);
            tmpButton.addActionListener(new ActionListener(){  
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    HearthStone.frame.setEnabled(false);
                    JFrame frame = new JFrame (jezyk.getText(Spackhistory)+" - "+jezyk.getText(title));
                    frame.getContentPane().add(new PackHistoryFrame(title));
                    ImageIcon img = new ImageIcon(getClass().getResource("/pack.png"));
                    frame.setIconImage(img.getImage());
                    frame.pack();
                    frame.setVisible (true);
                    frame.setLocationRelativeTo(contentPane);
                    frame.setResizable(false);
                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            HearthStone.frame.setEnabled(true);
                        }
                    });
                    frame.setSize(400,400);
                    
                } catch (ParseException ex) {
                    Logger.getLogger(PreviewFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
         });  
            top_bar.add(tmpButton);
        }            

        background.add(top_bar);
        makeBars(background);    
         
         przycisk.addActionListener(new ActionListener(){  
            @Override
            public void actionPerformed(ActionEvent e){
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, Smainframe);
            }
         });  
         
        setVisible(true);
    }
}