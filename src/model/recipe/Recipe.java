package model.recipe;

import java.util.ArrayList;

/**
 * Represents a full recipe with lists of ingredients and instructions.
 * @Author Ludvig Wedin Pettersson
 * @Version 3.0
 */
public class Recipe {
    private String name;
    private ArrayList<RecipeIngredient> ingredients;
    private ArrayList<String> instructions;

    /**
     * Default constructor used when recreating a recipe from firebase.
     */
    public Recipe(){}

    /**
     * Constructor used when creating a new recipe.
     * @param name Name of recipe.
     * @param ingredients List of RecipeIngredient.
     * @param instructions String list with instructions for recipe.
     */
    public Recipe(String name, ArrayList<RecipeIngredient> ingredients, ArrayList<String> instructions){
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public String toString(){
        return name;
    }


}
