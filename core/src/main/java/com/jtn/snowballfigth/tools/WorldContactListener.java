package com.jtn.snowballfigth.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.cenaries.Village;
import com.jtn.snowballfigth.characteres.enemys.Enemy;
import com.jtn.snowballfigth.characteres.hero.Hero;
import com.jtn.snowballfigth.characteres.hero.Player;
import com.jtn.snowballfigth.itens.bullets.Bullet;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
        // Objetos do Contato
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            // Abre a formacia qunado o jogador tocar na porta
            case Main.PLAYER | Main.DRUGSTORE:
                if (fixA.getFilterData().categoryBits == Main.DRUGSTORE) {
                    ((Village) fixA.getUserData()).drugstore();
                } else if (fixB.getFilterData().categoryBits == Main.DRUGSTORE) {
                    ((Village) fixB.getUserData()).drugstore();
                }
                // Abre a formacia qunado o jogador tocar na porta
            case Main.PLAYER | Main.GUN_STORE:
                if (fixA.getFilterData().categoryBits == Main.GUN_STORE) {
                    ((Village) fixA.getUserData()).gunStore();
                } else if (fixB.getFilterData().categoryBits == Main.GUN_STORE) {
                    ((Village) fixB.getUserData()).gunStore();
                }
            case Main.PLAYER | Main.STAGE1:
                if (fixA.getFilterData().categoryBits == Main.STAGE1) {
                    ((Village) fixA.getUserData()).stage1();
                } else if (fixB.getFilterData().categoryBits == Main.STAGE1) {
                    ((Village) fixB.getUserData()).stage1();
                }
            case Main.ENEMY | Main.SHOOT:
                if (fixA.getFilterData().categoryBits == Main.ENEMY) {
                    ((Enemy) fixA.getUserData()).hit(((Bullet) fixB.getUserData()).getShoot_point());
                } else if (fixB.getFilterData().categoryBits == Main.ENEMY) {
                    ((Enemy) fixB.getUserData()).hit(((Bullet) fixA.getUserData()).getShoot_point());
                }

            case Main.PLAYER | Main.BULLET_ENEMY:
                if (fixA.getFilterData().categoryBits == Main.PLAYER && fixB.getFilterData().categoryBits == Main.BULLET_ENEMY ) {
                    ((Player) fixA.getUserData()).hit( ((Bullet) fixB.getUserData()).shootPlayer());
                } else if (fixB.getFilterData().categoryBits == Main.PLAYER && fixA.getFilterData().categoryBits == Main.BULLET_ENEMY) {
                    ((Player) fixB.getUserData()).hit( ((Bullet) fixA.getUserData()).shootPlayer());
                }
        }
    }


	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
