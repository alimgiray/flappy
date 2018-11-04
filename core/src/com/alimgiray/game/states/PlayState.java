package com.alimgiray.game.states;

import com.alimgiray.game.Flappy;
import com.alimgiray.game.sprites.Bird;
import com.alimgiray.game.sprites.Tube;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PlayState extends State {

    private static final int TUBE_SPACING = 200;
    private static final int TUBE_COUNT = 4;

    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture background;
    private Texture ground;

    private Vector2 groundPos1;
    private Vector2 groundPos2;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        bird = new Bird(50, 300);
        background = new Texture("bg.png");
        ground = new Texture("ground.png");

        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        camera.setToOrtho(false, Flappy.WIDTH / 2, Flappy.HEIGHT / 2);

        tubes = new Array<Tube>();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.WIDTH)));
        }
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        camera.position.x = bird.getPosition().x + 80;

        for (Tube tube : tubes) {
            if (camera.position.x - camera.viewportWidth / 2 > tube.getPosTobTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTobTube().x + (Tube.WIDTH + TUBE_SPACING) * TUBE_COUNT);
            }

            if (tube.collides(bird.getBounds())) {
                gsm.pop();
                gsm.push(new PlayState(gsm));
            }
        }

        if (bird.getPosition().y < ground.getHeight() % GROUND_Y_OFFSET) {
            gsm.pop();
            gsm.push(new PlayState(gsm));
        }
        updateGround();
        camera.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        batch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        for (Tube tube : tubes) {
            batch.draw(tube.getTopTube(), tube.getPosTobTube().x, tube.getPosTobTube().y);
            batch.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }

        batch.draw(ground, groundPos1.x, groundPos1.y);
        batch.draw(ground, groundPos2.x, groundPos2.y);
        batch.end();
    }

    @Override
    public void dispose() {

    }

    private void updateGround() {
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
