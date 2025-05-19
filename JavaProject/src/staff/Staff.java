package staff;

public class Staff {
    private String name;
    private int skillLevel;

    public Staff(String name, int skillLevel) {
        this.name = name;
        this.skillLevel = skillLevel;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public String getName() {
        return name;
    }
}


