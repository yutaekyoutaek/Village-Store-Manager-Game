package research;

import java.util.*;

public class ResearchLab {
    private int researchPoints = 0;
    private boolean cafeUnlocked = false;
    private List<Researcher> researchers = new ArrayList<>();

    public void addResearcher(Researcher r) {
        researchers.add(r);
    }

    public void producePoints() {
        for (Researcher r : researchers) {
            researchPoints += r.getRate();
        }
    }

    public boolean unlockCafe() {
        if (!cafeUnlocked && researchPoints >= 10) {
            researchPoints -= 10;
            cafeUnlocked = true;
            return true;
        }
        return false;
    }

    public boolean isCafeUnlocked() {
        return cafeUnlocked;
    }

    public int getPoints() {
        return researchPoints;
    }

    public List<Researcher> getResearchers() {
        return researchers;
    }
}
