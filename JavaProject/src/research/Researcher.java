package research;

public class Researcher {
    private String name;
    private int level;

    public Researcher(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public int getRate() {
        return level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
}
