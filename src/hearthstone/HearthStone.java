package hearthstone;

import static hearthstone.Strings.Sfilename;
import static hearthstone.Strings.left_bar_titles;
import static hearthstone.Strings.top_bar_titles;
import java.awt.CardLayout;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class HearthStone implements Strings {
    public static JFrame frame;
    private JPanel contentPane;
    
    private MainFrame panel1;
    private SelectPackFrame panel2;
    static MouseAdapter ma;
   
    private static void makeFile() throws JSONException {
        JSONObject jo = new JSONObject();
        Map m;
        
        for(int i=1; i<top_bar_titles.length; i++) {
            m = new LinkedHashMap(5);
            for(int j=0; j<left_bar_titles.length; j++){
                m.put(left_bar_titles[j], "0");
            }   
            jo.put(top_bar_titles[i], m);
        }
        for(int i=1; i<top_bar_titles.length; i++)
            jo.put(top_bar_titles[i]+Ssuffix, new JSONArray());
        
                
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(Sfilename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        pw.write(jo.toString(4));
         
        pw.flush();
        pw.close();     
    }
    
    private void displayGUI() throws IOException, FontFormatException, JSONException, ParseException
    { 
        frame = new JFrame(Stitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addMouseListener(ma);
        frame.addMouseMotionListener(ma);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());
        panel1 = new MainFrame(contentPane);
        //panel2 = new SelectPackFrame(contentPane);
        contentPane.add(panel1, Smainframe); 
        //contentPane.add(panel2, Sselectpackframe);
        
        frame.setContentPane(contentPane);
        ImageIcon img = new ImageIcon(getClass().getResource("/pack.png"));
        frame.setIconImage(img.getImage());
        frame.pack();   
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
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
            
           public void run()
            {
                if(!(new File(Sfilename).isFile())) {
                    System.out.println("Make file...");
                    try {
                        makeFile();
                    } catch (JSONException ex) {
                        Logger.getLogger(HearthStone.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("File done.");
                }
                try {
                    new HearthStone().displayGUI();
                } catch (IOException ex) {
                    Logger.getLogger(HearthStone.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FontFormatException ex) {
                    Logger.getLogger(HearthStone.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(HearthStone.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(HearthStone.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
}
