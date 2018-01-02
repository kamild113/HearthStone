package hearthstone;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.json.simple.JSONObject;

public class SelectCardsFrame extends JPanel {
    
    private JPanel contentPane;
    private Font font;
    private JButton addButton;
    private int cardsCount = 0;
    private int commonsCount = 0;
    static MouseAdapter ma;
    Map m = new LinkedHashMap(5);
    /*
    private void saveData() {
        JSONObject jo = new JSONObject();
        Map m;
        for(int i=0; i<8; i++) {
            m = new LinkedHashMap(5);
            for(int j=1; j<6; j++){
                m.put(top_bar_titles[j], labels[i][j].getText());
            }   
            jo.put(left_bar_titles[i], m);
        }
                
        PrintWriter pw = null;
        try {
            pw = new PrintWriter("JSONExample.json");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        pw.write(jo.toJSONString());
         
        pw.flush();
        pw.close();        
    }*/
    
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
        label.setFont(font.deriveFont(30f));
        label.setForeground(Color.yellow);
        label.setBorder(BorderFactory.createEmptyBorder( 65 , 5, 0, 0 )); // 65px w dol, 5px w prawo
        label.setAlignmentX(CENTER_ALIGNMENT);
        tmp_button.add(label);
        ma = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    if(Integer.parseInt(label.getText()) > 0) {
                        int count = Integer.parseInt(label.getText())-1;
                        label.setText(""+count);
                        if((card.equals("common") || card.equals("gcommon"))) 
                            commonsCount--;
                        cardsCount--;
                        addButton.setEnabled(false);
                    }
                } else {
                    if(cardsCount < 5) {
                        if((card.equals("common") || card.equals("gcommon"))) {
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
    
    public SelectCardsFrame(JPanel panel) {
        contentPane = panel;
        Jezyk jezyk = new Polski();
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("BlizQuadrata.ttf"));
        } catch (FontFormatException ex) {
            Logger.getLogger(SelectCardsFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SelectCardsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setLayout(new BorderLayout());
	JLabel background=new JLabel(new ImageIcon(getClass().getResource("/background2.jpg")));
	add(background);
	
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        Singleton singleton = Singleton.getInstance();
        String text = singleton.getPack();
        JButton common = null;
        JButton gcommon = null;
        JButton rare = null;
        JButton grare = null;
        JButton epic = null;
        JButton gepic = null;
        JButton legend = null;
        JButton glegend = null;
        try {
            common = makeButton("common", "cards/common.png", "cards/common_hover.png", "cards/common_active.png", 110, 150);
            gcommon = makeButton("gcommon", "cards/gcommon.png", "cards/gcommon_hover.png", "cards/gcommon_active.png", 110, 150);
            rare = makeButton("rare", "cards/rare.png", "cards/rare_hover.png", "cards/rare_active.png", 110, 150);
            grare = makeButton("grare", "cards/grare.png", "cards/grare_hover.png", "cards/grare_active.png", 110, 150);
            epic = makeButton("epic", "cards/epic.png", "cards/epic_hover.png", "cards/epic_active.png", 110, 150);
            gepic = makeButton("gepic", "cards/gepic.png", "cards/gepic_hover.png", "cards/gepic_active.png", 110, 150);
            legend = makeButton("legend", "cards/legend.png", "cards/legend_hover.png", "cards/legend_active.png", 110, 150);
            glegend = makeButton("glegend", "cards/glegend.png", "cards/glegend_hover.png", "cards/glegend_active.png", 110, 150);
                
        } catch (IOException ex) {
            Logger.getLogger(SelectCardsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JButton back = new JButton("Powrót");
        addButton = new JButton("Dodaj");
        addButton.setEnabled(false);
        
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "SelectPackFrame");
            }
        });
        
        background.add(common);
        background.add(gcommon);
        background.add(rare);
        background.add(grare);
        background.add(epic);
        background.add(gepic);
        background.add(legend);
        background.add(glegend);
        background.add(back);
        background.add(addButton);
        
        setPreferredSize(new Dimension(700,400));
    }
    
}
