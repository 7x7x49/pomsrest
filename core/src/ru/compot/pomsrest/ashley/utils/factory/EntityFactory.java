package ru.compot.pomsrest.ashley.utils.factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.compot.pomsrest.ashley.components.*;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.systems.NPCSystem;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.enums.InteractType;
import ru.compot.pomsrest.scene2d.restaurant.RecipeData;
import ru.compot.pomsrest.utils.Animated2DCamera;
import ru.compot.pomsrest.utils.AtlasUtils;
import ru.compot.pomsrest.utils.constants.AnimationIDs;

import static ru.compot.pomsrest.utils.constants.Assets.*;

// методы для создания энтити
public class EntityFactory {

    private EntityFactory() {
    }

    public static Entity buildPlayerEntity(Engine engine, float x, float y, Screen screen, Animated2DCamera camera) {
        TextureAtlas atlas = getAsset(PLAYER_LLAMA); //атлас - набор объеденённых текстур
        return EntityBuilder.create(engine)
                .setPosition(x, y)
                .setZIndex(10)
                .setSize(70f, 70f)
                .setTextureRegion(atlas.findRegion(RIGHT_IDLE_REGION)) //из данного атласа получаем текстуру альпаки направленной вправо
                .addComponent(TextureAnimationComponent.class, tac -> {
                    tac.add(
                            AnimationIDs.PLAYER_RIGHT_RUNNING, //вправо
                            new Animation<>(PLAYER_FRAME_DURATION, AtlasUtils.findRegionFolder(atlas, RIGHT_RUNNING_FOLDER)),
                            atlas.findRegion(RIGHT_IDLE_REGION)
                    );
                    tac.add(
                            AnimationIDs.PLAYER_LEFT_RUNNING, //влево
                            new Animation<>(PLAYER_FRAME_DURATION, AtlasUtils.findRegionFolder(atlas, LEFT_RUNNING_FOLDER)),
                            atlas.findRegion(LEFT_IDLE_REGION)
                    );
                    tac.add(
                            AnimationIDs.PLAYER_FORWARD_RUNNING, //вперёд
                            new Animation<>(PLAYER_FRAME_DURATION, AtlasUtils.findRegionFolder(atlas, FORWARD_RUNNING)),
                            atlas.findRegion(FORWARD_IDLE_REGION)
                    );
                    tac.add(
                            AnimationIDs.PLAYER_BACK_RUNNING, //назад
                            new Animation<>(PLAYER_FRAME_DURATION, AtlasUtils.findRegionFolder(atlas, BACK_RUNNING)),
                            atlas.findRegion(BACK_IDLE_REGION)
                    );
                })
                .addComponent(TransformAnimationComponent.class)
                .addComponent(PlayerComponent.class, pc -> {
                    pc.screen = screen;
                    pc.camera = camera;
                })
                .build(); //создание игрока
    }

    public static void createInteractArea(Engine engine, float x, float y, float width, float height, InteractType type) { //создаёт области взаимодействия
        EntityBuilder.create(engine) //двигатель
                .setPosition(x, y)
                .setSize(width, height)
                .addComponent(InteractComponent.class, cc -> cc.interactType = type)
                .build();
    }

    public static void createNPCEntity(Engine engine, float targetX, float targetY, String sitRegion, String runningFolder, long spawnDelay, float xSitOffset, float ySitOffset, boolean leftSit) {
        TextureAtlas atlas = getAsset(NPC_ATLAS);
        EntityBuilder.create(engine) //двигатель
                .setPosition(targetX + (leftSit ? 122f : 0f), targetY) //куда будет двигаться нпс (если тру то 122 - полова дивана, если фолз то 0)
                .setSize(70, 70)
                .setTextureRegion(atlas.findRegion(sitRegion)) //из атласа с альпаками ищем ту которую указали в ситрегионе
                .addComponent(TransformAnimationComponent.class)
                .addComponent(TextureAnimationComponent.class, tac -> tac.add(
                        AnimationIDs.NPC_RUNNING,
                        new Animation<>(PLAYER_FRAME_DURATION, AtlasUtils.findRegionFolder(atlas, runningFolder)),
                        atlas.findRegion(sitRegion)
                ))
                .addComponent(NPCComponent.class, npc -> { //маркер нпс
                    npc.lastTimeSpawn = System.currentTimeMillis() + spawnDelay - NPCSystem.SPAWN_RATE; //спавнрейт - пока альпки нет
                    npc.targetX = targetX + (leftSit ? 122f : 0f);
                    npc.targetY = targetY - 3f;
                    npc.xSitOffset = xSitOffset; //отступы
                    npc.ySitOffset = ySitOffset;
                })
                .build();
    }

    public static void createFoodEntity(Engine engine, float tableX, float tableY, RecipeData recipe, int position) {
        TextureRegion region = ((TextureAtlas) getAsset(MINI_RECIPES_ATLAS)).findRegion(recipe.getMiniRegion()); //мини рецепты
        EntityBuilder.create(engine) //двигатель
                .setPosition(tableX + 5f + position * 42f, tableY + 5f) //находим позицию еды относительно стола, 42 - размер одной еды + отступ
                .setSize(35f, 25f)
                .setTextureRegion(region)
                .addComponent(FoodComponent.class, f -> { //позиция относительно стола
                    f.position = position;
                    f.positionX = tableX + 5f + position * 42f;
                    f.positionY = tableY + 5f;
                    f.recipe = recipe;
                })
                .build();
    }

    public static void createCloudEntity(Engine engine, Entity npc) { //облачко = облачко + еда (две сущности)
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(npc);
        NPCComponent npcData = Mappers.NPC_MAPPER.get(npc);
        TextureRegion foodRegion = ((TextureAtlas) getAsset(MINI_RECIPES_ATLAS)).findRegion(npcData.food.getMiniRegion());
        TextureRegion cloudRegion = ((TextureAtlas) getAsset(RESTAURANT_ATLAS)).findRegion(RESTAURANT_CLOUD);
        Entity food = EntityBuilder.create(engine) //создаём ентити еды
                .setPosition(
                        npcData.targetX + transform.width + cloudRegion.getRegionWidth() / 2f - foodRegion.getRegionWidth() / 2f, //кордината дивана + размер нпс + половина размера облака - половина размера еды
                        npcData.targetY + transform.height + cloudRegion.getRegionHeight() / 2f - foodRegion.getRegionHeight() / 2f
                )
                .setZIndex(5) //слой 5
                .setSize(foodRegion.getRegionWidth(), foodRegion.getRegionHeight())
                .setTextureRegion(foodRegion)
                .build();
        Entity result = EntityBuilder.create(engine) //создаётся облачко
                .setPosition(npcData.targetX + transform.width, npcData.targetY + transform.height) //позиция дивана + размер нпс
                .setSize(cloudRegion.getRegionWidth(), cloudRegion.getRegionHeight())
                .setZIndex(3) //слой 3
                .setTextureRegion(cloudRegion) //задали текстуру
                .addComponent(CloudComponent.class, cc -> cc.foodEntity = food) //задали маркер (облачко) ((ентити еды там содержица))
                .build();
        Mappers.TEXTURE_MAPPER.get(result).visible = false; //прячем облачко и еду
        Mappers.TEXTURE_MAPPER.get(food).visible = false;
        npcData.cloud = result;
    }

}
