package game;

import store.*;
import staff.Staff;
import research.*;

import java.util.*;

public class GameManager {
    private Player player;
    private Scanner scanner = new Scanner(System.in);
    private int day = 1;
    private List<Candidate> candidateList = new ArrayList<>();
    private List<ResearchCandidate> researcherList = new ArrayList<>();
    private Random rand = new Random();
    private Set<String> usedNames = new HashSet<>();

    public void startGame() {
        System.out.print("플레이어 이름을 입력하세요: ");
        String name = scanner.nextLine();
        player = new Player(name, 1000);

        System.out.println("\n처음 가게로 마트를 건설합니다.");
        Mart mart = new Mart();
        player.addStore(mart);

        while (true) {
            System.out.println("\n=== Day " + day + " ===");
            player.showStatus();

            System.out.println("\n[메뉴 선택]");
            System.out.println("1. 직원 고용");
            System.out.println("2. 가게 업그레이드");
            System.out.println("3. 하루 경영 진행");
            System.out.println("4. 연구소 관련 기능");
            System.out.println("5. 가게 건설");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                hireStaff();
            } else if (choice == 2) {
                upgradeStore();
            } else if (choice == 3) {
                runDay();
            } else if (choice == 4) {
                researchMenu();
            } else if (choice == 5) {
                buildStore();
            } else if (choice == 0) {
                System.out.println("게임을 종료합니다.");
                break;
            }
        }
    }

    private void buildStore() {
        System.out.println("\n[건설 가능한 가게 목록]");
        System.out.println("1. 마트 (항상 가능)");
        System.out.println("2. 카페 (연구 해금 필요)");
        System.out.print("건설할 가게 선택 (0: 취소): ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        String type = null;

        switch (choice) {
            case 1 -> type = "mart";
            case 2 -> type = "cafe";
            case 0 -> { return; }
            default -> {
                System.out.println("잘못된 입력입니다.");
                return;
            }
        }

        Store store = StoreFactory.createStore(type, player);
        if (store != null) {
            player.addStore(store);
            System.out.println(store.getName() + " 가게가 건설되었습니다!");
        }
    }

    private void hireStaff() {
        generateCandidates();
        System.out.println("고용 가능한 직원 목록:");

        for (int i = 0; i < candidateList.size(); i++) {
            Candidate c = candidateList.get(i);
            System.out.println((i + 1) + ". " + c.staff.getName() +
                    " (스킬: " + c.staff.getSkillLevel() + ", 비용: " + c.cost + "원)");
        }

        System.out.print("직원 선택 (0: 취소): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > candidateList.size()) return;
        Candidate selected = candidateList.get(choice - 1);

        if (player.getMoney() < selected.cost) {
            System.out.println("잔액이 부족합니다!");
            return;
        }

        List<Store> stores = player.getOwnedStores();
        for (int i = 0; i < stores.size(); i++) {
            System.out.println((i + 1) + ". " + stores.get(i).getName());
        }

        System.out.print("직원을 배치할 가게 선택: ");
        int storeIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        Store s = stores.get(storeIndex);
        if (s instanceof Mart mart) {
            mart.hire(selected.staff);
            player.pay(selected.cost);
            System.out.println(selected.staff.getName() + " 직원을 고용했습니다.");
        } else if (s instanceof Cafe cafe) {
            cafe.hire(selected.staff);
            player.pay(selected.cost);
            System.out.println(selected.staff.getName() + " 직원을 고용했습니다.");
        } else {
            System.out.println("해당 가게에 직원을 배치할 수 없습니다.");
        }
    }

    private void upgradeStore() {
        List<Store> stores = player.getOwnedStores();
        for (int i = 0; i < stores.size(); i++) {
            System.out.println((i + 1) + ". " + stores.get(i).getName());
        }
        System.out.print("업그레이드할 가게 번호: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();

        stores.get(idx).upgrade();
        System.out.println("가게가 업그레이드되었습니다.");
    }

    private void runDay() {
        player.collectRevenue();
        player.payMaintenance();
        day++;
        System.out.println("하루가 경과했습니다.");
    }

    private void generateCandidates() {
        String[] surnames = {"김", "이", "박", "최", "정", "조", "윤", "장", "임", "한", "서", "오", "신", "홍", "양", "배", "백", "허", "남", "노"};
        String[] firstParts = {"민", "지", "수", "하", "도", "예", "윤", "서", "태", "채", "현", "가", "시", "유", "세"};
        String[] secondParts = {"수", "훈", "연", "빈", "현", "우", "영", "진", "린", "성", "아", "율", "은", "온", "슬"};

        candidateList.clear();

        while (candidateList.size() < 5) {
            String fullName = surnames[rand.nextInt(surnames.length)]
                    + firstParts[rand.nextInt(firstParts.length)]
                    + secondParts[rand.nextInt(secondParts.length)];

            if (usedNames.contains(fullName)) continue;
            usedNames.add(fullName);

            int skill = rand.nextInt(5) + 1;
            int cost = 100 + (skill * 50) + rand.nextInt(101);
            candidateList.add(new Candidate(new Staff(fullName, skill), cost));
        }
    }

    private void generateResearchCandidates() {
        String[] first = { "김", "이", "박", "최", "정", "유", "윤", "홍", "임", "안" };
        String[] second = { "소연", "민재", "하은", "도윤", "서윤", "현우", "지안", "예준", "하진", "채린" };

        researcherList.clear();
        Set<String> used = new HashSet<>();

        while (researcherList.size() < 3) {
            String name = first[rand.nextInt(first.length)] + second[rand.nextInt(second.length)];
            if (used.contains(name)) continue;
            used.add(name);

            int level = rand.nextInt(3) + 1;
            int cost = 150 + (level * 70) + rand.nextInt(100);
            Researcher researcher = new Researcher(name, level);
            researcherList.add(new ResearchCandidate(researcher, cost));
        }
    }

    private void researchMenu() {
        while (true) {
            System.out.println("\n[연구소 메뉴]");
            System.out.println("1. 연구소 건설");
            System.out.println("2. 연구원 고용");
            System.out.println("3. 카페 해금 연구");
            System.out.println("4. 연구 현황 보기");
            System.out.println("0. 뒤로 가기");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                final int labCost = 500;
                if (player.hasResearchLab()) {
                    System.out.println("이미 연구소가 존재합니다.");
                } else if (player.getMoney() < labCost) {
                    System.out.println("연구소 건설 비용은 " + labCost + "원입니다. 잔액이 부족합니다.");
                } else {
                    player.pay(labCost);
                    player.buildResearchLab();
                    System.out.println("연구소가 건설되었습니다. (" + labCost + "원 차감)");
                }
            } else if (choice == 2) {
                if (!player.hasResearchLab()) {
                    System.out.println("연구소가 먼저 건설되어야 합니다.");
                    continue;
                }

                generateResearchCandidates();
                System.out.println("고용 가능한 연구원 목록:");
                for (int i = 0; i < researcherList.size(); i++) {
                    ResearchCandidate r = researcherList.get(i);
                    System.out.printf("%d. %s (레벨: %d, 비용: %d원)\n",
                            i + 1, r.researcher.getName(), r.researcher.getLevel(), r.cost);
                }

                System.out.print("연구원 선택 (0: 취소): ");
                int idx = scanner.nextInt();
                scanner.nextLine();

                if (idx < 1 || idx > researcherList.size()) continue;

                ResearchCandidate selected = researcherList.get(idx - 1);
                if (player.getMoney() < selected.cost) {
                    System.out.println("잔액이 부족합니다.");
                    continue;
                }

                player.getResearchLab().addResearcher(selected.researcher);
                player.pay(selected.cost);
                System.out.println(selected.researcher.getName() + " 연구원이 고용되었습니다.");
            } else if (choice == 3) {
                if (!player.hasResearchLab()) {
                    System.out.println("연구소가 먼저 건설되어야 합니다.");
                    continue;
                }
                if (player.getResearchLab().unlockCafe()) {
                    System.out.println("카페가 성공적으로 해금되었습니다!");
                } else {
                    System.out.println("해금 실패: 포인트 부족 또는 이미 해금됨");
                }
            } else if (choice == 4) {
                if (!player.hasResearchLab()) {
                    System.out.println("연구소가 아직 없습니다.");
                } else {
                    System.out.println("현재 연구 포인트: " + player.getResearchLab().getPoints());
                    System.out.println("카페 해금 상태: " + (player.getResearchLab().isCafeUnlocked() ? "해금됨" : "미해금"));
                    System.out.println("연구원 목록:");
                    for (Researcher r : player.getResearchLab().getResearchers()) {
                        System.out.println("- " + r.getName() + " (레벨 " + r.getLevel() + ")");
                    }
                }
            } else if (choice == 0) {
                break;
            }
        }
    }
}
