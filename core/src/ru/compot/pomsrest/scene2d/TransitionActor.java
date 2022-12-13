package ru.compot.pomsrest.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.compot.pomsrest.Application;
import ru.compot.pomsrest.screens.GameScreen;

// переход между экранами (черный экран рисуем)
public class TransitionActor extends Actor {

    private static final float TRANSITION_DURATION = .8f;

    private final Camera camera;
    private final ShapeRenderer rend = new ShapeRenderer();

    public TransitionActor(Camera camera, float r, float g, float b) {
        this.camera = camera;
        setColor(r, g, b, 0f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getColor().a > 0f) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            rend.setProjectionMatrix(camera.combined);
            rend.begin(ShapeRenderer.ShapeType.Filled);
            rend.setColor(getColor());
            rend.rect(getX(), getY(), camera.viewportWidth, camera.viewportHeight);
            rend.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void startTransition(Screen nextScreen) { //запускает переход между экранами
        getColor().a = 0f;
        addAction(Actions.sequence(Actions.fadeIn(TRANSITION_DURATION), Actions.run(() -> {
            if (nextScreen instanceof GameScreen)
                ((GameScreen) nextScreen).getTransition().startReversedTransition();
            Application.INSTANCE.setScreen(nextScreen);
        })));
    }

    public void startReversedTransition() { //чорни -> прозрачный
        getColor().a = 1f;
        addAction(Actions.fadeOut(TRANSITION_DURATION));
    }
}
