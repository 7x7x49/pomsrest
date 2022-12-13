package ru.compot.pomsrest.screens.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ashley.systems.InteractSystem;
import ru.compot.pomsrest.ashley.utils.constants.enums.InteractType;
import ru.compot.pomsrest.ashley.utils.factory.EntityFactory;
import ru.compot.pomsrest.screens.GameScreen;
import ru.compot.pomsrest.utils.Animated2DCamera;
import ru.compot.pomsrest.utils.constants.Assets;

// мир
public class WorldScreen extends GameScreen {

    public static final float WORLD_WIDTH = 3332f;
    public static final float GROUND_HEIGHT = 62f;
    public static final float CAMERA_MOVE_OFFSET = 70f;

    public WorldScreen(float playerX) {
        super(new ExtendViewport(GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT, new Animated2DCamera()));
        Entity player = EntityFactory.buildPlayerEntity(engine, playerX, GROUND_HEIGHT, this, (Animated2DCamera) viewport.getCamera());
        EntityFactory.createInteractArea(
                engine,
                200f, GROUND_HEIGHT,
                160f, 200f,
                InteractType.WORLD_ENTER_AREA
        );
        engine.addSystem(new InteractSystem(player));
        backgroundStage.addActor(new Image(Assets.<Texture>getAsset(Assets.WORLD_BACKGROUND)));
        foregroundStage.addListener(new WorldInputListener(player));
    }
}
