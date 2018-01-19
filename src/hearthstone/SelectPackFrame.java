package hearthstone;

import static hearthstone.Strings.Sdateformat;
import static hearthstone.Strings.Sfilename;
import static hearthstone.Strings.Ssuffix;
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
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    static private JPanel contentPane;
    private static Jezyk jezyk;
    static private Map<Date, String> m = new TreeMap<Date, String>(Collections.reverseOrder());
    private static Vector<JButton> buttons = new Vector<>();
    private static JLabel background;
    static JButton back;
    
    private static void loadData() throws JSONException, IOException, ParseException {
        m.clear();
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
                m.put(ft.parse("1970-01-0"+i+" 00:00:00:00"), top_bar_titles[i]);
            }
        }
    }
    
    private static int getButtonFromVector(String text) {
        for(int i=1; i<top_bar_titles.length; i++) {
            if(top_bar_titles[i].equals(text)) 
                return i;
        }
        return 0;
    }
    
    public static void putButtons() throws IOException {
        try {
            loadData();
        } catch (JSONException | ParseException ex) {
            Logger.getLogger(SelectPackFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        background.remove(back);
        for(int i=0; i<buttons.size(); i++)
            background.remove(buttons.get(i));
        
        for(Map.Entry<Date, String> entry : m.entrySet()) {
            JLabel label = new JLabel(jezyk.getText(entry.getValue()));
            label.setFont(new Font("Serif", Font.BOLD, 18));
            label.setForeground(Color.yellow);
            label.setPreferredSize(new Dimension(100, 135));
            label.setAlignmentY(BOTTOM_ALIGNMENT);
            label.setAlignmentX(CENTER_ALIGNMENT);
            buttons.get(getButtonFromVector(entry.getValue())-1).add(label);
            background.add(buttons.get(getButtonFromVector(entry.getValue())-1));
        }
                
        background.add(back);
    }
    
    public JButton makeButton(String text, int h, int w) throws IOException {
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
        label.setFont(new Font("Serif", Font.BOLD, 18));
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
                
                HearthStone.frame.setTitle(jezyk.getText(Stitle) +" - " + jezyk.getText(text));
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, Sselecttypeofpack);             
            }
        });
        background.add(tmp_button);
        return tmp_button;
    }
    
    public SelectPackFrame(JPanel panel) throws FontFormatException, IOException, JSONException, ParseException {
        contentPane = panel;
        jezyk = HearthStone.jezyk;
        loadData();
        setLayout(new BorderLayout());
	background=new JLabel(new ImageIcon(getClass().getResource("/background2.jpg")));
	add(background);
	
        background.setLayout(new FlowLayout(FlowLayout.CENTER,30,100));

        for(int i=1; i<top_bar_titles.length; i++) 
            buttons.add(makeButton(top_bar_titles[i], 100, 135));
        
        back = new JButton(jezyk.getText(Sback));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.first(contentPane);
            }
        });
             
    }
}