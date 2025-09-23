package com.jtn.snowballfigth.characteres.hero.special;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.hero.Player;
import com.jtn.snowballfigth.tools.TimerCount;

public abstract class Special {

    protected TextureRegion special;
    private TextureRegion specialPlayerAnimation;
    private TextureRegion specialPlayerAnimationHand1;
    private TextureRegion specialPlayerAnimationHand2;
    private Player player;
    private SpriteBatch batch;
    //Salva os tamanhos e posicao do player
    private float xp,yp,hp,wp;
    //Salva os tamanhos e posicao das maos
    private float xh,yh,hh,wh,xhStart;
    //Tamanhos das maos 2
    private float hh2,wh2;
    //Verificao de parte do especial
    private boolean hand1,hand2,specialTime;
    //Tamanhos e posicoes do especial
    protected float xs,ys,hs,ws,xsStart;
    private boolean fin;
    private float damage;
    /// Contador para o final do especial
    private TimerCount finalCount;
    //Quantidades de energia para os especiais
    public static final float SPECIAL1 = 3f;
    public static final float SPECIAL2 = 6f;
    public static final float SPECIAL3 = 12f;


    /**
     * Cria a animacoes dos especiais
     * E execulta o dano
     * @param player
     */
    public Special(Player player, String texture, float damage){
        this.player = player;
        //Paraliza o player;
        player.b2body.setLinearVelocity(0,0);
        this.batch = player.getBatch();
        //Carega as texturas das imagens que seram usadas nas animacoes
        Texture t = new Texture("characteres/hero/special0.png");
        specialPlayerAnimation = new TextureRegion(t, t.getWidth(), t.getHeight());
        Texture t1 = new Texture("characteres/hero/special1.png");
        specialPlayerAnimationHand1 = new TextureRegion(t1, t1.getWidth(), t1.getHeight());
        Texture t2 = new Texture("characteres/hero/special2.png");
        specialPlayerAnimationHand2 = new TextureRegion(t2, t2.getWidth(), t2.getHeight());
        //Configura a posicao dos objetos do player
        xp = 5f/Main.PPM;
        yp = 60f/Main.PPM;
        wp = (Main.V_WIDTH/ Main.PPM) - 10f/Main.PPM;
        hp = 100f/ Main.PPM;
        //Configura a posicao dos objetos das maos
        xh = ((Main.V_WIDTH/ Main.PPM)/2f)+(5f/Main.PPM);
        //Salva a posisao inicial do x das maos
        xhStart =((Main.V_WIDTH/ Main.PPM)/2f)+(5f/Main.PPM);
        yh = 50f/Main.PPM;
        wh = 50f/Main.PPM;
        hh = 50f/ Main.PPM;
        //Tamanhos da maos 2
        wh2 = 59f/Main.PPM;
        hh2 = 55f/ Main.PPM;
        //Verifica se e a imagem da mao 1 que ainda esta sendo renderido
        hand1 = true;
        hand2 = false;
        //Textura do especial
        Texture t3 = new Texture(texture);
        this.special = new TextureRegion(t3,t3.getWidth(),t3.getHeight());
        //Configura a posicao dos objetos do player
        xp = 5f/Main.PPM;
        yp = 60f/Main.PPM;
        wp = (Main.V_WIDTH/ Main.PPM) - 10f/Main.PPM;
        hp = 100f/ Main.PPM;
        //Verifica se é hora da animacao do especial
        specialTime = false;
        //Configura a posicao dos objetos do especial
        xs = (Main.V_WIDTH/ Main.PPM)/2f;
        ys = 30f/Main.PPM;
        ws = (Main.V_WIDTH/ Main.PPM);
        hs = Main.V_HEIGHT/ Main.PPM;
        /// Detrmina o final da animacao
        fin = false;
        /// Define o dano do especial
        this.damage = damage;
        /// Contador para o final do especial
        finalCount = new TimerCount();
        /// Zera o energia do poder
        player.setPowerEnergy(0);
    }

    /**
     * Atualiza os objetos do especial
     */
    public void update(){
        //Verifica se e ainda a imagem da mao 1 que ainda esta sendo renderizado
        if (hand1 && !hand2) {
            //Movimenta as maos
            xh += 0.04f/ Main.PPM;
            //faz a imagem da mao 1 se mover
            //Caso o x das maos aucancar um serto limite
            //E infomado para desenhar a imagem 2 das maos
            if (xh >= xhStart + (2f/Main.PPM)){
                hand1 =false;
                hand2 = true;
            }
        }
        else if (!hand1 && hand2){
            ///  Caso a x da mao 2 atinja o limite
            ///  Inforama o fim da animacao do player
            //Movimenta as maos
            xh += 0.04f/ Main.PPM;
            if (xh >= xhStart + (4f/Main.PPM)){
                hand2 = false;
            }
        }
        else{
            specialTime = true;
            /// Quando o x chegar a zero a imagem para de andar
            if (xs  <= 10f/Main.PPM){
                /// conta um segundo com a imagem parada na tela
                finalCount.update();
                if (finalCount.getTimer() >= .5f) {
                    fin = true;
                }
            }
            else {
                //Atualiza a posicao do especial
                xs -= 0.02f;
            }
        }
    }

    /**
     * Desenha os objetos do especial
     */
    public void draw(){
        //Verifica se deve renderizar o especial ou a introducao do especial
        if (!specialTime) {
            //Introducao do especial
            batch.draw(specialPlayerAnimation, xp, yp, wp, hp);
            //Caso o tempo da imagem 1 acabar e desenhada a mao 2
            //Mais informacoes no metodo update
            if (hand1) batch.draw(specialPlayerAnimationHand1, xh, yh, wh, hh);
            else batch.draw(specialPlayerAnimationHand2, xh, yh, wh2, hh2);
        }
        else {
            batch.draw(special, xs, ys, ws, hs);
        }
    }

    /**
     * @return Se é o fim do especial
     */
    public boolean isFin() {
        return fin;
    }

    /**
     * @return A quantidade de dano do especial
     */
    public float getDamage() {
        return damage;
    }
}
