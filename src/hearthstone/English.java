package hearthstone;

import static hearthstone.Strings.Sadd;
import static hearthstone.Strings.Sback;
import static hearthstone.Strings.Sopen;
import static hearthstone.Strings.Sview;
import java.util.HashMap;
import java.util.Map;

public class English implements Jezyk, Strings {
    private Map<String, String> mapa = new HashMap<>();
    
    public English() {
        // MENU //
        mapa.put(Sopen, "Open");
        mapa.put(Sview, "Preview");
        mapa.put(Sback, "Back");
        mapa.put(Sadd, "Add");
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
        
        // PAKIETY //
        mapa.put("classic", "Classic");
        mapa.put("kobolds", "Kobolds");
        mapa.put("knights", "Knights");
        mapa.put("un'goro", "Un'goro");
        mapa.put("gadgetzan", "Gadgetzan");
        // PAKIETY //
        
        // INNE //
        mapa.put("PacksCount", "Packs opened");
        mapa.put("PacksFromLegend", "Packs from the last legend");
        mapa.put("DaysFromLegend", "Days from the last legend");
        mapa.put(S1_4, "1+4");
        mapa.put(Sother, "Other");
        mapa.put(Stitle, "Hearthstone - packs counter");
        mapa.put(Spackhistory, "Pack history");
        // INNE //
        
    }
    
    @Override
    public String getText(String text) {
        if(mapa.containsKey(text)) return mapa.get(text);
        else return text;
    }
}
