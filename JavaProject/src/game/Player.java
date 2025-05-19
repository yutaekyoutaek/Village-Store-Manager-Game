package game;

import store.Store;
import research.*;
import java.util.*;

public class Player {
    private String name;
    private int money;
    private List<Store> ownedStores = new ArrayList<>();
    private ResearchLab researchLab = null;

    public Player(String name, int startingMoney) {
        this.name = name;
        this.money = startingMoney;
    }

    public void addStore(Store s) {
        ownedStores.add(s);
    }

    public void collectRevenue() {
        for (Store s : ownedStores) {
            s.increaseReputation();
            money += s.revenue();
        }
        if (researchLab != null) {
            researchLab.producePoints();
        }
    }

    public void payMaintenance() {
        for (Store s : ownedStores) {
            money -= s.maintenance();
        }
    }

    public void upgradeStore(Store s) {
        if (ownedStores.contains(s)) {
            s.upgrade();
        }
    }

    public int getMoney() {
        return money;
    }

    public void pay(int amount) {
        money -= amount;
    }
    public void pay(String reason, int amount) {
        System.out.println(reason + "으로 " + amount + "원을 지불합니다.");
        pay(amount);
    }

    public void showStatus() {
        System.out.println("플레이어 이름: " + name);
        System.out.println("보유 금액: " + money);
        System.out.println("보유 가게 수: " + ownedStores.size());
        for (int i = 0; i < ownedStores.size(); i++) {
            Store s = ownedStores.get(i);
            System.out.println((i + 1) + ". " + s.getName() + " (직원 수: " + s.employee() + ")");
        }
        if (hasResearchLab()) {
            System.out.println("연구 포인트: " + researchLab.getPoints());
            System.out.println("카페 해금 여부: " + (researchLab.isCafeUnlocked() ? "해금됨" : "미해금"));
        }
    }

    public void buildResearchLab() {
        if (researchLab == null) {
            researchLab = new ResearchLab();
            System.out.println("연구소가 건설되었습니다.");
        } else {
            System.out.println("이미 연구소가 존재합니다.");
        }
    }

    public boolean hasResearchLab() {
        return researchLab != null;
    }

    public ResearchLab getResearchLab() {
        return researchLab;
    }

    public List<Store> getOwnedStores() {
        return ownedStores;
    }
}
