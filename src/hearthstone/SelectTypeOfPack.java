package hearthstone;

import static hearthstone.Strings.S1_4;
import static hearthstone.Strings.Sother;
import static hearthstone.Strings.Sselectpackframe;
import static hearthstone.Strings.Stitle;
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
 
public class SelectTypeOfPack extends JPanel implements Strings { 
    
    private JPanel contentPane;  

    public SelectTypeOfPack(JPanel panel) throws IOException, FontFormatException {
        contentPane = panel;
        Jezyk jezyk = HearthStone.jezyk;
        
        setLayout(new BorderLayout());
	JLabel background=new JLabel(new ImageIcon(getClass().getResource("/background2.jpg")));
	add(background);
        background.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));

        JButton oneFour = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/onefour.png"))));
        oneFour.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/onefour_hover.png"))));
        oneFour.setPressedIcon(new ImageIcon(ImageIO.read(getClass().getResource("/onefour_active.png"))));
        oneFour.setBorder(BorderFactory.createEmptyBorder());
        oneFour.setContentAreaFilled(false);
        oneFour.setFocusable(false);
        oneFour.setPreferredSize(new Dimension(310, 260));
        
        JButton other = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/other.png"))));
        other.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/other_hover.png"))));
        other.setPressedIcon(new ImageIcon(ImageIO.read(getClass().getResource("/other_active.png"))));
        other.setBorder(BorderFactory.createEmptyBorder());
        other.setContentAreaFilled(false);
        other.setFocusable(false);
        other.setPreferredSize(new Dimension(310, 260));
        
        JLabel oneFourLabel = new JLabel(jezyk.getText(S1_4));
        oneFourLabel.setFont(new Font("Serif", Font.BOLD, 30));
        oneFourLabel.setForeground(Color.yellow);
        oneFourLabel.setPreferredSize(new Dimension(220, 245));
        oneFourLabel.setAlignmentY(BOTTOM_ALIGNMENT);
        oneFourLabel.setAlignmentX(CENTER_ALIGNMENT);
        oneFour.add(oneFourLabel);
        
        JLabel otherLabel = new JLabel(jezyk.getText(Sother));
        otherLabel.setFont(new Font("Serif", Font.BOLD, 30));
        otherLabel.setForeground(Color.yellow);
        otherLabel.setPreferredSize(new Dimension(220, 245));
        otherLabel.setAlignmentY(BOTTOM_ALIGNMENT);
        otherLabel.setAlignmentX(CENTER_ALIGNMENT);
        other.add(otherLabel);
        
        
        other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*try {
                    contentPane.add(new SelectCardsFrame(contentPane), Sselectcardsframe);
                } catch (IOException ex) {
                    Logger.getLogger(SelectTypeOfPack.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                SelectCardsFrame.resetLabels();
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, Sselectcardsframe);
            }
        });
        
        oneFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JSONHandling.saveOneFour();
                } catch (JSONException | IOException | ParseException ex) {
                    Logger.getLogger(SelectTypeOfPack.class.getName()).log(Level.SEVERE, null, ex);
                }
                HearthStone.frame.setTitle(jezyk.getText(Stitle));
                try {
                    SelectPackFrame.putButtons();
                } catch (IOException ex) {
                    Logger.getLogger(SelectTypeOfPack.class.getName()).log(Level.SEVERE, null, ex);
                }
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, Sselectpackframe);
            }
        });
        
        JButton back = new JButton(jezyk.getText(Sback));
        
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, Sselectpackframe);
            }
        });
        
        
        background.add(oneFour);
        background.add(other);
        background.add(back);
    }
}
