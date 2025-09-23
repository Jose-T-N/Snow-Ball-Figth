package com.jtn.snowballfigth.characteres.hero.special;

import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.hero.Player;

public class SnowMan extends Special{
    /**
     * Cria a animacoes dos especiais
     * E execulta o dano
     *
     * @param player jogador
     * */
    public SnowMan(Player player) {
        super(player, "characteres/hero/sp3.png",3f);
        hs = (Main.V_HEIGHT/ Main.PPM) - 30f/Main.PPM;
    }
}
