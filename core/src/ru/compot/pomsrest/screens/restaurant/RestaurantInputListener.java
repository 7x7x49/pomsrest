package ru.compot.pomsrest.screens.restaurant;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ai.GraphImpl;
import ru.compot.pomsrest.ai.GraphNode;
import ru.compot.pomsrest.ashley.components.FoodComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.scene2d.restaurant.recipebook.RecipeBookActor;
import ru.compot.pomsrest.utils.Animated2DCamera;
import ru.compot.pomsrest.utils.PathfinderUtils;
import ru.compot.pomsrest.utils.constants.AnimationIDs;

import java.util.Iterator;

// слушатель нажатий в ресторане
public class RestaurantInputListener extends InputListener {

    private final Entity player;
    private final GraphImpl graph;
    private final Engine engine;

    public RestaurantInputListener(Entity player, GraphImpl graph, Engine engine) {
        this.player = player;
        this.graph = graph;
        this.engine = engine;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        if (playerData.moveBlocked) return false; //если тру не обрабатываем событие нажатия
        RestaurantScreen screen = (RestaurantScreen) playerData.screen;
        Animated2DCamera camera = playerData.camera;
        RecipeBookActor recipeBook = screen.getRecipeBookActor();
        if (recipeBook.isOpened()) return false; //если открыта то не обрабатываем тож

        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(player);
        TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(player);
        playerData.interact(x, y); //чекаем области взаимодействмя в точке нажатия

        float onCameraX = x - (camera.position.x - GameCore.CAMERA_WIDTH);
        if (onCameraX <= 20f || onCameraX >= GameCore.SCREEN_WIDTH - 20f) { //если нажатая кнопка х относительно камеры меньше чем 20 или больше чем размер экрана-20 то
            //анимируем камеру за игроком
            float cameraX = x;
            float minCameraX = camera.viewportWidth / 2f;
            float maxCameraX = screen.getRestaurantWidth() - camera.viewportWidth / 2f;
            if (x < minCameraX) cameraX = minCameraX;
            else if (x > maxCameraX) cameraX = maxCameraX;
            camera.animate(cameraX, camera.position.y);
        }
        Iterator<GraphNode> nodes = PathfinderUtils.findPath(transform.x, transform.y, x, y, graph); //ищем короткий путь до места нажатия
        if (nodes == null || !nodes.hasNext()) return true; //если пути нет то ход пропущен
        TransformComponent playerTransform = Mappers.TRANSFORM_MAPPER.get(player);
        TextureAnimationComponent playerTextureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(player);
        PathfinderUtils.animatePath( //анимируем путь
                nodes,
                transform,
                transformAnimation,
                node -> {
                    if (playerTransform.x == node.getX() && playerTransform.y == node.getY()) return;
                    float xDiff = playerTransform.x - node.getX();
                    float yDiff = playerTransform.y - node.getY();
                    double angle = Math.toDegrees(Math.atan(yDiff / xDiff));
                    if (angle > 45 || angle < -45) {
                        playerTextureAnimation.animate(playerTransform.y < node.getY() ? AnimationIDs.PLAYER_FORWARD_RUNNING : AnimationIDs.PLAYER_BACK_RUNNING, true);
                    } else playerTextureAnimation.animate(playerTransform.x < node.getX() ? AnimationIDs.PLAYER_RIGHT_RUNNING : AnimationIDs.PLAYER_LEFT_RUNNING, true);
                },
                playerTextureAnimation::reset);
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        playerData.dragging = false;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) { //зажимание и тянучка еды
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        playerData.mousePoint.set(x, y); //переход в текущее положение мыши
        playerData.dragging = true;
        FoodComponent foodToDrag = null;
        ImmutableArray<Entity> food = engine.getEntitiesFor(Family.all(FoodComponent.class).get()); //массив всех ентити еды
        for (int i = 0; i < food.size(); i++) { //перебираемся
            Entity f = food.get(i);
            FoodComponent fc = Mappers.FOOD_MAPPER.get(f);
            if (fc.dragging) return; //если они уже двигаются то выходим из метода
            TransformComponent ftc = Mappers.TRANSFORM_MAPPER.get(f); //если нет то проверяем если мышка находится внутри границ еды то
            if (ftc.x <= x && ftc.y <= y && ftc.x + ftc.width >= x && ftc.y + ftc.height >= y)
                foodToDrag = fc; //записываем переменную
        }
        if (foodToDrag == null) return; //нажал в пустоту
        foodToDrag.dragging = true;
    }
}
