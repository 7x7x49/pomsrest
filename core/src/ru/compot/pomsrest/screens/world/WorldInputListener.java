package ru.compot.pomsrest.screens.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.utils.Animated2DCamera;
import ru.compot.pomsrest.utils.constants.AnimationIDs;
import ru.compot.pomsrest.utils.constants.OtherConstants;

// слушатель нажатия в мире
public class WorldInputListener extends InputListener {

    private final Entity player;

    public WorldInputListener(Entity player) {
        this.player = player;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        if (playerData.moveBlocked) return false;
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(player);
        TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(player);
        TextureAnimationComponent textureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(player);
        Animated2DCamera camera = playerData.camera;
        playerData.interact(x, y);
        byte animationId = x < transform.x + transform.originX ? AnimationIDs.PLAYER_LEFT_RUNNING : AnimationIDs.PLAYER_RIGHT_RUNNING;
        float destX = x - transform.originX;
        if (destX < WorldScreen.CAMERA_MOVE_OFFSET) destX = WorldScreen.CAMERA_MOVE_OFFSET;
        else if (destX > WorldScreen.WORLD_WIDTH - transform.width - WorldScreen.CAMERA_MOVE_OFFSET)
            destX = WorldScreen.WORLD_WIDTH - transform.width - WorldScreen.CAMERA_MOVE_OFFSET;
        textureAnimation.animate(animationId, true);
        transformAnimation.animate(
                TransformAnimationType.POSITION,
                OtherConstants.PLAYER_SPEED,
                transform.x,
                transform.y,
                destX,
                transform.y,
                textureAnimation::reset
        );
        float onCameraX = x - (camera.position.x - GameCore.CAMERA_WIDTH);
        if (onCameraX <= WorldScreen.CAMERA_MOVE_OFFSET || onCameraX >= GameCore.SCREEN_WIDTH - WorldScreen.CAMERA_MOVE_OFFSET) {
            float cameraX = x;
            float minCameraX = camera.viewportWidth / 2f;
            float maxCameraX = WorldScreen.WORLD_WIDTH - camera.viewportWidth / 2f;
            if (x < minCameraX) cameraX = minCameraX;
            else if (x > maxCameraX) cameraX = maxCameraX;
            camera.animate(cameraX, camera.position.y);
        }
        return true;
    }
}
