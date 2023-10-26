public class LevelScenario {

    String fantasyWorld;
    String Conflict;
    String descriptionOfOtherCharacters;

    String Options;

    String consequenceOfTheDecision;

    public LevelScenario(String fantasyWorld, String conflict, String descriptionOfOtherCharacters, String options) {
        this.fantasyWorld = fantasyWorld;
        Conflict = conflict;
        this.descriptionOfOtherCharacters = descriptionOfOtherCharacters;
        Options = options;
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

    public String getConsequenceOfTheDecision() {
        return consequenceOfTheDecision;
    }

    @Override
    public String toString() {
        return "LevelScenario{" +
                "fantasyWorld='" + fantasyWorld + '\'' +
                ", Conflict='" + Conflict + '\'' +
                ", descriptionOfOtherCharacters='" + descriptionOfOtherCharacters + '\'' +
                ", Options='" + Options + '\'' +
                ", consequenceOfTheDecision='" + consequenceOfTheDecision + '\'' +
                '}';
    }

    public String createStory (String input){
        return ChatGptService.chatGptCall("Erstelle aus den nachfolgenden Informationen ein Spielszenario mit maximal 100 Wörtern. Es sollen die Optionen genutzt werden um eine Frage an den Leser zu stellen" + input);

    }
}
