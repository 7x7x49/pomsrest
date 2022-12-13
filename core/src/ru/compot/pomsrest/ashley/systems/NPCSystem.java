package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import ru.compot.pomsrest.ai.GraphImpl;
import ru.compot.pomsrest.ai.GraphNode;
import ru.compot.pomsrest.ashley.components.FoodComponent;
import ru.compot.pomsrest.ashley.components.NPCComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.ashley.utils.factory.EntityFactory;
import ru.compot.pomsrest.scene2d.restaurant.RecipesRegistry;
import ru.compot.pomsrest.utils.PathfinderUtils;
import ru.compot.pomsrest.utils.constants.AnimationIDs;

import java.util.Iterator;

// система нпс
public class NPCSystem extends IteratingSystem {

    public static final long SPAWN_RATE = 20000L;
    public static final long EAT_TIME = 3000L;

    private final GraphImpl graph;
    private final boolean[] tableCellsAccess; //массив буллинов (4 эл-та, если эл-т = тру, значит ячейка занята (всо это на столе)

    private TransformComponent interactTransform;
    private FoodComponent interactFood;

    public NPCSystem(GraphImpl graph, boolean[] tableCellsAccess) {
        super(Family.all(TransformComponent.class, TransformAnimationComponent.class, NPCComponent.class).get());
        this.graph = graph;
        this.tableCellsAccess = tableCellsAccess;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        NPCComponent npc = Mappers.NPC_MAPPER.get(entity);
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(entity);
        TextureComponent texture = Mappers.TEXTURE_MAPPER.get(entity);
        if (npc.startTimeEat > -1L && System.currentTimeMillis() - npc.startTimeEat >= EAT_TIME) { //если нпс ест + время с начала поедания прошло больше или = то
            texture.visible = false; //скрываем нпс
            npc.spawned = false; //скрываем нпс
            npc.lastTimeSpawn = System.currentTimeMillis(); //текущее время, последний его жизненный цикл прошёл в данный момент
            npc.startTimeEat = -1L;
            return;
        }
        if (interactFood != null) { //если еда существует которую пользователь перенёс
            if (interactTransform.x >= transform.x && interactTransform.y >= transform.y && interactTransform.x <= transform.x + transform.width && interactTransform.y <= transform.y + transform.height) {
              //если позиция еды входит в границы нпс
                if (npc.food == interactFood.recipe) { //приэтом еда = нпс == еде которую перенесли на него
                    tableCellsAccess[interactFood.position] = false; //еду отдали, => освобождаем ячейку
                    ImmutableArray<Entity> foods = getEngine().getEntitiesFor(Family.all(FoodComponent.class).get()); //получаем всю еду (ентити), которая существует
                    for (int i = 0; i < foods.size(); i++) { //проходим по всей еде
                        Entity f = foods.get(i);
                        if (Mappers.FOOD_MAPPER.get(foods.get(i)).position == interactFood.position) //получаем позицию еды, сравниваем с позицией отданной еды
                            getEngine().removeEntity(f); //если позиции совпадают то удаляем из списка сущностей
                    }
                    npc.startTimeEat = System.currentTimeMillis(); //начало времени жрачки
                    getEngine().removeEntity(Mappers.CLOUD_MAPPER.get(npc.cloud).foodEntity); //киллим облачко
                    getEngine().removeEntity(npc.cloud); //киллим облачко
                }
                interactTransform.x = interactFood.positionX; //возвращаем еду на стол
                interactTransform.y = interactFood.positionY;
                interactTransform = null; //обнуляем потому что обработали перенос еды
                interactFood = null;
            }
        }
        if (System.currentTimeMillis() - npc.lastTimeSpawn <= SPAWN_RATE) { //чекаем, если прошло меньше чем спавнрейт (через сколько после ухода из ресторана нпс заспавнится)
            texture.visible = false; //скрываем текстуру
            return;
        }
        if (npc.spawned) return; //если нпс заспавнился, то пропускаем дальнейшие действия
        TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(entity);
        TextureAnimationComponent textureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(entity);
        texture.visible = npc.spawned = true;
        npc.food = RecipesRegistry.RECIPES[(int) Math.round((RecipesRegistry.RECIPES.length - 1) * Math.random())]; //определяем рандомно желаемую еду (берём длину списка с едой * на мафрандом
        EntityFactory.createCloudEntity(getEngine(), entity); //спавним облачко
        Iterator<GraphNode> nodes = PathfinderUtils.findPath(0f, 40f, npc.targetX, npc.targetY, graph); //ищем наикротчайший путь до дивана от входа рестика
        if (nodes != null && nodes.hasNext()) { //если путь найден, то
            textureAnimation.animate(AnimationIDs.NPC_RUNNING, true); //запускаем анимку бега
            transform.x = 0f; //позиция входа в ресторан
            transform.y = 40f; //позиция входа в ресторан
        }
        PathfinderUtils.animatePath(nodes, transform, transformAnimation, null, () -> { //альпака пробежит по всему пути, который нашли заранее
            Mappers.TEXTURE_MAPPER.get(npc.cloud).visible = true; //когда дойдёт до дивана отобразим облачко
            Mappers.TEXTURE_MAPPER.get(Mappers.CLOUD_MAPPER.get(npc.cloud).foodEntity).visible = true;
            transform.x += 22f + npc.xSitOffset; //зададим позицию сидения (позиция дивана + координаты офсета)
            transform.y += 30f + npc.ySitOffset;
            textureAnimation.reset(); //анимации закончатся после присаживания
            transformAnimation.stopAnimations(TransformAnimationType.POSITION);
        });
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime); //прогоняет все ентити
        if (interactFood != null) { //если юзер перенес еду куда-то, приэтом нпс не среагировал
            interactTransform.x = interactFood.positionX; //возвращаем еду на стол
            interactTransform.y = interactFood.positionY;
            interactTransform = null; //обнул
            interactFood = null;
        }
    }

    public void setInteractFoodEntity(TransformComponent interactTransform, FoodComponent interactFood) {
        this.interactTransform = interactTransform;
        this.interactFood = interactFood;
    }
}
