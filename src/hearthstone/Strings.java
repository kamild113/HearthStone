package hearthstone;

import java.awt.Color;

public interface Strings {
    
    String top_bar_titles[] = {"nothing", "classic", "kobolds", "knights", "un'goro", "gadgetzan"};
    String left_bar_titles[] = {"common", "gcommon", "rare", "grare", "epic", "gepic", "legend", "glegend"};
    String Sback = "back";
    String Sadd = "add";
    String Smainframe = "MainFrame";
    String Sselectpackframe = "SelectPackFrame";
    String Sselecttypeofpack = "SelectTypeOfPack";
    String Sfont = "BlizQuadrata.ttf";
    String Sopen = "open";
    String Sview = "view";
    String Spreviewframe = "PreviewFrame";
    String Sselectcardsframe = "SelectCardsFrame";
    String Stitle = "HearthStone Packs Counter";
    String Sfilename = "settings.hs";
    String Ssuffix = "_s";
    String Sdateformat = "yyyy-MM-dd HH:mm:ss:SS";
    String Spackhistory = "PackHistory";
    String pack_history[] = {"PacksCount", "PacksFromLegend", "DaysFromLegend"};
    String Sselectlanguage = "Select your language:";
    String Slanguage = "Language";
    String S1_4 = "1+4";
    String Sother = "other";
    Jezyk languages[] = {new Polski(), new English()};
    Color colors[] = {Color.WHITE, Color.WHITE, Color.BLUE, Color.BLUE, Color.MAGENTA, Color.MAGENTA,
                        Color.YELLOW, Color.YELLOW};
}
