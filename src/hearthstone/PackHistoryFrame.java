package hearthstone;

import static hearthstone.Strings.Sdateformat;
import static hearthstone.Strings.Sfilename;
import static hearthstone.Strings.top_bar_titles;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class PackHistoryFrame extends JPanel implements Strings { 
    long packsTab[] = new long[pack_history.length];
    
    private long getDaysBetween(Date dateStart, Date dateEnd) {
        return  Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
    }
    
    private void loadData(String pack) throws JSONException, IOException, ParseException {
        SimpleDateFormat ft = new SimpleDateFormat (Sdateformat);
        packsTab[2] = Long.MAX_VALUE;
        
        String text = new String(Files.readAllBytes(Paths.get(Sfilename)), StandardCharsets.UTF_8);
        JSONObject jo = new JSONObject(text); 
        JSONArray tab = jo.getJSONArray(pack+Ssuffix);
        packsTab[0]= tab.length();
        packsTab[1] = tab.length();
        for(int i=tab.length()-1; i>=0; i--) {
            JSONObject json = tab.getJSONObject(i);
            if(json.has(left_bar_titles[6]) || json.has(left_bar_titles[7])) {
                packsTab[1] = tab.length()-i;
                break;
            }
        }
        for(int i=tab.length()-1; i>=0; i--) {
            JSONObject json = tab.getJSONObject(i);
            if(json.has(left_bar_titles[6]) || json.has(left_bar_titles[7])) {
                Date dateBefore = ft.parse(json.getString("date"));
                Date dateAfter = ft.parse(new Date().toLocaleString());
                long daysBetween = getDaysBetween(dateBefore, dateAfter);
                if(daysBetween < packsTab[2])
                    packsTab[2] = daysBetween;
            }
        }
        if(packsTab[2] == Long.MAX_VALUE)
            packsTab[2] = 0;
    }
    
    public PackHistoryFrame(String pack) throws ParseException {
        Jezyk jezyk = new Polski();
        try {
            loadData(pack);
        } catch (JSONException ex) {
            Logger.getLogger(PackHistoryFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PackHistoryFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setLayout(new BorderLayout());
	JLabel background=new JLabel(new ImageIcon(getClass().getResource("/background2.jpg")));
        background.setLayout(new GridLayout(3,1));
        add(background);

        
        for(int i=0; i<pack_history.length; i++) {
            JPanel panel = new JPanel();
            panel.setOpaque(false);
            panel.setLayout(new GridLayout(1,3));
            JLabel Lpacks = new JLabel("<html><body align=center style='width: 100px'><b>"+jezyk.getText(pack_history[i]));
            
            Lpacks.setForeground(Color.yellow);
            Lpacks.setHorizontalAlignment(JLabel.CENTER);
            JLabel kreska = new JLabel("----");
            kreska.setForeground(Color.yellow);
            kreska.setHorizontalAlignment(JLabel.CENTER);
            JLabel LpacksCount = new JLabel(packsTab[i]+"");
            LpacksCount.setForeground(Color.yellow);
            LpacksCount.setHorizontalAlignment(JLabel.CENTER);
            panel.add(Lpacks);
            panel.add(kreska);
            panel.add(LpacksCount);

            background.add(panel);
        }
    
        /*
        JLabel LpacksFromLegend = new JLabel(jezyk.getText(Spacksfromlegend));
        JLabel LdaysFromLegend = new JLabel(jezyk.getText(Sdaysfromlegend));
        LpacksFromLegend.setForeground(Color.yellow);
        LdaysFromLegend.setForeground(Color.yellow);
        panel.add(LpacksFromLegend);
        panel.add(LdaysFromLegend);*/
//JLabel LpacksCount = new JLabel(""+packsCount);
        
    }
}
