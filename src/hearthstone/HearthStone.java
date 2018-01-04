package hearthstone;

import java.awt.CardLayout;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.sling.commons.json.JSONException;

public class HearthStone implements Strings {
    private static JFrame frame;
    private JPanel contentPane;
    
    private MainFrame panel1;
    private SelectPackFrame panel2;
    static MouseAdapter ma;
   
    
    private void displayGUI() throws IOException, FontFormatException, JSONException
    {
        
        frame = new JFrame(Stitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addMouseListener(ma);
        frame.addMouseMotionListener(ma);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());
        panel1 = new MainFrame(contentPane);
        panel2 = new SelectPackFrame(contentPane);
        contentPane.add(panel1, Smainframe); 
        contentPane.add(panel2, Sselectpackframe);
        
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
                try {
                    new HearthStone().displayGUI();
                } catch (IOException ex) {
                    Logger.getLogger(HearthStone.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FontFormatException ex) {
                    Logger.getLogger(HearthStone.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(HearthStone.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
}
