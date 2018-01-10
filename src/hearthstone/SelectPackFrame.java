package hearthstone;

import static hearthstone.Strings.Sdateformat;
import static hearthstone.Strings.Sfilename;
import static hearthstone.Strings.Ssuffix;
import static hearthstone.Strings.left_bar_titles;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import static java.awt.Component.BOTTOM_ALIGNMENT;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class SelectPackFrame extends JPanel implements Strings {
    
    private JPanel contentPane;
    private Font font;
    private Jezyk jezyk;
    private Map<Date, String> m = new TreeMap<Date, String>(Collections.reverseOrder());
    
    private void loadData() throws JSONException, IOException, ParseException {
        SimpleDateFormat ft = new SimpleDateFormat (Sdateformat);
        
        String text = new String(Files.readAllBytes(Paths.get(Sfilename)), StandardCharsets.UTF_8);
        JSONObject jo = new JSONObject(text);
        for(int i=1; i<top_bar_titles.length; i++) {
            JSONArray tab = jo.getJSONArray(top_bar_titles[i]+Ssuffix);
            if(tab.length() > 0) {
                JSONObject obj = tab.getJSONObject(tab.length()-1);
                Date data = ft.parse(obj.getString("date"));
                m.put(data, top_bar_titles[i]);
            } else {
                m.put(ft.parse("1970-01-0"+i+" 00:00:00"),top_bar_titles[i]);
            }
        }
    }
    
    public final JButton makeButton(String text, int h, int w) throws IOException {
        URL u = getClass().getResource("/packs/"+text+"_pack.png");
        String button = "/packs/"+text+"_pack.png";
        String hoverButton = "/packs/"+text+"_pack_hover.png";
        String activeButton = "/packs/"+text+"_pack_active.png";
        if(u == null) {
            button = "/packs/classic_pack.png";
            hoverButton = "/packs/classic_pack_hover.png";
            activeButton = "/packs/classic_pack_active.png";
        }
        BufferedImage startButton = ImageIO.read(getClass().getResource(button));
        BufferedImage startButtonHover = ImageIO.read(getClass().getResource(hoverButton));
        BufferedImage startButtonActive = ImageIO.read(getClass().getResource(activeButton));
        
        JButton tmp_button = new JButton(new ImageIcon(startButton));
        tmp_button.setRolloverIcon(new ImageIcon(startButtonHover));
        tmp_button.setPressedIcon(new ImageIcon(startButtonActive));
        tmp_button.setBorder(BorderFactory.createEmptyBorder());
        tmp_button.setContentAreaFilled(false);
        tmp_button.setFocusable(false);
        tmp_button.setPreferredSize(new Dimension(h, w));
        
        
        JLabel label = new JLabel(jezyk.getText(text));
        label.setFont(font.deriveFont(20f));
        label.setForeground(Color.yellow);
        label.setPreferredSize(new Dimension(h, w));
        label.setAlignmentY(BOTTOM_ALIGNMENT);
        label.setAlignmentX(CENTER_ALIGNMENT);
        tmp_button.add(label);
        tmp_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Singleton singleton = Singleton.getInstance();
                singleton.setPack(text);
                
                contentPane.add(new SelectCardsFrame(contentPane), Sselectcardsframe);
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, Sselectcardsframe);             
            }
        });
        return tmp_button;
    }
    
    public SelectPackFrame(JPanel panel) throws FontFormatException, IOException, JSONException, ParseException {
        contentPane = panel;
        jezyk = new Polski();
        font = Font.createFont(Font.TRUETYPE_FONT, new File(Sfont));
        loadData();
        setLayout(new BorderLayout());
	JLabel background=new JLabel(new ImageIcon(getClass().getResource("/background2.jpg")));
	add(background);
	
        background.setLayout(new FlowLayout(FlowLayout.CENTER,30,100));
        
        for(Map.Entry<Date, String> entry : m.entrySet()) {
            background.add(makeButton(entry.getValue(), 100, 135));
        }
        /*
        for(int i=1; i<top_bar_titles.length; i++) {
            background.add(makeButton(top_bar_titles[i], 100, 135));
        }*/
        
        JButton back = new JButton(jezyk.getText(Sback));
        
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.first(contentPane);
            }
        });
        
        background.add(back);
        
        setPreferredSize(new Dimension(700,400));
    }
}
