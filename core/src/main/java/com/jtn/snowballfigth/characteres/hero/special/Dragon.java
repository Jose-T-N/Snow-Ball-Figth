package com.jtn.snowballfigth.characteres.hero.special;

import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.hero.Player;

public class Dragon extends Special{
    /**
     * Cria a animacoes dos especiais
     * E execulta o dano
     *
     * @param player objeto do jogador
     */
    public Dragon(Player player) {
        super(player, "characteres/hero/sp2.png",3f);
        hs = (Main.V_HEIGHT/ Main.PPM) - 30f/Main.PPM;
    }
}
