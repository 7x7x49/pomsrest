package ru.compot.pomsrest.scene2d.restaurant;

// инфа о рецепте
public class RecipeData {

    private final String title;
    private final String defaultRegion, miniRegion;
    private final RecipeComponent[] recipeComponents;

    public RecipeData(String title, String defaultRegion, String miniRegion, RecipeComponent[] recipeComponents) {
        this.title = title;
        this.defaultRegion = defaultRegion; //регион в атласе с большими блюдами
        this.miniRegion = miniRegion; //регион в атласе с мини блюдами
        this.recipeComponents = recipeComponents; //компоненты (состав) реца
    }

    public String getTitle() {
        return title;
    }

    public String getDefaultRegion() {
        return defaultRegion;
    }

    public String getMiniRegion() {
        return miniRegion;
    }

    public RecipeComponent[] getRecipeComponents() {
        return recipeComponents;
    }

    public static class RecipeComponent {
        public String id, displayName;

        public RecipeComponent(String id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }
    }
}
