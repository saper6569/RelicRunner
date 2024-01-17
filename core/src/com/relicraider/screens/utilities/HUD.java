package com.relicraider.screens.utilities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;
import com.relicraider.characters.Player;
import com.relicraider.screens.gamescreens.*;

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

    private Button enterButton;
    private boolean showButton;
    private float doorX, doorY;
    private String room;
    private RelicRaider game;

    public HUD (RelicRaider game, SpriteBatch spriteBatch, Player player) {
        this.player = player;
        this.game = game;
        fullHeart = new Texture(Gdx.files.internal("Other/fullHeart.png"));
        halfHeart = new Texture(Gdx.files.internal("Other/halfHeart.png"));
        emptyHeart = new Texture(Gdx.files.internal("Other/emptyHeart.png"));

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);
        stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        enterButton = new Button("ENTER", 0, 0, stage, 24);
        showButton = false;
        room = null;

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
        relicsLable = new Label(Player.getRelicsCollected() +  " / 6", labelStyle);

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

        Table enterTable = new Table();
        enterTable.bottom().right();
        enterTable.setFillParent(true);
        enterTable.add(enterButton.getButton()).padBottom(20).padRight(5);

        hearts = new Table();
        hearts.bottom().left();
        hearts.setFillParent(true);

        stage.addActor(table);
        stage.addActor(enterTable);
        drawHearts(Player.playerHealth, 1);
        stage.addActor(hearts);

        enterButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerIsEntering();
            }
        });
    }

    private void drawHearts(int health, int counter) {
        if (health < 5 && counter > 10) {
            return;
        } else if (health < 5) {
            hearts.add(new Image(emptyHeart)).padBottom(20).height(40).width(40).padLeft(5);
            drawHearts(health, counter + 1);
        } else if (health < 10) {
            hearts.add(new Image(halfHeart)).padBottom(20).height(40).width(40).padLeft(5);
            drawHearts(health - 5, counter + 1);
        } else {
            hearts.add(new Image(fullHeart)).padBottom(20).height(40).width(40).padLeft(5);
            drawHearts(health - 10, counter + 1);
        }
    }

    public void update(float dt) {
        roomLabel.setText(Player.room);
        relicsLable.setText(Player.getRelicsCollected() +  " / 6");
        healthLabel.setText(String.format("%03d", player.getHealth()));
        int health = player.getHealth();
        hearts.clear();
        drawHearts(health, 1);
        if (!showButton) {
            enterButton.getButton().setVisible(false);
            enterButton.getButton().setDisabled(true);
        } else {
            enterButton.getButton().setText("ENTER " + room.toUpperCase());
            enterButton.getButton().setVisible(true);
            enterButton.getButton().setDisabled(false);
        }
    }

    private void playerIsEntering() {
        if (room.equals("room 1")) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Room1(game, doorX, doorY));
        } else if (room.equals("room 2")) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Room2(game, doorX, doorY));
        } else if (room.equals("room 3")) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Room3(game, doorX, doorY));
        } else if (room.equals("room 4")) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Room4(game, doorX, doorY));
        } else if (room.equals("room 5")) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Room5(game, doorX, doorY));
        } else if (room.equals("room 6")) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Room6(game, doorX, doorY));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Viewport getViewport() {
        return viewport;
    }

    public Stage getStage() {
        return stage;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public float getDoorX() {
        return doorX;
    }

    public void setDoorX(float doorX) {
        this.doorX = doorX;
    }

    public void setDoorY(float doorY) {
        this.doorY = doorY;
    }
}
