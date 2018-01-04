package hearthstone;

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
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectPackFrame extends JPanel {
    
    private JPanel contentPane;
    private Font font;
    private Jezyk jezyk;
    
    public final JButton makeButton(String text, String button, String rollover, String pressed, int h, int w) throws IOException {
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
                
                contentPane.add(new SelectCardsFrame(contentPane), "SelectCardsFrame");
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "SelectCardsFrame");             
            }
        });
        return tmp_button;
    }
    
    public SelectPackFrame(JPanel panel) throws FontFormatException, IOException {
        contentPane = panel;
        jezyk = new Polski();
        font = Font.createFont(Font.TRUETYPE_FONT, new File("BlizQuadrata.ttf"));
        
        setLayout(new BorderLayout());
	JLabel background=new JLabel(new ImageIcon(getClass().getResource("/background2.jpg")));
	add(background);
	
        background.setLayout(new FlowLayout(FlowLayout.CENTER,30,100));
        
        JButton classic = makeButton("classic", "Spack.png", "Spack_hover.png", "Spack_active.png", 100, 135);
        JButton cobolds = makeButton("cobolds", "Spack.png", "Spack_hover.png", "Spack_active.png", 100, 135);
        JButton knights = makeButton("knights", "Spack.png", "Spack_hover.png", "Spack_active.png", 100, 135);
        JButton ungoro = makeButton("un'goro","Spack.png", "Spack_hover.png", "Spack_active.png", 100, 135);
        JButton gadgetzan = makeButton("gadgetzan","Spack.png", "Spack_hover.png", "Spack_active.png", 100, 135);
        JButton back = new JButton(jezyk.getText("back"));
        
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.first(contentPane);
            }
        });
        
        background.add(classic);
        background.add(cobolds);
        background.add(knights);
        background.add(ungoro);
        background.add(gadgetzan);
        background.add(back);
        
        setPreferredSize(new Dimension(700,400));
    }
}
