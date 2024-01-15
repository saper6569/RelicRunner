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

public class Button {
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

    public Button(String text, float x, float y, Stage stage, int fontSize) {
        this.text = text;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("KenPixel Mini Square.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.magFilter = Texture.TextureFilter.MipMapNearestNearest;
        fontParameter.size = fontSize;

        BitmapFont font = generator.generateFont(fontParameter);

        texture = new Texture(Gdx.files.internal("Other/button.png"));
        textureRegion = new TextureRegion(texture);
        drawable = new TextureRegionDrawable(textureRegion);
        style = new ImageTextButton.ImageTextButtonStyle(drawable, drawable, drawable, font);
        button = new ImageTextButton(text, style);

        button.setPosition(x, y);

        stage.addActor(button);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public TextureRegionDrawable getDrawable() {
        return drawable;
    }

    public void setDrawable(TextureRegionDrawable drawable) {
        this.drawable = drawable;
    }

    public void setFontSize(int size){
        fontParameter.size = size;
    }

    public Label getButtonLabel() {
        return buttonLabel;
    }

    public void setButtonLabel(Label buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public ImageTextButton getButton() {
        return button;
    }

    public void setButton(ImageTextButton button) {
        this.button = button;
    }

    public ImageTextButton.ImageTextButtonStyle getStyle() {
        return style;
    }

    public void setStyle(ImageTextButton.ImageTextButtonStyle style) {
        this.style = style;
    }

    public FreeTypeFontGenerator.FreeTypeFontParameter getFontParameter() {
        return fontParameter;
    }

    public void setFontParameter(FreeTypeFontGenerator.FreeTypeFontParameter fontParameter) {
        this.fontParameter = fontParameter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        button.setText(text);
    }
}

