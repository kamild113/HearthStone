package hearthstone;

import java.util.HashMap;
import java.util.Map;

public class Polski implements Jezyk, Strings {
    private Map<String, String> mapa = new HashMap<>();
    
    public Polski() {
        // MENU //
        mapa.put(Sopen, "Otwórz");
        mapa.put(Sview, "Podgląd");
        mapa.put(Sback, "Powrót");
        mapa.put(Sadd, "Dodaj");
        mapa.put(Sadded, "Dodano");
        // MENU//
        
        // RZADKOŚĆ //
        mapa.put("common", "Zwykłe");
        mapa.put("gcommon", "Zwykłe złote");
        mapa.put("rare", "Rzadkie");
        mapa.put("grare", "Rzadkie złote");
        mapa.put("epic", "Epickie");
        mapa.put("gepic", "Epickie złote");
        mapa.put("legend", "Legendarne");
        mapa.put("glegend", "Legendarne złote");
        // RZADKOŚĆ //
        
        // PAKIETY //
        mapa.put("classic", "Klasyczny");
        mapa.put("kobolds", "Koboldy");
        mapa.put("knights", "Rycerze");
        mapa.put("un'goro", "Un'goro");
        mapa.put("gadgetzan", "Gadżeton");
        // PAKIETY //
        
        // INNE //
        mapa.put("PacksCount", "Otworzone pakiety");
        mapa.put("PacksFromLegend", "Pakietów od ostatniej legendy");
        mapa.put("DaysFromLegend", "Dni od ostatniej legendy");
        mapa.put(S1_4, "Rzadka + 4");
        mapa.put(Sother, "Inny");
        mapa.put(Stitle, "HearthStone - licznik pakietów");
        mapa.put(Spackhistory, "Hisoria pakietów");
        // INNE //
        
    }
    
    @Override
    public String getText(String text) {
        if(mapa.containsKey(text)) return mapa.get(text);
        else return text;
    }
}
