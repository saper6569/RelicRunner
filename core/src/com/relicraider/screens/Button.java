package com.relicraider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.relicraider.SetupVariables;

public class Button {
    private Texture texture;
    private TextureRegion textureRegion;
    private TextureRegionDrawable drawable;
    private ImageButton button;

    private Label buttonLabel;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

    public Button(String text, float x, float y) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("PixelifySans-Bold.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.magFilter = Texture.TextureFilter.MipMapNearestNearest;
        fontParameter.size = 18;

        BitmapFont font = generator.generateFont(fontParameter);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        texture = new Texture(Gdx.files.internal("Other/button.png"));
        textureRegion = new TextureRegion(texture);
        drawable = new TextureRegionDrawable(textureRegion);
        button = new ImageButton(drawable);
        button.setPosition(x, y);
        buttonLabel = new Label(text, labelStyle);
        buttonLabel.setPosition(x,y);

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

    public ImageButton getButton() {
        return button;
    }

    public void setButton(ImageButton button) {
        this.button = button;
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
}

