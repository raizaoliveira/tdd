package test.br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;
import br.com.caelum.leilao.servico.CriadorDeLeilao;
import org.junit.*;

import java.util.List;

public class AvaliadorTest {

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

    @After
    public void finaliza() {
        System.out.println("fim");
    }

    @BeforeClass
    public static void testandoBeforeClass() {
        System.out.println("before class");
    }

    @AfterClass
    public static void testandoAfterClass() {
        System.out.println("after class");
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {

        Leilao leilao = new CriadorDeLeilao().para("Play")
                .lance(maria, 250.0)
                .lance(joao,300.0)
                .lance(jose,400.0)
                .constroi();

        leiloeiro.avalia(leilao);
        // comparando a saida com o esperado
        double maiorEsperado = 400;
        double menorEsperado = 250;

        Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }


    @Test
    public void deveEntenderLancesEmOrdemCrescenteComOutrosValores() {
        Usuario joao = new Usuario("João");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 1000.0));
        leilao.propoe(new Lance(jose, 2000.0));
        leilao.propoe(new Lance(maria, 3000.0));

        leiloeiro.avalia(leilao);

        Assert.assertEquals(3000.0, leiloeiro.getMaiorLance(), 0.0001);
        Assert.assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {

        Leilao leilao = new CriadorDeLeilao().para("Play")
                .lance(joao, 1000.00)
                .constroi();

        leiloeiro.avalia(leilao);

        Assert.assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.0001);
        Assert.assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);


    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {

        Leilao leilao = new CriadorDeLeilao().para("Play")
                .lance(joao, 200.0)
                .lance(maria, 450.00)
                .lance(joao, 120.0)
                .lance(maria, 700.00)
                .lance(joao, 630.0)
                .lance(maria, 230.00)
                .constroi();
        leiloeiro.avalia(leilao);

        Assert.assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
        Assert.assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
        Leilao leilao = new CriadorDeLeilao().para("Play 3")
                .lance(joao, 400.00)
                .lance(maria, 300.00)
                .lance(joao, 200.00)
                .lance(maria, 100.00)
                .constroi();

        leiloeiro.avalia(leilao);

        Assert.assertEquals(400.0, leiloeiro.getMaiorLance(), 0.0001);
        Assert.assertEquals(100.0, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(joao, 300.0)
                .lance(maria, 400.0)
                .constroi();

        leiloeiro.avalia(leilao);
        List<Lance> maiores = leiloeiro.getTresMaiores();

        Assert.assertEquals(3, maiores.size());
        Assert.assertEquals(400, maiores.get(0).getValor(), 0.00001);
        Assert.assertEquals(300, maiores.get(1).getValor(), 0.00001);
        Assert.assertEquals(200, maiores.get(2).getValor(), 0.00001);
    }

    @Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 100.00)
                .lance(maria, 200.00)
                .constroi();

        leiloeiro.avalia(leilao);
        List<Lance> maiores = leiloeiro.getTresMaiores();

        Assert.assertEquals(2, maiores.size());
        Assert.assertEquals(200, maiores.get(0).getValor(), 0.00001);
        Assert.assertEquals(100, maiores.get(1).getValor(), 0.00001);
    }

    @Test
    public void deveDevolverListaVaziaCasoNaoHajaLances() {
        Leilao leilao = new Leilao("Playstation 3 Novo");
        leiloeiro.avalia(leilao);
        List<Lance> maiores = leiloeiro.getTresMaiores();
        Assert.assertEquals(0, maiores.size());
    }


}