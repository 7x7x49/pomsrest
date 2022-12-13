package ru.compot.pomsrest;

import com.badlogic.gdx.assets.AssetManager;
import ru.compot.pomsrest.ashley.config.PlayerConfig;
import ru.compot.pomsrest.screens.GameScreen;
import ru.compot.pomsrest.utils.FreeTypeSkinLoader;

import static ru.compot.pomsrest.utils.constants.Assets.*;

public class GameCore { //это всё проста загрузка и константы

    public static final GameCore INSTANCE = new GameCore();
    public static final float SCREEN_WIDTH = 350f;
    public static final float SCREEN_HEIGHT = 700f;
    public static final float CAMERA_WIDTH = SCREEN_WIDTH / 2f;

    public final PlayerConfig playerConfig = new PlayerConfig(); //ласт ворлд позишн
    private final AssetManager assetManager = new AssetManager();

    private GameCore() {
    }

    public void init() {
        assetManager.setLoader(SKIN, new FreeTypeSkinLoader(assetManager.getFileHandleResolver()));
        assetManager.load(WORLD_BACKGROUND, TEXTURE);
        assetManager.load(RESTAURANT_ATLAS, TEXTURE_ATLAS);
        assetManager.load(PLAYER_LLAMA, TEXTURE_ATLAS);
        assetManager.load(RECIPE_BOOK_BACKGROUND, TEXTURE);
        assetManager.load(RECIPES_ATLAS, TEXTURE_ATLAS);
        assetManager.load(UI_SKIN, SKIN);
        assetManager.load(MINIGAME_ATLAS, TEXTURE_ATLAS);
        assetManager.load(MINIGAME_BACKGROUND, TEXTURE);
        assetManager.load(NPC_ATLAS, TEXTURE_ATLAS);
        assetManager.load(RECIPE_BOOK_EXIT, TEXTURE);
        assetManager.load(MINI_RECIPES_ATLAS, TEXTURE_ATLAS);
        assetManager.finishLoading();
    }

    public <T> T getAsset(String name) {
        return assetManager.get(name);
    }

    public void setCurrentScreen(GameScreen currentScreen) {
        Application.INSTANCE.setScreenWithTransition(currentScreen);
    }

    public void dispose() {
        assetManager.dispose();
    }
}
