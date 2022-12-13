package ru.compot.pomsrest.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.compot.pomsrest.ashley.systems.animations.TextureAnimationSystem;
import ru.compot.pomsrest.ashley.systems.animations.TransformAnimationSystem;
import ru.compot.pomsrest.ashley.systems.render.RenderSystem;
import ru.compot.pomsrest.scene2d.TransitionActor;

// абстрактный класс экрана
public abstract class GameScreen extends ScreenAdapter {

    protected final SpriteBatch batch = new SpriteBatch();
    protected final PooledEngine engine = new PooledEngine();
    protected final Viewport viewport;
    protected final TransitionActor transition;
    protected Stage backgroundStage, foregroundStage;

    protected GameScreen(Viewport viewport) {
        this.viewport = viewport;
        this.transition = new TransitionActor(viewport.getCamera(), 0f, 0f, 0f);
        this.backgroundStage = new Stage(viewport, batch);
        this.foregroundStage = new Stage(viewport, batch);
        engine.addSystem(new RenderSystem(batch));
        engine.addSystem(new TextureAnimationSystem());
        engine.addSystem(new TransformAnimationSystem());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(foregroundStage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void render(float delta) {
        backgroundStage.draw();
        engine.update(delta);
        foregroundStage.draw();
        backgroundStage.act(delta);
        foregroundStage.act(delta);
        transition.draw(batch, 0f);
        transition.act(delta);
    }

    @Override
    public void hide() {
        engine.removeAllEntities();
        engine.removeAllSystems();
        Gdx.input.setInputProcessor(null);
    }

    public Stage getForegroundStage() {
        return foregroundStage;
    }

    public TransitionActor getTransition() {
        transition.setPosition(viewport.getCamera().position.x - viewport.getWorldWidth() / 2f, 0f);
        return transition;
    }
}
