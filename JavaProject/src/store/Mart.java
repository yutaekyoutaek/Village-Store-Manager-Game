package store;

import staff.Staff;
import java.util.*;

public class Mart extends Store implements Employable {
    private List<Staff> staffList = new ArrayList<>();
    private Random rand = new Random();

    public Mart() {
        name = "마트";
        level = 1;
        base_reve = 300;
        base_cost = 100;
    }

    @Override
    public int revenue() {
        int skillBonus = staffList.stream()
                .mapToInt(s -> s.getSkillLevel() * 10)
                .sum();
        int repBonus = reputationBonus();

        int min = base_reve + level * 30 + repBonus;
        int max = base_reve + level * 60 + skillBonus + repBonus;
        return rand.nextInt(max - min + 1) + min;
    }

    @Override
    public int maintenance() {
        return base_cost + (level * 20);
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
        if (condition >= 80 && employee() >= 2 && averageSkill() >= 3) {
            reputation = Math.min(reputation + 5, maxReputation);
        }
    }

    public void hire(Staff s) {
        staffList.add(s);
    }
}

