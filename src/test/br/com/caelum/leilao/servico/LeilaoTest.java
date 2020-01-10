package test.br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;
import br.com.caelum.leilao.servico.CriadorDeLeilao;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeilaoTest {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;

    @Before
    public void setUp(){
        this.leiloeiro = new Avaliador();
    }

    @Before
    public void criaAvaliador() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
    }


    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook")
                .constroi();

        assertEquals(0, leilao.getLances().size());

        leilao = new CriadorDeLeilao().para("Macbook")
                .lance(joao, 2000)
                .constroi();

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook")
                .lance(joao, 2000)
                .lance(jose, 3000)
                .constroi();
        assertEquals(2, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook")
                .lance(joao, 2000.0)
                .lance(joao, 3000.0)
                .constroi();
        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {

        Leilao leilao = new CriadorDeLeilao().para("Macbook")
                .lance(joao, 2000.0)
                .lance(maria, 3000.0)
                .lance(joao, 4000.0)
                .lance(maria, 5000.0)
                .lance(joao, 6000.0)
                .lance(maria, 7000.0)
                .lance(joao, 8000.0)
                .lance(maria, 9000.0)
                .lance(joao, 10000.0)
                .lance(maria, 11000.0)
                .lance(joao, 12000.0)
                .constroi();

        assertEquals(10, leilao.getLances().size());
        assertEquals(11000.0, leilao.getLances().get(leilao.getLances().size()-1).getValor(), 0.00001);
    }

    @Test
    public void deveDobrarOUltimoLanceDado() {

        Leilao leilao = new CriadorDeLeilao().para("Macbook")
                .lance(joao, 2000)
                .lance(jose, 3000)
                .constroi();
        leilao.dobraLance(joao);

        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
    }

    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook")
                .constroi();
        leilao.dobraLance(joao);
        assertEquals(0, leilao.getLances().size());
    }

    @Test(expected=RuntimeException.class)
    public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .constroi();

        leiloeiro.avalia(leilao);
    }
}