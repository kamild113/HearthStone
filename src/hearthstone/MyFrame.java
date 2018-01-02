package hearthstone;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

//public class MyFrame extends JFrame {

public class MyFrame extends JPanel {
    
    String top_bar_titles[] = new String[6];
    String left_bar_titles[] = new String[8];
    
    JLabel labels[][] = new JLabel[8][6];
    private Jezyk jezyk;
    private JPanel contentPane;  
    String fileName = "settings.hs";
    
    private void makeBars() {
        JLabel tab[];
        JPanel bar;
        for(int j=0; j<8; j++) {
            tab = new JLabel[6];
            tab[0] = new JLabel(jezyk.getText(left_bar_titles[j]));
            for(int i=1; i<6; i++)
                tab[i] = new JLabel();

            bar = new JPanel();
            bar.setLayout(new GridLayout(0, 6));
            for(int i=0; i<6; i++)
                bar.add(tab[i]);
            labels[j] = tab;
            add(bar);
        }
        loadData();
    }
    
    private void parseJson(JSONObject obj) {
        for(int i=0; i<8; i++) {
            Map m = ((Map) obj.get(left_bar_titles[i]));
            for(int j=1; j<6; j++) {
                labels[i][j].setText(m.get(top_bar_titles[j]).toString());
            }
        }
    }
    
    private void loadData() {
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader(fileName));
        } catch (IOException ex) {
            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        JSONObject jo = (JSONObject) obj;
        parseJson(jo);
    }
    
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
            pw = new PrintWriter(fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        pw.write(jo.toJSONString());
         
        pw.flush();
        pw.close();        
    }
    
    private void makeFile() {
        JSONObject jo = new JSONObject();
        Map m;
        for(int i=0; i<8; i++) {
            m = new LinkedHashMap(5);
            for(int j=1; j<6; j++){
                m.put(top_bar_titles[j], "0");
            }   
            jo.put(left_bar_titles[i], m);
        }
                
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        pw.write(jo.toJSONString());
         
        pw.flush();
        pw.close();     
    }
    
    public MyFrame(JPanel panel) {
        contentPane = panel;
        setSize(700, 400);
        setLayout(new GridLayout(9,0));

        jezyk = new Polski();
        
        top_bar_titles[0] = "nothing";
        top_bar_titles[1] = "classic";
        top_bar_titles[2] = "cobolds";
        top_bar_titles[3] = "knights";
        top_bar_titles[4] = "un'goro";
        top_bar_titles[5] = "gadgetzan";
        
        left_bar_titles[0] = "common";
        left_bar_titles[1] = "gcommon";
        left_bar_titles[2] = "rare";
        left_bar_titles[3] = "grare";
        left_bar_titles[4] = "epic";
        left_bar_titles[5] = "gepic";
        left_bar_titles[6] = "legend";
        left_bar_titles[7] = "glegend";
        
        
        if(!(new File(fileName).isFile())) {
            System.out.println("Make file...");
            makeFile();
            System.out.println("File done.");
        }
        JPanel top_bar = new JPanel();
        top_bar.setLayout(new GridLayout(0, 6));
        JButton przycisk = new JButton("Cofnij");
        //JButton przycisk2 = new JButton("Cofnij");
        top_bar.add(przycisk);
        //top_bar.add(przycisk2);
        top_bar.add(new JLabel(jezyk.getText("classic")));
        top_bar.add(new JLabel(jezyk.getText("cobolds")));
        top_bar.add(new JLabel(jezyk.getText("knights")));
        top_bar.add(new JLabel(jezyk.getText("un'goro")));
        top_bar.add(new JLabel(jezyk.getText("gadgetzan")));
        add(top_bar);
        
        makeBars(); 
         
         
         przycisk.addActionListener(new ActionListener(){  
            @Override
            public void actionPerformed(ActionEvent e){
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "MainFrame");
            }
         });  
         
        
        setSize(600, 400);
        setVisible(true);
    }
}