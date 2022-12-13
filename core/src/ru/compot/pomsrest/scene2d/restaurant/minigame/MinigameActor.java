package ru.compot.pomsrest.scene2d.restaurant.minigame;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.compot.pomsrest.ashley.components.FoodComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.factory.EntityFactory;
import ru.compot.pomsrest.scene2d.restaurant.RecipeData;
import ru.compot.pomsrest.screens.restaurant.RestaurantScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.compot.pomsrest.utils.constants.Assets.*;

// миниигра
public class MinigameActor extends Actor {

    private static final int NORMAL = 1, SHIT = 2;

    private final ShapeRenderer renderer = new ShapeRenderer();
    private final Group children = new Group();
    private final TextureAtlas atlas = getAsset(MINIGAME_ATLAS);
    private final TextureRegion background = new TextureRegion((Texture) getAsset(MINIGAME_BACKGROUND));
    private final TextureRegion normalFood = atlas.findRegion(NORMAL_FOOD);
    private final TextureRegion shitFood = atlas.findRegion(SHIT_FOOD);
    private final RecipeData recipe;
    private final List<String> ingredients = new ArrayList<>();
    private int cookingState;

    public MinigameActor(RecipeData recipe, RestaurantScreen screen, boolean[] tableCellsAccess, Engine engine, float tableX, float tableY) {
        this.recipe = recipe;
        Map<String, Image> map = new HashMap<>();
        map.put(INGREDIENT_ICE_CREAM, addListenerTo(new Image(atlas.findRegion(INGREDIENT_ICE_CREAM)), new MinigameListener(INGREDIENT_ICE_CREAM, this)));
        map.put(INGREDIENT_TOMATO, addListenerTo(new Image(atlas.findRegion(INGREDIENT_TOMATO)), new MinigameListener(INGREDIENT_TOMATO, this)));
        map.put(INGREDIENT_WINE, addListenerTo(new Image(atlas.findRegion(INGREDIENT_WINE)), new MinigameListener(INGREDIENT_WINE, this)));
        map.put(INGREDIENT_OCTOPUS, addListenerTo(new Image(atlas.findRegion(INGREDIENT_OCTOPUS)), new MinigameListener(INGREDIENT_OCTOPUS, this)));
        map.put(INGREDIENT_WHIPPING_CREAM, addListenerTo(new Image(atlas.findRegion(INGREDIENT_WHIPPING_CREAM)), new MinigameListener(INGREDIENT_WHIPPING_CREAM, this)));
        map.put(INGREDIENT_EGG, addListenerTo(new Image(atlas.findRegion(INGREDIENT_EGG)), new MinigameListener(INGREDIENT_EGG, this)));
        map.put(INGREDIENT_LEMON, addListenerTo(new Image(atlas.findRegion(INGREDIENT_LEMON)), new MinigameListener(INGREDIENT_LEMON, this)));
        map.put(INGREDIENT_BISCUIT, addListenerTo(new Image(atlas.findRegion(INGREDIENT_BISCUIT)), new MinigameListener(INGREDIENT_BISCUIT, this)));
        map.put(INGREDIENT_CHICKEN, addListenerTo(new Image(atlas.findRegion(INGREDIENT_CHICKEN)), new MinigameListener(INGREDIENT_CHICKEN, this)));
        map.put(INGREDIENT_CURD, addListenerTo(new Image(atlas.findRegion(INGREDIENT_CURD)), new MinigameListener(INGREDIENT_CURD, this)));
        map.put(INGREDIENT_LOBSTER, addListenerTo(new Image(atlas.findRegion(INGREDIENT_LOBSTER)), new MinigameListener(INGREDIENT_LOBSTER, this)));
        map.put(INGREDIENT_CHERRY, addListenerTo(new Image(atlas.findRegion(INGREDIENT_CHERRY)), new MinigameListener(INGREDIENT_CHERRY, this)));
        map.put(INGREDIENT_TOPPING, addListenerTo(new Image(atlas.findRegion(INGREDIENT_TOPPING)), new MinigameListener(INGREDIENT_TOPPING, this)));
        map.put(INGREDIENT_FLOUR, addListenerTo(new Image(atlas.findRegion(INGREDIENT_FLOUR)), new MinigameListener(INGREDIENT_FLOUR, this)));
        map.put(INGREDIENT_MUSSEL, addListenerTo(new Image(atlas.findRegion(INGREDIENT_MUSSEL)), new MinigameListener(INGREDIENT_MUSSEL, this)));
        map.put(INGREDIENT_CRACKERS, addListenerTo(new Image(atlas.findRegion(INGREDIENT_CRACKERS)), new MinigameListener(INGREDIENT_CRACKERS, this)));
        map.put(INGREDIENT_SPICES, addListenerTo(new Image(atlas.findRegion(INGREDIENT_SPICES)), new MinigameListener(INGREDIENT_SPICES, this)));
        map.put(INGREDIENT_JUICE, addListenerTo(new Image(atlas.findRegion(INGREDIENT_JUICE)), new MinigameListener(INGREDIENT_JUICE, this)));
        map.put(INGREDIENT_SHRIMP, addListenerTo(new Image(atlas.findRegion(INGREDIENT_SHRIMP)), new MinigameListener(INGREDIENT_SHRIMP, this)));
        map.put(INGREDIENT_ONION, addListenerTo(new Image(atlas.findRegion(INGREDIENT_ONION)), new MinigameListener(INGREDIENT_ONION, this)));
        map.put(INGREDIENT_PUFF_PASTRY, addListenerTo(new Image(atlas.findRegion(INGREDIENT_PUFF_PASTRY)), new MinigameListener(INGREDIENT_PUFF_PASTRY, this)));
        map.put(INGREDIENT_SUGAR, addListenerTo(new Image(atlas.findRegion(INGREDIENT_SUGAR)), new MinigameListener(INGREDIENT_SUGAR, this)));
        setPosition(Gdx.graphics.getWidth() / 2f - background.getRegionWidth() / 2f, Gdx.graphics.getHeight() / 2f - background.getRegionHeight() / 2f);
        float comX = 10f, comY = 10f;
        for (Map.Entry<String, Image> entry : map.entrySet()) { //расставляем всё как табличку
            Image com = entry.getValue();
            children.addActor(com);
            if (entry.getKey().equals(INGREDIENT_LOBSTER)) { //если ключ элемента лобстер
                com.setPosition(380f - com.getWidth(), 30f); //то справа
                continue;
            }
            com.setPosition(5f + comX, comY);
            if (comX + com.getWidth() + 5f > 370f - com.getWidth()) {
                comX = 10f;
                comY += com.getHeight();
                com.setPosition(5f + comX, comY);
            }
            comX += com.getWidth() + 5f;
        }
        Image exit = new Image(atlas.findRegion(MINIGAME_EXIT)); //иконка выхода х
        exit.setPosition(background.getRegionWidth(), background.getRegionHeight() - exit.getImageHeight() - 5f); //позиция = угол мини игры
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) { //+ обработчик нажатий
                if (cookingState == NORMAL && ingredients.size() >= recipe.getRecipeComponents().length) { //если еда ок + размер списка ингридиентов + больше или равен длине рецепта то
                    boolean spawned = false;
                    for (int i = 0; i < tableCellsAccess.length; i++) { //проходимся по ячейкам на столе
                        boolean cell = tableCellsAccess[i]; //получаем ячейку
                        if (!cell) { //если ячейка пуста (=фолз)
                            EntityFactory.createFoodEntity(engine, tableX, tableY, recipe, i); //помещаем еду
                            tableCellsAccess[i] = true; //занята
                            spawned = true;
                            break;
                        }
                    }
                    if (!spawned) {
                        tableCellsAccess[tableCellsAccess.length - 1] = true; //берём самую последнюю ячейку и освобождаем
                        ImmutableArray<Entity> foods = engine.getEntitiesFor(Family.all(FoodComponent.class).get()); //получаем список всех ентити еды, проходимся
                        for (int j = 0; j < foods.size(); j++) { //проходимся
                            Entity f = foods.get(j);
                            FoodComponent fc = Mappers.FOOD_MAPPER.get(f);
                            if (fc.position == tableCellsAccess.length - 1) engine.removeEntity(f); //чекаем места позиции еды = последнему индексу на столе то удаляем из двигателя
                        }
                        EntityFactory.createFoodEntity(engine, tableX, tableY, recipe, tableCellsAccess.length - 1); //создать еду на последней ячейке
                    }
                }
                screen.closeUI(); //закрываем мини игру
                screen.openRecipeBook(); //открываем книгу
            }
        });
        children.addActor(exit); //список детей = дочерние компоненты
    }

    private static Image addListenerTo(Image actor, ClickListener listener) {
        actor.addListener(listener);
        return actor;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) { //отрисовка мини игры по центру
        Camera camera = getStage().getViewport().getCamera();
        float halfViewportWidth = camera.viewportWidth / 2f; //ищем центр экрана
        float halfViewportHeight = camera.viewportHeight / 2f; //ищем центр экрана
        float actorX = getX() + camera.position.x - halfViewportWidth;
        float actorY = getY() + camera.position.y - halfViewportHeight;
        children.setPosition(actorX, actorY); //указываем дочерним позицию отцентрированной мини гейм
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(new Color(0f, 0f, 0f, 0.5f));
        renderer.rect(camera.position.x - halfViewportWidth, camera.position.y - halfViewportHeight, camera.viewportWidth, camera.viewportHeight);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND); //до этой строки рисуется фон
        batch.begin(); //сама мини игра
        batch.draw(background, actorX, actorY);
        switch (cookingState) { //рисуется игра
            case NORMAL:
                batch.draw(normalFood, actorX + 160, actorY + 350); //нямка
                break;
            case SHIT:
                batch.draw(shitFood, actorX + 153, actorY + 345); //бяка
                break;
        }
    }

    public Group getChildren() {
        return children;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void onAddIngredient() {
        cookingState = NORMAL;
        for (String ing : ingredients) {
            boolean normal = false;
            for (RecipeData.RecipeComponent rc : recipe.getRecipeComponents()) {
                if (rc.id.equals(ing)) {
                    normal = true;
                    break;
                }
            }
            if (!normal) cookingState = SHIT;
        }
    }
}
