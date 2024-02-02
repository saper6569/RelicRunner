/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Hud Class
 */
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
import com.relicraider.screens.MainMenu;
import com.relicraider.screens.gamescreens.*;
import com.relicraider.screens.utilities.Button;

//Hud class implements the Disposable super class
public class HUD implements Disposable {
//ATTRIBUTES
    private Viewport viewport;
    public Stage stage;
    private Player player;

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

    /**
     * Primary Constructor for HUD
     * @param game - Game Object
     * @param spriteBatch - libGDX Sprite Batch object
     * @param player - Player Object
     */
    public HUD (RelicRaider game, SpriteBatch spriteBatch, Player player) {
        this.player = player;
        this.game = game;
        //Import Images, create textures using each of the images
        fullHeart = new Texture(Gdx.files.internal("Other/fullHeart.png"));
        halfHeart = new Texture(Gdx.files.internal("Other/halfHeart.png"));
        emptyHeart = new Texture(Gdx.files.internal("Other/emptyHeart.png"));

        OrthographicCamera camera = new OrthographicCamera(); //Create new camera
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera); //Camera's viewport is set width and height, found in setup variables class
        stage = new Stage(viewport, spriteBatch); //Create new stage
        Gdx.input.setInputProcessor(stage); //Set inputs onto stage

        enterButton = new Button("ENTER", 0, 0, stage, 24); //Create button for entering a new room
        showButton = false; //Dont show button unless player is close to a door
        room = null;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("KenPixel Mini Square.ttf")); //Import font
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.magFilter = Texture.TextureFilter.MipMapNearestNearest; //Set font texture
        fontParameter.size = 18; //Set font size

        BitmapFont font = generator.generateFont(fontParameter);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE); //Create new label with font

        Table table = new Table(); //Create new table for stats to be in
        table.top().left(); //Set table to top left of screen
        table.setFillParent(true);

//Fill table with stats
        roomL = new Label (" ROOM ", labelStyle);
        roomLabel = new Label (Player.room, labelStyle);
        relicsL = new Label (" RELICS COLLECTED ", labelStyle);
        relicsLable = new Label(Player.getRelicsCollected() +  " / 6", labelStyle);
        table.add(roomL).padTop(10).padLeft(5);
        table.row();
        table.add(roomLabel).padLeft(5);
        table.row();
        table.add(relicsL).padTop(10).padLeft(5);
        table.row();
        table.add(relicsLable).padTop(10).padLeft(5);

//Add enter button to hud, put to the bottom right of the screen
        Table enterTable = new Table();
        enterTable.bottom().right();
        enterTable.setFillParent(true);
        enterTable.add(enterButton.getButton()).padBottom(20).padRight(5);

//Add hearts to hud
        hearts = new Table();
        hearts.bottom().left();
        hearts.setFillParent(true);

        stage.addActor(table); //Add table to stage
        stage.addActor(enterTable);
        drawHearts(Player.playerHealth, 1); //Draw players health
        stage.addActor(hearts);

        //If player clicks enter button call playerIsEntering method
        enterButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RelicRaider.soundPlayer.getDoorOpen().play();
                playerIsEntering();
            }
        });
    }

    /**
     * Method to draw player's hearts
     * @param health - amount of health player has
     * @param counter - Counter variable for players hearts
     */
    private void drawHearts(int health, int counter) {
        if (health < 5 && counter > 10) { //If player's health is less than 5 and counter is greater than 10, return
            return;
        } else if (health < 5) { //If player's health is less than 5
            hearts.add(new Image(emptyHeart)).padBottom(20).height(40).width(40).padLeft(5); //Draw an empty heart
            drawHearts(health, counter + 1);
        } else if (health < 10) { //If players health is less than 10
            hearts.add(new Image(halfHeart)).padBottom(20).height(40).width(40).padLeft(5); //Draw half a heart
            drawHearts(health - 5, counter + 1);
        } else { //If players health is not true for any of the above
            hearts.add(new Image(fullHeart)).padBottom(20).height(40).width(40).padLeft(5); //Draw a full heart
            drawHearts(health - 10, counter + 1);
        }
    }

    public void update(float dt) {
        roomLabel.setText(Player.room);
        relicsLable.setText(Player.getRelicsCollected() +  " / 6");
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

    /**
     * Method to show set enter button to show what room the door goes to, set screen to the new room
     */
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

    /**
     * Method to dispose assets used in the hud
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Method to get viewport object
     * @return - Viewport object
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     * Method to set viewport object
     * @param viewport - New Viewport
     */
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    /**
     * Method to get stage object
     * @return - Stage Object
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Method to set stage object
     * @param stage - New stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method to get player object
     * @return - Player Object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Method to set player object
     * @param player - New Player Object
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Method to set if button is to be shown
     * @param showButton - True - If button is to be shown, False -  if it isnt
     */
    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
    }

    /**
     * Method to get current room player is in
     * @return - current room player is in
     */
    public String getRoom() {
        return room;
    }

    /**
     * Method to set current room player is in
     * @param room - New Room player is in
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Method to get x position of a door
     * @return - X position of door
     */
    public float getDoorX() {
        return doorX;
    }

    /**
     * Method to set x position of a door
     * @param doorX - New x position of door
     */
    public void setDoorX(float doorX) {
        this.doorX = doorX;
    }

    /**
     * Method to get y position of a door
     * @return - Y position of door
     */
    public float getDoorY() {
        return doorY;
    }

    /**
     * Method to set y position of a door
     * @param doorY - New y position of door
     */
    public void setDoorY(float doorY) {
        this.doorY = doorY;
    }


}
