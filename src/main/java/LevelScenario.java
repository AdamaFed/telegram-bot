public class LevelScenario {


    String fantasyWorld;
    String Conflict;
    String descriptionOfOtherCharacters;

    String Options;

    String scenario;

    String consequenceOfTheDecision;

    public LevelScenario(String fantasyWorld, String conflict, String descriptionOfOtherCharacters, String options, String scenario, String consequenceOfTheDecision) {
        this.fantasyWorld = fantasyWorld;
        Conflict = conflict;
        this.descriptionOfOtherCharacters = descriptionOfOtherCharacters;
        Options = options;
        this.scenario = scenario;
        this.consequenceOfTheDecision = consequenceOfTheDecision;
    }

    public String getFantasyWorld() {
        return fantasyWorld;
    }

    public String getConflict() {
        return Conflict;
    }

    public String getDescriptionOfOtherCharacters() {
        return descriptionOfOtherCharacters;
    }

    public String getOptions() {
        return Options;
    }

    public String getScenario() {
        return scenario;
    }

    public String getConsequenceOfTheDecision() {
        return consequenceOfTheDecision;
    }
}
