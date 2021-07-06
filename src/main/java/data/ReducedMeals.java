package data;

public class ReducedMeals implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private Integer reducedMealsID = 3;
    private String description;

    public ReducedMeals(Integer reducedMealsID, String description) {
        setReducedMealsID(reducedMealsID);
        setDescription(description);
    }

    public ReducedMeals(String description) {
        setDescription(description);
    }

    public ReducedMeals(Integer reducedMealsID) {
        setReducedMealsID(reducedMealsID);
    }

    public Integer getReducedMealsID() {
        return this.reducedMealsID;
    }

    public void setReducedMealsID(Integer reducedMealsID) {
        this.reducedMealsID = reducedMealsID;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}