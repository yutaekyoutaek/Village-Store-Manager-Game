package game;

import research.Researcher;

public class ResearchCandidate {
    public Researcher researcher;
    public int cost;

    public ResearchCandidate(Researcher researcher, int cost) {
        this.researcher = researcher;
        this.cost = cost;
    }
}
