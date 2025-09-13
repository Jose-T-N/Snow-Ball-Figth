package com.jtn.snowballfigth.characteres.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.cenaries.Stage;
import com.jtn.snowballfigth.characteres.enemys.Enemy;
import com.jtn.snowballfigth.util.UI;

public class Hero_Life {

    private Animation<TextureRegion> aniLife;
    private float lifeBarW, lifeBarH, checkW, checkH,checkShadowW,checkShadowH;
    private UI ui;
    private Player player;
    private SpriteBatch batch;
    private float delta,life;
    public final static float MAX_LIFE = 6.5f;
    /**
     * A cada 0.5 e uma barra de vida
     */
    public final float LIFE_HIT = .5f;
    private float x,y;

    /**
     * Barra que mostra a forca do arremeco
     */
    public Hero_Life(UI ui, Player player) {
        this.ui = ui;
        this.player = player;
        this.batch = ui.getBatch();
        //animacao do PowerBar
        Texture powerBarTexture = new Texture("util/life-bar-player.png");
        Array<TextureRegion> array = new Array<>();
        for(int i = 0;i<13;i++) {
            array.add(new TextureRegion(powerBarTexture, i * powerBarTexture.getWidth()/13, 0,15,16));
        }
        aniLife = new Animation<>(LIFE_HIT, array);
        //tamanhos PowerBar
        lifeBarW = 50f/ Main.PPM;
        lifeBarH = 25f/Main.PPM;
        x = 6f/Main.PPM;
        y = ui.getHeight()/15f;

        life = MAX_LIFE;
    }

    /**
     * ---------------------------------------------------------------
     *O contador vai de 1 a 8 ( 1 e sem sangue e 8  e sangue completo)
     * ---------------------------------------------------------------
     **/
    public void draw() {
        //------------------------------------------------------------
        // -- A variavel life representa o valor do frame do sangue--
        // -- que vai de 1 a 7.5 -------------------------------------
        //------------------------------------------------------------
        batch.draw( aniLife.getKeyFrame(life,false), x,y,lifeBarW,lifeBarH);
        //life *= Gdx.graphics.getDeltaTime();
    }
    /**
     * reseta o Powerbar
     *
     * */
    public void reset() {
        life = 0;
    }
    /**
     * Retorna o valor do lifebar
     * */
    public float getLife() {
        return life;
    }
    /**
     * muda a o valor do lifebar
     * @param  life valor da vida atual do personagem
     * */
    public void setLife(float life) {
        //---------------------------------------------------------
        // --- Diminui o valor da vida do personagem na barra -----
        //---------------------------------------------------------
        this.life =  life;
        //O valor da vida nao pode ser menor que zero
        if (this.life <= 0)
            this.life = 0;
    }

    public static float getMAX_LIFE() {
        return MAX_LIFE;
    }


}
