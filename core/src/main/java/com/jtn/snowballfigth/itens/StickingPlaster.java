package com.jtn.snowballfigth.itens;

import com.jtn.snowballfigth.cenaries.stores.Store;
import com.jtn.snowballfigth.characteres.hero.Player;

public class StickingPlaster extends Item {

	public StickingPlaster(Store store) {
		super("sticling plaster", 6 ,store,"itens/item5.png");
	}

    //Aplica o efeito do item
    @Override
    protected void effect(Player player) {
        player.setLife(player.getLife() + 1f);
        player.getHeroLife().setLife(player.getLife());
    }
}
