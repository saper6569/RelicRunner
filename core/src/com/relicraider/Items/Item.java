/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   abstract item class
 */
package com.relicraider.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public abstract class Item extends Sprite {
    private static int itemCounter;
    private int itemID;
    String itemName;
    boolean isPickedUp;
    protected float elapsed_time;
    private final static float FRAME_DURATION = 0.30f;

    protected TextureRegion region;
    protected TextureRegion image;
    protected TextureAtlas atlas;
    protected Animation<TextureRegion> animation;

    protected World world;
    protected Body b2dBody;

    /**
     * constructor for an item with an animation
     * @param itemName - name of the item
     * @param atlasFile - file location of the texture atlas file
     */
    public Item(String itemName, String atlasFile) {
        this.itemName = itemName;
        itemCounter++;
        itemID = itemCounter;
        isPickedUp = false;

        elapsed_time = 0.0f;
        region = null;

        atlas = new TextureAtlas(atlasFile);
        Array<TextureAtlas.AtlasRegion> region = atlas.getRegions();
        animation = new Animation<TextureRegion>(FRAME_DURATION, region, Animation.PlayMode.LOOP);
        image = new TextureRegion(atlas.findRegion("00"));
        setRegion(image);
    }

    /**
     * constructor for an item without an animation
     * @param itemName - name of the item
     * @param atlasFile - file location of the texture atlas file
     * @param atlasRegion - name of the region in the atlas file
     */
    public Item(String itemName, String atlasFile, String atlasRegion) {
        this.itemName = itemName;
        itemCounter++;
        itemID = itemCounter;
        isPickedUp = false;

        elapsed_time = 0.0f;
        region = null;

        atlas = new TextureAtlas(atlasFile);
        Array<TextureAtlas.AtlasRegion> region = atlas.getRegions();
        animation = new Animation<TextureRegion>(FRAME_DURATION, region, Animation.PlayMode.LOOP);
        image = new TextureRegion(atlas.findRegion(atlasRegion));
        setRegion(image);
    }

    public abstract void itemIsPickedUp();

    public abstract void update(float dt);

    public abstract void defineBody(float xPos, float yPos);

    /**
     * method for getting a frame from an itme
     * @param dt - time since last render
     * @return the texture region of the animation that should be drawn next
     */
    public TextureRegion getFrame(float dt) {
        elapsed_time += Gdx.graphics.getDeltaTime();
        return animation.getKeyFrame(elapsed_time, true);
    }

    /**
     * method used for removing an item
     */
    public void removeItem() {
        world.destroyBody(b2dBody);
        getTexture().dispose();
    }

    public static int getItemCounter() {
        return itemCounter;
    }

    public static void setItemCounter(int itemCounter) {
        Item.itemCounter = itemCounter;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public float getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(float elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public TextureRegion getImage() {
        return image;
    }

    public void setImage(TextureRegion image) {
        this.image = image;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Body getB2dBody() {
        return b2dBody;
    }

    public void setB2dBody(Body b2dBody) {
        this.b2dBody = b2dBody;
    }
}
