package data;

public class Allergy {
    private final String allergy;
    private final String reaction;

    public Allergy(String allergy, String reaction) {
        this.allergy = allergy;
        this.reaction = reaction;
    }


    public String getAllergy() {
        return allergy;
    }

    public String getReaction() {
        return reaction;
    }
}
