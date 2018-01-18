package hearthstone;

import static hearthstone.Strings.Sfilename;
import java.awt.CardLayout;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class HearthStone implements Strings {
    public static Jezyk jezyk;
    
    public static JFrame frame;
    
    private MainFrame panel1;
    private SelectTypeOfPack panel2;
    private SelectCardsFrame panel3;
    private SelectPackFrame panel4;
    static MouseAdapter ma;
    static boolean fileExist = true;
    static private Map<String, Jezyk> m = new HashMap();
    
    
    static String loadLanguage() throws IOException, JSONException {
        String text = new String(Files.readAllBytes(Paths.get(Sfilename)), StandardCharsets.UTF_8);
        JSONObject jo = new JSONObject(text);
        return jo.getString(Slanguage);
    }
    
    public HearthStone() {}
        
    public HearthStone(Jezyk lng) {
        jezyk = lng;
    }
    
    public void displayGUI() throws IOException, FontFormatException, JSONException, ParseException
    { 
        frame = new JFrame(jezyk.getText(Stitle));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        
        frame.setResizable(false);
        frame.addMouseListener(ma);
        frame.addMouseMotionListener(ma);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());
        panel1 = new MainFrame(contentPane);
        panel2 = new SelectTypeOfPack(contentPane);
        panel3 = new SelectCardsFrame(contentPane);
        panel4 = new SelectPackFrame(contentPane);
        contentPane.add(panel1, Smainframe); 
        contentPane.add(panel2, Sselecttypeofpack);
        contentPane.add(panel3, Sselectcardsframe);
        contentPane.add(panel4, Sselectpackframe);
        
        frame.setContentPane(contentPane);
        ImageIcon img = new ImageIcon(getClass().getResource("/pack.png"));
        frame.setIconImage(img.getImage());
        frame.pack();   
        frame.setSize(700, 400);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) throws IOException, JSONException {
        for(int i=0; i<languages.length; i++)
            m.put(languages[i].getClass().getSimpleName(), languages[i]);
        
        if(!(new File(Sfilename).isFile())) {
            fileExist = false;
        }
        if(!fileExist){
            new SelectLanguage();
            
        } else {
            String lng = loadLanguage();
            if(m.containsKey(lng))
                jezyk = m.get(lng);
            else
                jezyk = new English();
        }
        ma = new MouseAdapter() {
        int lastX, lastY;
        @Override
        public void mousePressed(MouseEvent e) {
            lastX = e.getXOnScreen();
            lastY = e.getYOnScreen();
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            // Move frame by the mouse delta
            frame.setLocation(frame.getLocationOnScreen().x + x - lastX,
                    frame.getLocationOnScreen().y + y - lastY);
            lastX = x;
            lastY = y;
        }
    };
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            
           public void run() { 
                if(fileExist)
                    try {
                        new HearthStone().displayGUI();
                    } catch (IOException | FontFormatException | JSONException | ParseException ex) {
                        Logger.getLogger(HearthStone.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
    }
    
}
