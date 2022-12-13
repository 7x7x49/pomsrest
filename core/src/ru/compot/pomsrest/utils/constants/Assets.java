package ru.compot.pomsrest.utils.constants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ru.compot.pomsrest.GameCore;

// все названия текстур
public class Assets {

    public static final float PLAYER_FRAME_DURATION = .3f;
    public static final String UI_SKIN = "skins/ui.json";
    public static final String WORLD_BACKGROUND = "textures/world/background.png";
    public static final String RECIPE_BOOK_BACKGROUND = "textures/restaurant/recipe-book/background.png";
    public static final String MINIGAME_BACKGROUND = "textures/minigame/background.png";

    public static final String PLAYER_LLAMA = "textures/player-llama/player-llama.atlas";
    public static final String RIGHT_RUNNING_FOLDER = "right-running";
    public static final String RIGHT_IDLE_REGION = "right-running/0";
    public static final String LEFT_RUNNING_FOLDER = "left-running";
    public static final String LEFT_IDLE_REGION = "left-running/0";
    public static final String FORWARD_RUNNING = "forward";
    public static final String FORWARD_IDLE_REGION = "forward/0";
    public static final String BACK_RUNNING = "back";
    public static final String BACK_IDLE_REGION = "back/0";

    public static final String RESTAURANT_ATLAS = "textures/restaurant/restaurant.atlas";
    public static final String RESTAURANT_SINK = "sink";
    public static final String RESTAURANT_CUPBOARD = "cupboard";
    public static final String RESTAURANT_STOVE = "stove";
    public static final String RESTAURANT_FRIDGE = "fridge";
    public static final String RESTAURANT_STAIR = "stair";
    public static final String RESTAURANT_CHAIR = "chair";
    public static final String RESTAURANT_POTS = "flowerpots";
    public static final String RESTAURANT_TABLE = "table";
    public static final String RESTAURANT_FLOWER_FOLDER = "chair-flowers/";
    public static final String RESTAURANT_LEFT_LAMP = "lamps/left";
    public static final String RESTAURANT_RIGHT_LAMP = "lamps/right";
    public static final String RESTAURANT_LIGHT_CELL = "cells/light";
    public static final String RESTAURANT_DARK_CELL = "cells/dark";
    public static final String RESTAURANT_KITCHEN_WALL_CELL = "kitchen-wall";
    public static final String RESTAURANT_WALL_CELL = "wall";
    public static final String RESTAURANT_LEFT_BAR = "bars/left";
    public static final String RESTAURANT_RIGHT_BAR = "bars/right";
    public static final String RESTAURANT_CLOUD = "cloud";

    public static final String RECIPE_BOOK_EXIT = "textures/restaurant/recipe-book/exit.png";

    public static final String RECIPES_ATLAS = "textures/restaurant/recipe-book/recipes/recipes.atlas";
    public static final String RECIPE_BUYABES = "buyabes";
    public static final String RECIPE_STRUDEL = "shtrudel";
    public static final String RECIPE_CAESAR = "caesar";
    public static final String RECIPE_TAKOYAKI = "takoyaki";
    public static final String RECIPE_MACAROON = "macaroon";
    public static final String RECIPE_LOBSTER = "lobster";
    public static final String RECIPE_MULLED_WINE = "mulled-wine";
    public static final String RECIPE_TRIFUL = "triful";

    public static final String MINI_RECIPES_ATLAS = "textures/restaurant/recipe-book/mini-recipes/mini-recipes.atlas";
    public static final String MINI_RECIPE_BUYABES = "buyabes";
    public static final String MINI_RECIPE_STRUDEL = "strudel";
    public static final String MINI_RECIPE_CAESAR = "caesar";
    public static final String MINI_RECIPE_TAKOYAKI = "takoyaki";
    public static final String MINI_RECIPE_MACAROON = "macaroon";
    public static final String MINI_RECIPE_LOBSTER = "lobster";
    public static final String MINI_RECIPE_MULLED_WINE = "mulled-wine";
    public static final String MINI_RECIPE_TRIFUL = "triful";

