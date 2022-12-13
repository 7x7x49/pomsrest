package ru.compot.pomsrest.scene2d.restaurant;

import static ru.compot.pomsrest.utils.constants.Assets.*;


// все рецепты
public class RecipesRegistry {
    public static final RecipeData BUYABES = new RecipeData(
            "Буйабес",
            RECIPE_BUYABES,
            MINI_RECIPE_BUYABES,
            new RecipeData.RecipeComponent[]{
                    new RecipeData.RecipeComponent(INGREDIENT_SHRIMP, "Креветка"),
                    new RecipeData.RecipeComponent(INGREDIENT_MUSSEL, "Мидии"),
                    new RecipeData.RecipeComponent(INGREDIENT_LEMON, "Лимон")
            });

    public static final RecipeData STRUDEL = new RecipeData(
            "Штрудель",
            RECIPE_STRUDEL,
            MINI_RECIPE_STRUDEL,
            new RecipeData.RecipeComponent[]{
                    new RecipeData.RecipeComponent(INGREDIENT_CURD, "Творог"),
                    new RecipeData.RecipeComponent(INGREDIENT_CHERRY, "Вишня"),
                    new RecipeData.RecipeComponent(INGREDIENT_ICE_CREAM, "Мороженое")
            });

    public static final RecipeData CAESAR = new RecipeData(
            "Цезарь",
            RECIPE_CAESAR,
            MINI_RECIPE_CAESAR,
            new RecipeData.RecipeComponent[]{
                    new RecipeData.RecipeComponent(INGREDIENT_CHICKEN, "Курица"),
                    new RecipeData.RecipeComponent(INGREDIENT_TOMATO, "Помидор"),
                    new RecipeData.RecipeComponent(INGREDIENT_SHRIMP, "Креветка")
            });

    public static final RecipeData TAKOYAKI = new RecipeData(
            "Такояки",
            RECIPE_TAKOYAKI,
            MINI_RECIPE_TAKOYAKI,
            new RecipeData.RecipeComponent[]{
                    new RecipeData.RecipeComponent(INGREDIENT_FLOUR, "Мука"),
                    new RecipeData.RecipeComponent(INGREDIENT_EGG, "Яйцр"),
                    new RecipeData.RecipeComponent(INGREDIENT_ONION, "Лук"),
                    new RecipeData.RecipeComponent(INGREDIENT_OCTOPUS, "Осьминог")
            });

    public static final RecipeData MULLED_WINE = new RecipeData(
            "Глинтвейн",
            RECIPE_MULLED_WINE,
            MINI_RECIPE_MULLED_WINE,
            new RecipeData.RecipeComponent[]{
                    new RecipeData.RecipeComponent(INGREDIENT_WINE, "Вино"),
                    new RecipeData.RecipeComponent(INGREDIENT_JUICE, "Сок"),
                    new RecipeData.RecipeComponent(INGREDIENT_LEMON, "Лимон")
            });

    public static final RecipeData TRIFUL = new RecipeData(
            "Трайфул",
            RECIPE_TRIFUL,
            MINI_RECIPE_TRIFUL,
            new RecipeData.RecipeComponent[]{
                    new RecipeData.RecipeComponent(INGREDIENT_BISCUIT, "Бисквит"),
                    new RecipeData.RecipeComponent(INGREDIENT_WHIPPING_CREAM, "Взбитые сливки"),
                    new RecipeData.RecipeComponent(INGREDIENT_TOPPING, "Топпинг")
            });

    public static final RecipeData LOBSTER = new RecipeData(
            "Лобстер",
            RECIPE_LOBSTER,
            MINI_RECIPE_LOBSTER,
            new RecipeData.RecipeComponent[]{
                    new RecipeData.RecipeComponent(INGREDIENT_LOBSTER, "Лобстер"),
                    new RecipeData.RecipeComponent(INGREDIENT_LEMON, "Лимон")
            });

    public static final RecipeData MACAROON = new RecipeData(
            "Макарун",
            RECIPE_MACAROON,
            MINI_RECIPE_MACAROON,
            new RecipeData.RecipeComponent[]{
                    new RecipeData.RecipeComponent(INGREDIENT_FLOUR, "Мука"),
                    new RecipeData.RecipeComponent(INGREDIENT_EGG, "Яйцо"),
                    new RecipeData.RecipeComponent(INGREDIENT_SUGAR, "Сахар")
            });

    public static final RecipeData[] RECIPES = new RecipeData[]{
            BUYABES, STRUDEL, CAESAR, TAKOYAKI, MULLED_WINE, TRIFUL, LOBSTER, MACAROON
    };
}
