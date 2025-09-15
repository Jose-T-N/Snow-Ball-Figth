package com.jtn.snowballfigth.itens;

import com.jtn.snowballfigth.cenaries.stores.Store;
import com.jtn.snowballfigth.characteres.hero.Player;

public class Portion extends Item{

	public Portion(Store store) {
		super("portion", 12,store,"itens/item8.png");
	}

    @Override
    public void effect(Player player) {

    }
}
