package hearthstone;


public class Singleton {
    private static final Singleton ourInstance = new Singleton();
    private String currentPack = "";
    private String fileName = "settings.hs";

    public String getPack() { return currentPack; }
    public void setPack(String pack) { this.currentPack = pack; }
    
    public String getFileName() { return fileName; }


    static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() { }
}