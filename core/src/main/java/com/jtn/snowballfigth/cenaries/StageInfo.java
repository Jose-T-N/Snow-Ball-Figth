package com.jtn.snowballfigth.cenaries;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.screens.PlayScreen;
import com.jtn.snowballfigth.tools.TimerCount;

public class StageInfo extends PlayScreen {
    //Lista do primeiro estagio
    public static final int STAGE1_1 = 0;
    public static final int STAGE2_1 = 1;
    public static final int STAGE3_1 = 2;
    public static final int STAGE4_1 = 3;
    //Lista do segundo estagio
    public static final int STAGE1_2 = 4;
    public static final int STAGE2_2 = 5;
    public static final int STAGE3_2 = 6;
    public static final int STAGE4_2 = 7;
    //Lista do terceiro estagio
    public static final int STAGE1_3 = 8;
    public static final int STAGE2_3 = 9;
    public static final int STAGE3_3 = 10;
    public static final int STAGE4_3 = 11;
    //Lista do quarto estagio
    public static final int STAGE1_4 = 12;
    public static final int STAGE2_4 = 13;
    public static final int STAGE3_4 = 14;
    public static final int STAGE4_4 = 15;

    private Texture number1;
    private Texture number2;
    private Texture line;
    private Texture s,t,a,g,e;

    ///  Conta o tempo de mostra as informacoes
    private TimerCount infoTime;
    /// objeto de renderizacao
    private SpriteBatch batch;
    /// Stagio para onde o player esta indo
    private Stage stage;
    /**
     * Mostra as inforamacoes do estagio antes de entra
     * no estagio
     * @param stageAndFase Fase e estagio que o jogador esta prestis a entrar
     * @param game objeto da classe Main
     */

    public StageInfo(int stageAndFase, Main game){
        super(game,false,true);
        this.batch = game.batch;
        //Carrega as texturas das letras
        //Letra s
        s = new Texture("alphanumerics/word-0.png");
        //Letra t
        t = new Texture("alphanumerics/word-1.png");
        //Letra a
        a = new Texture("alphanumerics/word-2.png");
        //Letra g
        g = new Texture("alphanumerics/word-3.png");
        //Letra e
        e = new Texture("alphanumerics/word-4.png");
        //Linha que divide os numeros
        line = new Texture("alphanumerics/lineG.png");
        //-----------------------------------------------------------------------------------
        // ---------------------- Numeros do primeiro estagio -------------------------------
        //- ---------------------------------------------------------------------------------
        if (stageAndFase == STAGE1_1){
            number1 = new Texture("alphanumerics/stage1.png");
            number2 = new Texture("alphanumerics/stage1.png");
            //Stagio para onde o player esta sendo direcionado
            stage = new Stage(getGame());

        }
        else if (stageAndFase == STAGE2_1){
            number1 = new Texture("alphanumerics/stage2.png");
            number2 = new Texture("alphanumerics/stage1.png");
        }else if (stageAndFase == STAGE3_1){
            number1 = new Texture("alphanumerics/stage3.png");
            number2 = new Texture("alphanumerics/stage1.png");
        }
        else if (stageAndFase == STAGE4_1){
            number1 = new Texture("alphanumerics/stage4.png");
            number2 = new Texture("alphanumerics/stage1.png");
        }
        //-----------------------------------------------------------------------------------
        // ---------------------- Numeros do segundo estagio -------------------------------
        //- ---------------------------------------------------------------------------------
        else if (stageAndFase == STAGE1_2){
            number1 = new Texture("alphanumerics/stage1.png");
            number2 = new Texture("alphanumerics/stage2.png");
        }
        else if (stageAndFase == STAGE2_2){
            number1 = new Texture("alphanumerics/stage2.png");
            number2 = new Texture("alphanumerics/stage2.png");
        }else if (stageAndFase == STAGE3_2){
            number1 = new Texture("alphanumerics/stage3.png");
            number2 = new Texture("alphanumerics/stage2.png");
        }
        else if (stageAndFase == STAGE4_2){
            number1 = new Texture("alphanumerics/stage4.png");
            number2 = new Texture("alphanumerics/stage2.png");
        }
        //-----------------------------------------------------------------------------------
        // ---------------------- Numeros do segundo estagio -------------------------------
        //- ---------------------------------------------------------------------------------
        else if (stageAndFase == STAGE1_3){
            number1 = new Texture("alphanumerics/stage1.png");
            number2 = new Texture("alphanumerics/stage3.png");
        }
        else if (stageAndFase == STAGE2_3){
            number1 = new Texture("alphanumerics/stage2.png");
            number2 = new Texture("alphanumerics/stage3.png");
        }else if (stageAndFase == STAGE3_3){
            number1 = new Texture("alphanumerics/stage3.png");
            number2 = new Texture("alphanumerics/stage3.png");
        }
        else if (stageAndFase == STAGE4_3){
            number1 = new Texture("alphanumerics/stage4.png");
            number2 = new Texture("alphanumerics/stage3.png");
        }
        //-----------------------------------------------------------------------------------
        // ---------------------- Numeros do segundo estagio -------------------------------
        //- ---------------------------------------------------------------------------------
        else if (stageAndFase == STAGE1_4){
            number1 = new Texture("alphanumerics/stage1.png");
            number2 = new Texture("alphanumerics/stage4.png");
        }
        else if (stageAndFase == STAGE2_4){
            number1 = new Texture("alphanumerics/stage2.png");
            number2 = new Texture("alphanumerics/stage4.png");
        }else if (stageAndFase == STAGE3_4){
            number1 = new Texture("alphanumerics/stage3.png");
            number2 = new Texture("alphanumerics/stage4.png");
        }
        else if (stageAndFase == STAGE4_4){
            number1 = new Texture("alphanumerics/stage4.png");
            number2 = new Texture("alphanumerics/stage4.png");
        }
        infoTime = new TimerCount();
    }

