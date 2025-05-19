package store;

public abstract class Store {
    protected int level;
    protected int base_reve; // 기본 수익
    protected int base_cost; // 기본 유지비
    protected String name;
    protected int reputation = 0; // 인지도
    protected int maxReputation = 100;
    protected int condition = 100; // 건물 상태

    public abstract int revenue(); // 수익 계산
    public abstract int maintenance(); // 유지비 계산
    public abstract void upgrade(); // 업그레이드 처리
    public abstract int employee(); // 직원 수 반환
    public abstract int averageSkill(); // 평균 스킬
    public abstract void increaseReputation(); // 인지도 상승 처리

    public String getName() { return name; }
    public int reputationBonus() {
        return (int)(reputation * 0.5); // 인지도 100 → +50 보너스
    }
}