    public static final String MINIGAME_ATLAS = "textures/minigame/minigame.atlas";
    public static final String MINIGAME_EXIT = "exit";
    public static final String NORMAL_FOOD = "normal-food";
    public static final String SHIT_FOOD = "shit-food";
    public static final String INGREDIENT_ICE_CREAM = "ingredients/ice-cream";
    public static final String INGREDIENT_TOMATO = "ingredients/tomato";
    public static final String INGREDIENT_WINE = "ingredients/wine";
    public static final String INGREDIENT_OCTOPUS = "ingredients/octopus";
    public static final String INGREDIENT_WHIPPING_CREAM = "ingredients/whipping-cream";
    public static final String INGREDIENT_EGG = "ingredients/egg";
    public static final String INGREDIENT_LEMON = "ingredients/lemon";
    public static final String INGREDIENT_BISCUIT = "ingredients/biscuit";
    public static final String INGREDIENT_CHICKEN = "ingredients/chicken";
    public static final String INGREDIENT_CURD = "ingredients/curd";
    public static final String INGREDIENT_LOBSTER = "ingredients/lobster-mini";
    public static final String INGREDIENT_CHERRY = "ingredients/cherry";
    public static final String INGREDIENT_TOPPING = "ingredients/topping";
    public static final String INGREDIENT_FLOUR = "ingredients/flour";
    public static final String INGREDIENT_MUSSEL = "ingredients/mussel";
    public static final String INGREDIENT_CRACKERS = "ingredients/crackers";
    public static final String INGREDIENT_SPICES = "ingredients/spices";
    public static final String INGREDIENT_JUICE = "ingredients/juice";
    public static final String INGREDIENT_SHRIMP = "ingredients/shrimp";
    public static final String INGREDIENT_ONION = "ingredients/onion";
    public static final String INGREDIENT_PUFF_PASTRY = "ingredients/puff-pastry";
    public static final String INGREDIENT_SUGAR = "ingredients/sugar";

    public static final String NPC_ATLAS = "textures/npc-llama/npc-llama.atlas";
    public static final String AGENT_RUNNING_FOLDER = "agent/running";
    public static final String AGENT_SIT_REGION = "agent/sit";
    public static final String BLUE_RUNNING_FOLDER = "blue/running";
    public static final String BLUE_SIT_REGION = "blue/sit";
    public static final String COSMIC_BLUE_RUNNING_FOLDER = "cosmic-blue/running";
    public static final String COSMIC_BLUE_SIT_REGION = "cosmic-blue/sit";
    public static final String GREEN_RUNNING_FOLDER = "green/running";
    public static final String GREEN_SIT_REGION = "green/sit";
    public static final String LIGHT_PURPLE_RUNNING_FOLDER = "light-purple/running";
    public static final String LIGHT_PURPLE_SIT_REGION = "light-purple/sit";
    public static final String MAUVE_RUNNING_FOLDER = "mauve/running";
    public static final String MAUVE_SIT_REGION = "mauve/sit";
    public static final String PINK_RUNNING_FOLDER = "pink/running";
    public static final String PINK_SIT_REGION = "pink/sit";
    public static final String PURPLE_RUNNING_FOLDER = "purple/running";
    public static final String PURPLE_SIT_REGION = "purple/sit";
    public static final String RED_RUNNING_FOLDER = "red/running";
    public static final String RED_SIT_REGION = "red/sit";
    public static final String TIGER_RUNNING_FOLDER = "tiger/running";
    public static final String TIGER_SIT_REGION = "tiger/sit";
    public static final String WHITE_RUNNING_FOLDER = "white/running";
    public static final String WHITE_SIT_REGION = "white/sit";
    public static final String YELLOW_RUNNING_FOLDER = "yellow/running";
    public static final String YELLOW_SIT_REGION = "yellow/sit";

    public static final Class<Texture> TEXTURE = Texture.class;
    public static final Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;
    public static final Class<Skin> SKIN = Skin.class;

    private Assets() {
    }

    public static <T> T getAsset(String name) {
        return GameCore.INSTANCE.getAsset(name);
    }
}
