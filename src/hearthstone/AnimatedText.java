package hearthstone;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;

public class AnimatedText extends JLabel {
    
    public void animation(Point ps, int delay) throws InterruptedException { /// wiekszy delay - wolniejsza animacja
        Thread one = new Thread() {
            public void run() {
                try {
                    for(int i=0; i<100; i++) {
                        setLocation(ps.x, ps.y-i);
                        setForeground(new Color(255,255,0,i*2+i/2));
                        Thread.sleep(delay);
                    }
                    for(int i=99; i>=0; i--) {
                        setLocation(ps.x, ps.y-i);
                        setForeground(new Color(255,255,0,i*2+i/2));
                        Thread.sleep(delay);
                    } 
                } catch(InterruptedException v) {
                     System.out.println(v);
                }
            }  
        };

        one.start();
    }

   
   public AnimatedText(String text) {
        setText(text);
        setFont(new Font("Serif", Font.BOLD, 40));
        setForeground(new Color(255,255,0,0));
    }  
  
}
