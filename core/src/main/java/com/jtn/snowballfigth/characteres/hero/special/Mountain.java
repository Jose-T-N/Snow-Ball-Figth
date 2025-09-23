package com.jtn.snowballfigth.characteres.hero.special;

import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.hero.Player;

public class Mountain extends Special{
    /**
     * Cria a animacoes dos especiais
     * E execulta o dano
     *
     * @param player
     */
    public Mountain(Player player) {
        super(player,"characteres/hero/sp1.png",3f);
        hs = (Main.V_HEIGHT/ Main.PPM) - 30f/Main.PPM;
    }
}
