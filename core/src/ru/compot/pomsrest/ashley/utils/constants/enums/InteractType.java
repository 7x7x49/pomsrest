package ru.compot.pomsrest.ashley.utils.constants.enums;

import com.badlogic.ashley.core.Entity;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ai.GraphNode;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.screens.restaurant.RestaurantScreen;
import ru.compot.pomsrest.screens.world.WorldScreen;
import ru.compot.pomsrest.utils.PathfinderUtils;
import ru.compot.pomsrest.utils.constants.AnimationIDs;
import ru.compot.pomsrest.utils.constants.OtherConstants;

import java.util.Iterator;

// типы взаимодействий
public enum InteractType {
    WORLD_ENTER_AREA((player, collider) -> { //область входа в рестик
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player); //из плеера извлекаем инфу
        TransformComponent playerTransform = Mappers.TRANSFORM_MAPPER.get(player);
        TransformAnimationComponent playerTransformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(player);
        TextureAnimationComponent playerTextureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(player);
        TransformComponent areaTransform = Mappers.TRANSFORM_MAPPER.get(collider); //извлекаем позицию и размер
        float destX = areaTransform.x + (areaTransform.width - playerTransform.width) / 2f; //центр двери (куда должны зайти)
        playerTextureAnimation.animate( //анимируем бег <- ->
                playerTransform.x < destX ? AnimationIDs.PLAYER_RIGHT_RUNNING : AnimationIDs.PLAYER_LEFT_RUNNING, //если слева от двери то вправо если справа от двери то влево бежит
                true //бесконечная анимка
        );
        playerTransformAnimation.animate( //анимируем позицию
                TransformAnimationType.POSITION,
                OtherConstants.PLAYER_SPEED, //скорость игрока
                playerTransform.x, //начало
                playerTransform.y, //начало
                destX, //конечная (центр двери)
                playerTransform.y,
                () -> { //логика при завершении данной анимации позиции
                    playerData.moveBlocked = true; //блок передвижения)0)0, обратной дороги нема
                    playerTextureAnimation.reset(); //приостанавливаем все запущенные раннее анимки текстуры
                    playerTextureAnimation.animate(AnimationIDs.PLAYER_FORWARD_RUNNING, true); //запуск анимки входа в дверь
                    playerTransformAnimation.animate( //запуск анимации размера
                            TransformAnimationType.SCALE,
                            0.4f, //скорость
                            playerTransform.scaleX, //1
                            playerTransform.scaleY, //1
                            0.7f,
                            0.7f, //смена размера (уменьшение)
                            null
                    );
                    GameCore.INSTANCE.playerConfig.lastWorldPosition = playerTransform.x; //запись центра двери
                    playerTransformAnimation.animate( //альпака чуть-чуть смещается вверх при входе (анимация позиция)
                            TransformAnimationType.POSITION,
                            50f,
                            playerTransform.x,
                            playerTransform.y,
                            playerTransform.x,
                            playerTransform.y + 25f,
                            () -> {
                                playerTextureAnimation.reset();
                                GameCore.INSTANCE.setCurrentScreen(new RestaurantScreen()); //перемещаемся в рестик
                            }
                    );
                }
        );
        float cameraX = destX + playerTransform.originX; //смена позиции камеры
        float minCameraX = playerData.camera.viewportWidth / 2f; //возможно минимальная позиция камеры
        float maxCameraX = WorldScreen.WORLD_WIDTH - playerData.camera.viewportWidth / 2f; //возможно макси позиция камеры
        if (destX < minCameraX) cameraX = minCameraX; //если мышка ушла за границы камеры, то камеру переместим на мин позицию камеры
        else if (destX > maxCameraX) cameraX = maxCameraX;
        playerData.camera.animate(cameraX, playerData.camera.position.y); //анимируем камеру для плавного передвижения
    }),

    RECIPE_BOOK_AREA((player, collider) -> { //область взаимодействия с плитой
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player); //инфа о юзере
        TransformComponent playerTransform = Mappers.TRANSFORM_MAPPER.get(player);
        TransformAnimationComponent playerTransformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(player);
        TextureAnimationComponent playerTextureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(player);
        TransformComponent areaTransform = Mappers.TRANSFORM_MAPPER.get(collider); //инфа о плите
        RestaurantScreen screen = (RestaurantScreen) playerData.screen; //экран в котором находимся (рестик)
        Iterator<GraphNode> iterator = PathfinderUtils.findPath( //ищем путь от текущей позиции юзера до плиты (центра)
                playerTransform.x,
                playerTransform.y,
                areaTransform.x + areaTransform.originX - playerTransform.originX,
                areaTransform.y - playerTransform.originY,
                screen.getGraph()
        );
        PathfinderUtils.animatePath( //анимируем путь
                iterator,
                playerTransform,
                playerTransformAnimation,
                node -> { //логика при прохождении отрезка. путь - ломанная, когда пройдем каждый отрезок, вызовется след функция
                    if (playerTransform.x == node.getX() && playerTransform.y == node.getY()) return;
                    float xDiff = playerTransform.x - node.getX();
                    float yDiff = playerTransform.y - node.getY();
                    double angle = Math.toDegrees(Math.atan(yDiff / xDiff)); //ищем разницу между конечными точками, потом ищем отношение (потом арктангенс)
                    if (angle > 45 || angle < -45) { //сравниваем угол, альпака движется либо вперед либо назад
                        playerTextureAnimation.animate(playerTransform.y < node.getY() ? AnimationIDs.PLAYER_FORWARD_RUNNING : AnimationIDs.PLAYER_BACK_RUNNING, true); //либо верх, либо вниз
                    } else playerTextureAnimation.animate(playerTransform.x < node.getX() ? AnimationIDs.PLAYER_RIGHT_RUNNING : AnimationIDs.PLAYER_LEFT_RUNNING, true); //либо влево либо вправо (если нет)
                },
                () -> { //функция завершения
                    playerTextureAnimation.reset();
                    screen.openRecipeBook();
                    screen.getRecipeBookActor().open(); //откроем книгу рецептов
                });
    });

    private final InteractApplier action;

    InteractType(InteractApplier action) {
        this.action = action;
    }

    public void acceptAction(Entity player, Entity collider) {
        action.apply(player, collider);
    }

    @FunctionalInterface
    private interface InteractApplier {
        void apply(Entity player, Entity collider);
    }
}
