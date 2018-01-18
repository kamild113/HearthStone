package hearthstone;

import static hearthstone.HearthStone.jezyk;
import static hearthstone.Strings.Stitle;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class SelectCardsFrame extends JPanel implements Strings{
    
    private JPanel contentPane;
    static private JButton addButton;
    static private int cardsCount = 0;
    static private int commonsCount = 0;
    static MouseAdapter ma;
    Map m = new LinkedHashMap(top_bar_titles.length);
    
    static Vector<JLabel> labels=new Vector<JLabel>();
    
    private void saveData() throws JSONException, IOException, ParseException {
        Singleton singleton = Singleton.getInstance();
        
        String text = new String(Files.readAllBytes(Paths.get(Sfilename)), StandardCharsets.UTF_8);
        JSONObject jo = new JSONObject(text); 

        JSONObject loaded = jo.getJSONObject(singleton.getPack());

        for(int i=0; i<left_bar_titles.length; i++) {
            if(m.containsKey(left_bar_titles[i])) {
                int value = Integer.parseInt(loaded.get(left_bar_titles[i]).toString())+
                        Integer.parseInt(m.get(left_bar_titles[i]).toString());
                loaded.remove(left_bar_titles[i]);
                loaded.put(left_bar_titles[i], ""+value);
            }      
        }
        jo.remove(singleton.getPack());
        jo.put(singleton.getPack(), loaded);

        JSONObject tmp = new JSONObject(m);
        jo.accumulate(singleton.getPack()+Ssuffix, tmp);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(Sfilename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PreviewFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pw.write(jo.toString(4));
         
        pw.flush();
        pw.close();        
    }
    
    public static void resetLabels() {
        for(int i=0; i<labels.size(); i++) {
            labels.get(i).setText("0");
        }
        cardsCount = 0;
        commonsCount = 0;
        addButton.setEnabled(false);
    }
    
    public final JButton makeButton(String card, String button, String rollover, String pressed, int h, int w) throws IOException {
        BufferedImage startButton = ImageIO.read(getClass().getResource("/"+button));
        BufferedImage startButtonHover = ImageIO.read(getClass().getResource("/"+rollover));
        BufferedImage startButtonActive = ImageIO.read(getClass().getResource("/"+pressed));
        
        JButton tmp_button = new JButton(new ImageIcon(startButton));
        tmp_button.setRolloverIcon(new ImageIcon(startButtonHover));
        tmp_button.setPressedIcon(new ImageIcon(startButtonActive));
        tmp_button.setBorder(BorderFactory.createEmptyBorder());
        tmp_button.setContentAreaFilled(false);
        tmp_button.setFocusable(false);
        tmp_button.setPreferredSize(new Dimension(h, w));
        
        
        JLabel label = new JLabel("0");
        labels.add(label);
        label.setFont(new Font("Serif", Font.BOLD, 16));
        label.setForeground(Color.yellow);
        label.setBorder(BorderFactory.createEmptyBorder( 65 , 7, 0, 0 )); // 65px w dol, 5px w prawo
        label.setAlignmentX(CENTER_ALIGNMENT);
        tmp_button.add(label);
        ma = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    if(Integer.parseInt(label.getText()) > 0) {
                        int count = Integer.parseInt(label.getText())-1;
                        label.setText(""+count);
                        if((card.equals(left_bar_titles[0]) || card.equals(left_bar_titles[1]))) /// Common && GCommon
                            commonsCount--;
                        cardsCount--;
                        m.remove(card, label.getText());
                        addButton.setEnabled(false);
                    }
                } else {
                    if(cardsCount < 5) {
                        if((card.equals(left_bar_titles[0]) || card.equals(left_bar_titles[1]))) {  /// Common && GCommon
                            if(commonsCount < 4) {
                                int count = Integer.parseInt(label.getText())+1;
                                label.setText(""+count);
                                cardsCount++;
                                commonsCount++;
                                m.put(card, label.getText());
                            }
                        } else {
                            int count = Integer.parseInt(label.getText())+1;
                            label.setText(""+count);
                            cardsCount++;
                            m.put(card, label.getText());
                        }
                        if(cardsCount == 5) {
                            addButton.setEnabled(true);
                        }
                    }
                }
            }
        };
        tmp_button.addMouseListener(ma);
        
        return tmp_button;
    }
    
    public SelectCardsFrame(JPanel panel) throws IOException {
        SimpleDateFormat ft = new SimpleDateFormat (Sdateformat);
        
        contentPane = panel;
        m.put("date", ft.format(new Date()));
        Jezyk jezyk = HearthStone.jezyk;
        
        setLayout(new BorderLayout());
	JLabel background=new JLabel(new ImageIcon(getClass().getResource("/background2.jpg")));
	add(background);
	
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        
        addButton = new JButton(jezyk.getText(Sadd));
        addButton.setEnabled(false);
        
        JButton back = new JButton(jezyk.getText(Sback));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SelectPackFrame.putButtons();
                } catch (IOException ex) {
                    Logger.getLogger(SelectCardsFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, Sselectpackframe);
                HearthStone.frame.setTitle(jezyk.getText(Stitle));
            }
        });
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveData();
                    SelectPackFrame.putButtons();
                } catch (JSONException | IOException | ParseException ex) {
                    Logger.getLogger(SelectCardsFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                HearthStone.frame.setTitle(jezyk.getText(Stitle));
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, Smainframe);
            }
        });
        
        for (String title : left_bar_titles) {
            background.add(makeButton(title, "cards/"+title+".png", "cards/"+title+"_hover.png", "cards/"+title+"_active.png", 110, 150));
        }
        
        background.add(back);
        background.add(addButton);     
        
    }
    
}
