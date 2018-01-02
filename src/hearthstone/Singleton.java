package hearthstone;


public class Singleton {
    private static final Singleton ourInstance = new Singleton();
    private String currentPack = "";

    public String getPack() { return currentPack; }
    public void setPack(String pack) { this.currentPack = pack; }


    static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() { }
}