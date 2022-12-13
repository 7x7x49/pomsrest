package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.compot.pomsrest.ashley.components.FoodComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;

// система взаимодействия с едой
public class FoodSystem extends IteratingSystem {

    private final Entity player;
    private final NPCSystem npcSystem;

    public FoodSystem(Entity player, NPCSystem npcSystem) {
        super(Family.all(TransformComponent.class, FoodComponent.class).get());
        this.player = player;
        this.npcSystem = npcSystem;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) { //проходимся по ентитям еды
        FoodComponent fc = Mappers.FOOD_MAPPER.get(entity); //извлекаем фуд компонент
        if (!fc.dragging) return; //если в фудкомпоненте еда не передвигается то выходим из метода
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player); //если передвигается то извлекаем плееркомпонент из плеера
        TransformComponent foodTransform = Mappers.TRANSFORM_MAPPER.get(entity); //из еды
        if (playerData.dragging) { //если чото передвигается то позицию еды обновляем на текущую позицию нажатия
            foodTransform.x = playerData.mousePoint.x;
            foodTransform.y = playerData.mousePoint.y;
        } else {
            npcSystem.setInteractFoodEntity(foodTransform, fc);
            fc.dragging = false;
        }
    }
}
