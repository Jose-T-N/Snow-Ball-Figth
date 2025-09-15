package com.jtn.snowballfigth.itens;

import com.jtn.snowballfigth.cenaries.stores.Store;
import com.jtn.snowballfigth.characteres.hero.Player;

public class PowerfulAdhesive extends Item {

	public PowerfulAdhesive(Store store) {
		super("powerful adhesive", 10,store,"itens/item7.png");
		// TODO Auto-generated constructor stub
	}

    @Override
    protected void effect(Player player) {

    }
}
