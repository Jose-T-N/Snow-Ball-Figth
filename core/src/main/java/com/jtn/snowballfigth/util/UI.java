package com.jtn.snowballfigth.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.cenaries.Stage;
import com.jtn.snowballfigth.characteres.hero.Hero_Life;
import com.jtn.snowballfigth.characteres.hero.Player;
import com.jtn.snowballfigth.characteres.hero.PowerEnergy;
import com.jtn.snowballfigth.screens.PlayScreen;

public class UI {

    private Texture ui;
    private Animation<Texture> life;
    private float lifeValue, width, height, x,y;
    private PlayScreen screen;
    private Player player;
    // SpriteBatch
    private SpriteBatch batch;
    private Hero_Life heroLife;
    private Itens_Hero itensHero;
    private PowerEnergy power;

    /***
     * Cria a imagem da UI do jogador nos estagios
     * @param screen estagio onde a UI sera renderizada
     */
    public UI( Stage screen) {
        //this.lifeValue = lifeValue;
        this.width = (Main.V_WIDTH/Main.PPM);
        this.height = ((Main.V_HEIGHT/Main.PPM) / 4f) - (20f/Main.PPM);
        this.x = 0;
        this.y = 0;
        this.batch = screen.getBatch();
        this.screen = screen;
        this.player = (Player) screen.getPlayer();
        //----------------------------------------------------------
        // --------------------- imagem da UI ----------------------
        //----------------------------------------------------------
        ui = new Texture("util/ui.png");
        this.heroLife = new Hero_Life(this, player);

        this.itensHero = new Itens_Hero(screen);

        //Energia do poder
        power = new PowerEnergy(this,player);

    }
    public void draw(){
        batch.draw(ui, 0, 0, width, height );
        heroLife.draw();
        itensHero.draw();
        power.draw();
    }

    public void shapeDraw(){
        itensHero.shapeDraw();
    }

    public void update(){
        itensHero.update();
    }

    /**
     * Almenta a energia do poder do jogador
     * @param pe powerEnergy
     */
    public void increasePowerEnergy(float pe){
        if (screen.getGame().getEnergyPower() < PowerEnergy.MAX_POWER) {
            ((Player) screen.getPlayer()).setPowerEnergy(screen.getGame().getEnergyPower() + pe);
            ((Stage)screen).getUi().getPower().setPowerEnergy(screen.getGame().getEnergyPower());
        }
        else {
            ((Player) screen.getPlayer()).setPowerEnergy(PowerEnergy.MAX_POWER);
            ((Stage)screen).getUi().getPower().setPowerEnergy(screen.getGame().getEnergyPower());
            //System.out.println("else");
        }

        //((Player) screen.getPlayer()).setPowerEnergy(screen.getGame().getEnergyPower());
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Hero_Life getHeroLife() {
        return heroLife;
    }

    public Itens_Hero getItensHero() {
        return itensHero;
    }

    public PowerEnergy getPower() {
        return power;
    }

    public PlayScreen getScreen() {
        return screen;
    }
}
