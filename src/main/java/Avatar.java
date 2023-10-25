public class Avatar {
    private String name;
    private String description;
    private String abilities;

    public Avatar(String name, String description, String abilities) {
        this.name = name;
        this.description = description;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAbilities() {
        return abilities;
    }
}
