package com.jtn.snowballfigth.cenaries;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.hero.Hero;
import com.jtn.snowballfigth.screens.PlayScreen;

public abstract class Scenario extends PlayScreen  {
    /***
     * Imagem do cenrio
     */
    protected Texture scenario;
    //Autura e largura do cenario
    protected float width, height;

    /**
     * Cria os estadios do jogo
     * @param scenario imagem do cenario
     * @param main Classe principal do core
     * */
    public Scenario(Texture scenario, Main main, boolean villageScenario) {
    	super(main,villageScenario,false);
        //objeto PlayScreen
        this.scenario = scenario;
        this.width = Main.V_WIDTH/Main.PPM;
        this.height = Main.V_HEIGHT/Main.PPM;
        //this.b2dr = screen.b2dr;
    }

}
