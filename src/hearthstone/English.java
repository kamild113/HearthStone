package hearthstone;

import java.util.HashMap;
import java.util.Map;

public class English implements Jezyk {
    private Map<String, String> mapa = new HashMap<>();
    
    public English() {
        // MENU //
        mapa.put("open", "Open");
        mapa.put("view", "Preview");
        // MENU//
        
        // PAKIETY //
        mapa.put("common", "Common");
        mapa.put("gcommon", "Gold common");
        mapa.put("rare", "Rare");
        mapa.put("grare", "Gold rare");
        mapa.put("epic", "Epic");
        mapa.put("gepic", "Gold epic");
        mapa.put("legend", "Legend");
        mapa.put("glegend", "Gold legend");
        // PAKIETY //
        
    }
    
    @Override
    public String getText(String text) {
        if(mapa.containsKey(text)) return mapa.get(text);
        else return text;
    }
}
