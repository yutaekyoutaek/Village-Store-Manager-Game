package store;

import game.Player;
import research.ResearchLab;

public class StoreFactory {
    public static Store createStore(String type, Player player) {
        switch (type.toLowerCase()) {
            case "mart":
                return new Mart();
            case "cafe":
                if (player.hasResearchLab() && player.getResearchLab().isCafeUnlocked()) {
                    return new Cafe();
                } else {
                    System.out.println("[경고] 카페는 아직 해금되지 않았습니다.");
                    return null;
                }
            default:
                System.out.println("[오류] 알 수 없는 가게 타입입니다: " + type);
                return null;
        }
    }
}

