package com.jtn.snowballfigth.characteres.hero;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.util.UI;

public class PowerEnergy {

    private Animation<TextureRegion> power;
    private float powerBarW, powerBarH, checkW, checkH,checkShadowW,checkShadowH;
    private UI ui;
    private Player player;
    private SpriteBatch batch;
    private float powerEnergy;
    public final static float MAX_POWER = 9f;
    /**
     * A cada 0.5 e uma barra de vida
     */
    public static final float POWER_HIT = .3f;
    private float x,y;

    /**
     * Barra que mostra a energia do poder
     */
    public PowerEnergy(UI ui, Player player) {
        this.ui = ui;
        this.player = player;
        this.batch = ui.getBatch();
        //animacao do PowerBar
        Texture powerBarTexture = new Texture("util/power-energy-player.png");
        Array<TextureRegion> array = new Array<>();
        for(int i = 0;i<19;i++) {
            array.add(new TextureRegion(powerBarTexture, i * powerBarTexture.getWidth()/19, 0,38,6));
        }
        power = new Animation<>(0.5f, array);
        //tamanhos PowerBar
        powerBarW = 118f/ Main.PPM;
        powerBarH = 10f/Main.PPM;
        x = 90f/Main.PPM;
        y = ui.getHeight()/13f;
        //Configura a energia do poder com o valor que esta na classe principal
        powerEnergy = ui.getScreen().getGame().getEnergyPower();
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
        batch.draw( power.getKeyFrame(powerEnergy,false), x,y,powerBarW,powerBarH);
        //powerEnergy += 1.5f * Gdx.graphics.getDeltaTime();
    }
    /**
     * reseta o Powerbar
     *
     * */
    public void reset() {
        powerEnergy = 0;
    }
    /**
     * Retorna o valor do lifebar
     * */
    public float getPowerEnergy() {
        return powerEnergy;
    }
    /**
     * muda a o valor do lifebar
     * @param  pe valor da energia do poder atual do personagem
     * */
    public void setPowerEnergy(float pe) {
        //---------------------------------------------------------
        // --- Diminui o valor da vida do personagem na barra -----
        //---------------------------------------------------------
        this.powerEnergy =  pe;
        //O valor da vida nao pode ser menor que zero
        if (this.powerEnergy <= 0)
            this.powerEnergy = 0;
        ((Player)(ui.getScreen().getPlayer())).setPowerEnergy(this.powerEnergy);
    }

    public static float getMAX_LIFE() {
        return MAX_POWER;
    }

}