    @Override
    public void update(float delta) {
        //Conta o tempo que mostra a informacao do estagio
        infoTime.update();
        //Quando o tempo atingir o limite o estagio comessa
        if (infoTime.getTimer() >=3){
            this.game.setScenario(stage);
            this.game.setScreen(this.game.getScenario());
        }

    }

    @Override
    public void draw(float delta) {
        //Posicao x da primeira letra
        float xw = 130f/Main.PPM;
        //Posicao y de todas as letra
        float yw =  150f/Main.PPM;
        //Tamanho da largura de todas as letras
        float ww = (float) s.getWidth() /Main.PPM;
        //Tamanho da altura de todas as letras
        float hw = (float) s.getHeight() /Main.PPM;
        //entre cada letra soma-se o especo de 30px
        float distanceX = 30f/Main.PPM;
        //Desenha o "s" de STAGE
        batch.draw(s,xw,yw,ww,hw);
        //Desenha o "t" de STAGE
        batch.draw(t,xw+distanceX,yw,ww,hw);
        //Desenha o "a" de STAGE
        batch.draw(a,xw+(distanceX*2f),yw,ww,hw);
        //Desenha o "g" de STAGE
        batch.draw(g,xw+(distanceX*3f),yw,ww,hw);
        //Desenha o "e" de STAGE
        batch.draw(e,xw+(distanceX*4f),yw,ww,hw);
        // ---------------------------------------
        //  --------- Desenha os numeros ---------
        // ---------------------------------------
        //Posicao x da primeiro numero
        float xn = xw +30f/Main.PPM;
        //Posicao y de todas os numeros
        float yn =  yw - 50f/Main.PPM;
        //Tamanho da largura de todos os numeros
        float wn = (float) s.getWidth() /Main.PPM;
        //Tamanho da altura de todos os numeros
        float hn = (float) s.getHeight() /Main.PPM;
        //entre cada numero soma-se o especo de 30px
        float distanceXNum = 30f/Main.PPM;
        //Desenha o primeiro numero
        batch.draw(number1,xn,yn,wn,hn);
        //Deseha a linha que divide os numeros
        batch.draw(line,xn+distanceXNum,yn,wn,hn);
        //Desenha o segundo numero
        batch.draw(number2,xn+(distanceXNum*2),yn,wn,hn);
    }

    @Override
    public void handleInput() {

    }
}
