package hearthstone;

import java.util.HashMap;
import java.util.Map;

public class Polski implements Jezyk, Strings {
    private Map<String, String> mapa = new HashMap<>();
    
    public Polski() {
        // MENU //
        mapa.put("open", "Otwórz");
        mapa.put("view", "Podgląd");
        mapa.put("back", "Powrót");
        mapa.put("add", "Dodaj");
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
        mapa.put("cobolds", "Koboldy");
        mapa.put("knights", "Rycerze");
        mapa.put("un'goro", "Un'goro");
        mapa.put("gadgetzan", "Gadżeton");
        // PAKIETY //
        
        // INNE //
        mapa.put("PacksCount", "Otworzone pakiety");
        mapa.put("PacksFromLegend", "Pakietów od ostatniej legendy");
        mapa.put("DaysFromLegend", "Dni od ostatniej legendy");
        
        // INNE //
        
    }
    
    @Override
    public String getText(String text) {
        if(mapa.containsKey(text)) return mapa.get(text);
        else return text;
    }
}
