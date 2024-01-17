/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Button Class
 */
package com.relicraider.screens.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.relicraider.SetupVariables;

//Create Public Button Class
public class Button {
//ATTRIBUTES
    public static int width = 190;
    public static int height = 49;
    private Texture texture;
    private TextureRegion textureRegion;
    private TextureRegionDrawable drawable;
    private ImageTextButton button;
    private ImageTextButton.ImageTextButtonStyle style;
    private Label buttonLabel;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private String text;

    /**
     * Primary Constructor
     * @param text - Text on the button
     * @param x - x value of the button
     * @param y - y value of the button
     * @param stage - stage button is on
     * @param fontSize - font size of the text
     */
    public Button(String text, float x, float y, Stage stage, int fontSize) {
        //Set all attributes
        this.text = text;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("KenPixel Mini Square.ttf")); //Add new font from file
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.magFilter = Texture.TextureFilter.MipMapNearestNearest;
        fontParameter.size = fontSize;

        BitmapFont font = generator.generateFont(fontParameter); //Generate new font added

        texture = new Texture(Gdx.files.internal("Other/button.png"));
        textureRegion = new TextureRegion(texture);
        drawable = new TextureRegionDrawable(textureRegion);
        style = new ImageTextButton.ImageTextButtonStyle(drawable, drawable, drawable, font);
        button = new ImageTextButton(text, style);

        button.setPosition(x, y);

        stage.addActor(button);
    }

    /**
     * Get Texture of button
     * @return - texture of button
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Set Texture of button
     * @param texture - the new texture for the button
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Get Texture Region of button
     * @return - Texture Region of button
     */
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    /**
     * Set Texture Region of button
     * @param textureRegion - New Texture Region of Button
     */
    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    /**
     * Get Drawable of Button
     * @return - Drawable of Button
     */
    public TextureRegionDrawable getDrawable() {
        return drawable;
    }

    /**
     * Set Drawable of Button
     * @param drawable - New Drawable of button
     */
    public void setDrawable(TextureRegionDrawable drawable) {
        this.drawable = drawable;
    }

    /**
     * Set Font Size of the text on the button
     * @param size - New Size of text
     */
    public void setFontSize(int size){
        fontParameter.size = size;
    }

    /**
     * returns the button
     * @return the button
     */
    public ImageTextButton getButton() {
        return button;
    }

    /**
     * setter for the text
     * @param text - text
     */
    public void setText(String text) {
        this.text = text;
        button.setText(text);
    }
}

