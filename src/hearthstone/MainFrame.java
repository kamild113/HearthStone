package hearthstone;

import static hearthstone.Strings.Spreviewframe;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.sling.commons.json.JSONException;
 
public class MainFrame extends JPanel implements Strings { 
    
    private JPanel contentPane;  
    
    public MainFrame(JPanel panel) throws IOException, FontFormatException {
       
        contentPane = panel;
        Jezyk jezyk = new Polski();
        
        Font font = Font.createFont(Font.TRUETYPE_FONT, new File(Sfont));
        setLayout(new BorderLayout());

	JLabel background=new JLabel(new ImageIcon(getClass().getResource("/background2.jpg")));
	add(background);
	
        background.setLayout(new FlowLayout(FlowLayout.CENTER,100,50));

        BufferedImage startButton = ImageIO.read(getClass().getResource("/pack.png"));
        BufferedImage startButtonHover = ImageIO.read(getClass().getResource("/pack_hover.png"));
        BufferedImage startButtonActive = ImageIO.read(getClass().getResource("/pack_active.png"));
        
        BufferedImage startButtonView = ImageIO.read(getClass().getResource("/packs.png"));
        BufferedImage startButtonHoverView = ImageIO.read(getClass().getResource("/packs_hover.png"));
        BufferedImage startButtonActiveView = ImageIO.read(getClass().getResource("/packs_active.png"));

        JButton newPack = new JButton(new ImageIcon(startButton));
        newPack.setRolloverIcon(new ImageIcon(startButtonHover));
        newPack.setPressedIcon(new ImageIcon(startButtonActive));
        newPack.setBorder(BorderFactory.createEmptyBorder());
        newPack.setContentAreaFilled(false);
        newPack.setFocusable(false);
        newPack.setPreferredSize(new Dimension(200, 260));
        
        JButton viewPacks = new JButton(new ImageIcon(startButtonView));
        viewPacks.setRolloverIcon(new ImageIcon(startButtonHoverView));
        viewPacks.setPressedIcon(new ImageIcon(startButtonActiveView));
        viewPacks.setBorder(BorderFactory.createEmptyBorder());
        viewPacks.setContentAreaFilled(false);
        viewPacks.setFocusable(false);
        
        JLabel newPackLabel = new JLabel(jezyk.getText(Sopen));
        newPackLabel.setFont(font.deriveFont(35f));
        newPackLabel.setForeground(Color.yellow);
        newPackLabel.setPreferredSize(new Dimension(220, 245));
        newPackLabel.setAlignmentY(BOTTOM_ALIGNMENT);
        newPackLabel.setAlignmentX(CENTER_ALIGNMENT);
        newPack.add(newPackLabel);
        
        JLabel viewPacksLabel = new JLabel(jezyk.getText(Sview));
        viewPacksLabel.setFont(font.deriveFont(35f));
        viewPacksLabel.setForeground(Color.yellow);
        viewPacksLabel.setPreferredSize(new Dimension(220, 245));
        viewPacksLabel.setAlignmentY(BOTTOM_ALIGNMENT);
        viewPacksLabel.setAlignmentX(CENTER_ALIGNMENT);
        viewPacks.add(viewPacksLabel);
        
        
        viewPacks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    /*
                    JFrame frame = new JFrame ("MyPanel");
                    frame.getContentPane().add (new MyFrame());
                    frame.pack();
                    frame.setVisible (true);
                    */
                    contentPane.add(new MyFrame(contentPane), Spreviewframe);
                } catch (JSONException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, Spreviewframe);
            }
        });
        
        newPack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    contentPane.add(new SelectPackFrame(contentPane), Sselectpackframe);
                } catch (FontFormatException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, Sselectpackframe);
            }
        });
        
        background.add(newPack);
        background.add(viewPacks);
        setSize(700, 400);
        setVisible(true);
    }
}
