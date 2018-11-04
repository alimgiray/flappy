package com.alimgiray.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {

    private static final int OFFSET = 130;
    private static final int GAP = 100;
    private static final int LOWEST = 120;

    public static final int WIDTH = 52;

    private Texture topTube;
    private Texture bottomTube;

    private Vector2 posTobTube;
    private Vector2 posBottomTube;

    private Rectangle topBounds;
    private Rectangle bottomBounds;

    private Random random;

    public Tube(float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        random = new Random();

        posTobTube = new Vector2(x, random.nextInt(OFFSET) + GAP + LOWEST);
        posBottomTube = new Vector2(x, posTobTube.y - GAP - bottomTube.getHeight());

        topBounds = new Rectangle(posTobTube.x, posTobTube.y, topTube.getWidth(), topTube.getHeight());
        bottomBounds = new Rectangle(posBottomTube.x, posBottomTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTobTube() {
        return posTobTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    public void reposition(float x) {
        posTobTube = new Vector2(x, random.nextInt(OFFSET) + GAP + LOWEST);
        posBottomTube = new Vector2(x, posTobTube.y - GAP - bottomTube.getHeight());

        topBounds.setPosition(posTobTube.x, posTobTube.y);
        bottomBounds.setPosition(posBottomTube.x, posBottomTube.y);
    }

    public boolean collides(Rectangle rectangle) {
        return rectangle.overlaps(topBounds) || rectangle.overlaps(bottomBounds);
    }
}
