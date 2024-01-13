package com.relicraider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.relicraider.SetupVariables;
import com.relicraider.characters.Player;

public class HUD implements Disposable {
    private Viewport viewport;
    public Stage stage;
    private Player player;

    private Label healthL;
    private Label healthLabel;
    private Label roomL;
    private Label roomLabel;
    private Label relicsL;
    private Label relicsLable;

    private Table hearts;
    private Texture fullHeart;
    private Texture halfHeart;
    private Texture emptyHeart;

    public HUD (SpriteBatch spriteBatch, Player player) {
        this.player = player;
        fullHeart = new Texture(Gdx.files.internal("Other/fullHeart.png"));
        halfHeart = new Texture(Gdx.files.internal("Other/halfHeart.png"));
        emptyHeart = new Texture(Gdx.files.internal("Other/emptyHeart.png"));

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);
        stage = new Stage(viewport, spriteBatch);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("KenPixel Mini Square.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.magFilter = Texture.TextureFilter.MipMapNearestNearest;
        fontParameter.size = 18;

        BitmapFont font = generator.generateFont(fontParameter);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        Table table = new Table();
        table.top().left();
        table.setFillParent(true);

        healthL = new Label (" HEALTH ", labelStyle);
        healthLabel = new Label(String.format("%03d", player.getHealth()), labelStyle);
        roomL = new Label (" ROOM ", labelStyle);
        roomLabel = new Label (Player.room, labelStyle);
        relicsL = new Label (" RELICS COLLECTED ", labelStyle);
        relicsLable = new Label(Player.getRelicsCollected() +  " / 8", labelStyle);

        table.add(healthL).padTop(10).padLeft(5);
        table.row();
        table.add(healthLabel).padLeft(5);
        table.row();
        table.add(roomL).padTop(10).padLeft(5);
        table.row();
        table.add(roomLabel).padLeft(5);
        table.row();
        table.add(relicsL).padTop(10).padLeft(5);
        table.row();
        table.add(relicsLable).padTop(10).padLeft(5);

        hearts = new Table();
        hearts.bottom().left();
        hearts.setFillParent(true);
        drawHearts();

        stage.addActor(table);
        stage.addActor(hearts);
    }

    private void drawHearts(int health, int counter) {
        if (health < 5 && counter > 10) {
            return;
        } else if (health < 5) {
            hearts.add(new Image(emptyHeart)).padBottom(20).height(40).width(40).padLeft(10);
            drawHearts(health, counter + 1);
        } else if (health < 10) {
            hearts.add(new Image(halfHeart)).padBottom(20).height(40).width(40).padLeft(10);
            drawHearts(health - 5, counter + 1);
        } else {
            hearts.add(new Image(fullHeart)).padBottom(20).height(40).width(40).padLeft(10);
            drawHearts(health - 10, counter + 1);
        }
    }

    public void update(float dt) {
        healthLabel.setText(String.format("%03d", player.getHealth()));
        int health = player.getHealth();
        hearts.clear();
        drawHearts(health, 1);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
