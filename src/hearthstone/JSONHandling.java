package hearthstone;

import static hearthstone.Strings.Sdateformat;
import static hearthstone.Strings.Sfilename;
import static hearthstone.Strings.Slanguage;
import static hearthstone.Strings.Ssuffix;
import static hearthstone.Strings.left_bar_titles;
import static hearthstone.Strings.top_bar_titles;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class JSONHandling {
    
    public static void makeFile(String lang) throws JSONException {
        JSONObject jo = new JSONObject();
        Map m;
        jo.put(Slanguage, lang);
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
            Logger.getLogger(PreviewFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        pw.write(jo.toString(4));
         
        pw.flush();
        pw.close();     
    }
    
    public static void saveOneFour() throws JSONException, IOException, ParseException {
        Singleton singleton = Singleton.getInstance();
        Map m = new LinkedHashMap(top_bar_titles.length);
        SimpleDateFormat ft = new SimpleDateFormat (Sdateformat);
        m.put("date", ft.format(new Date()));
        
        String text = new String(Files.readAllBytes(Paths.get(Sfilename)), StandardCharsets.UTF_8);
        JSONObject jo = new JSONObject(text); 

        JSONObject loaded = jo.getJSONObject(singleton.getPack());
        
        int commons = Integer.parseInt(loaded.get(left_bar_titles[0]).toString()) + 4;
        loaded.remove(left_bar_titles[0]);
        loaded.put(left_bar_titles[0], ""+commons);
        
        int rare = Integer.parseInt(loaded.get(left_bar_titles[2]).toString()) + 1;
        loaded.remove(left_bar_titles[2]);
        loaded.put(left_bar_titles[2], ""+rare);

        jo.remove(singleton.getPack());
        jo.put(singleton.getPack(), loaded);
        m.put(left_bar_titles[0], ""+4);
        m.put(left_bar_titles[2], ""+1);
        
        JSONObject tmp = new JSONObject(m);
        jo.accumulate(singleton.getPack()+Ssuffix, tmp);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(Sfilename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PreviewFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pw.write(jo.toString(4));
         
        pw.flush();
        pw.close();        
    }
   
}
