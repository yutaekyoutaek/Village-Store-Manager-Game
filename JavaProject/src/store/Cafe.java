package store;

import staff.Staff;
import java.util.*;

public class Cafe extends Store {
    private List<Staff> staffList = new ArrayList<>();
    private Random rand = new Random();

    public Cafe() {
        name = "카페";
        level = 1;
        base_reve = 250;
        base_cost = 120;
    }

    @Override
    public int revenue() {
        int skillBonus = staffList.stream()
                .mapToInt(s -> s.getSkillLevel() * 12)
                .sum();
        int repBonus = reputationBonus();

        int min = base_reve + level * 20 + repBonus;
        int max = base_reve + level * 50 + skillBonus + repBonus;
        return rand.nextInt(max - min + 1) + min;
    }

    @Override
    public int maintenance() {
        return base_cost + (level * 25);
    }

    @Override
    public void upgrade() {
        level += 1;
    }

    @Override
    public int employee() {
        return staffList.size();
    }

    @Override
    public int averageSkill() {
        if (staffList.isEmpty()) return 0;
        return (int) staffList.stream()
                .mapToInt(Staff::getSkillLevel)
                .average()
                .orElse(0);
    }

    @Override
    public void increaseReputation() {
        if (condition >= 70 && employee() >= 1 && averageSkill() >= 2) {
            reputation = Math.min(reputation + 4, maxReputation);
        }
    }

    public void hire(Staff s) {
        staffList.add(s);
    }
}

