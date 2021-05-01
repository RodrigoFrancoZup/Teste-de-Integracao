package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Lance;
import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;

public class LeilaoDaoTeste {

	private Session session;
	private LeilaoDao leilaoDao;
	private UsuarioDao usuarioDao;

	@Before
	public void antes() {
		this.session = new CriadorDeSessao().getSession();
		this.leilaoDao = new LeilaoDao(session);
		this.usuarioDao = new UsuarioDao(session);
		this.session.beginTransaction();
	}

	@After
	public void depois() {

		this.session.getTransaction().rollback();
		this.session.close();
	}

	@Test
	public void contarQuantidadeDeLeilao() {

		Usuario usuario = new Usuario("Rodrigo", "rodrigo@gmailc.om");

		Leilao leilao = new Leilao("Leilao do PS5", 3000.00, usuario, false);
		Leilao leilaoEncerrado = new Leilao("Leilao do PS3", 1000.00, usuario, true);
		leilaoEncerrado.encerra();

		usuarioDao.salvar(usuario);
		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilaoEncerrado);

		long total = leilaoDao.total();

		assertEquals(1, total);
	}

	@Test
	public void quantidadeDeLeilaoIgualAZero() {

		Usuario usuario = new Usuario("Rodrigo", "rodrigo@gmailc.om");

		Leilao leilao = new Leilao("Leilao do PS5", 3000.00, usuario, false);
		Leilao leilaoEncerrado = new Leilao("Leilao do PS3", 1000.00, usuario, true);
		leilao.encerra();
		leilaoEncerrado.encerra();

		usuarioDao.salvar(usuario);
		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilaoEncerrado);

		long total = leilaoDao.total();

		assertEquals(0, total);
	}

	@Test
	public void deveRetornarLeiloesDeProdutosNovos() {

		Usuario usuario = new Usuario("Rodrigo", "rodrigo@gmailc.om");

		Leilao leilao = new Leilao("Leilao do PS5", 3000.00, usuario, false);
		Leilao leilaoUsado = new Leilao("Leilao do PS3", 1000.00, usuario, true);

		usuarioDao.salvar(usuario);
		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilaoUsado);

		List<Leilao> leiloes = leilaoDao.novos();

		assertEquals(1, leiloes.size());
		assertEquals(false, leiloes.get(0).isUsado());

	}

	@Test
	public void deveRetornarApenasLeiloesAntigos() {

		Leilao leilaoHoje = new Leilao();
		Leilao leilaoHoje2 = new Leilao();

		Leilao leilaoAntigo = new Leilao();
		Calendar c = Calendar.getInstance();
		leilaoAntigo.setDataAbertura(c);
		c.add(Calendar.DAY_OF_MONTH, -10);
		leilaoAntigo.setNome("Antigo");

		Leilao leilaoAntigo2 = new Leilao();
		leilaoAntigo2.setDataAbertura(c);
		leilaoAntigo2.setNome("Antigo2");

		leilaoDao.salvar(leilaoHoje);
		leilaoDao.salvar(leilaoHoje2);
		leilaoDao.salvar(leilaoAntigo);
		leilaoDao.salvar(leilaoAntigo2);

		List<Leilao> leiloies = leilaoDao.antigos();

		assertEquals(2, leiloies.size());
		assertEquals("Antigo", leiloies.get(0).getNome());
		assertEquals("Antigo2", leiloies.get(1).getNome());
	}
	
	@Test
	public void deveRetornarLeilaoCriadoHaSeteDias() {

		Leilao leilao = new Leilao();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -7);
		leilao.setDataAbertura(c);
		leilao.setNome("Antigo");
		leilaoDao.salvar(leilao);

		List<Leilao> leiloies = leilaoDao.antigos();

		assertEquals(1, leiloies.size());
		assertEquals("Antigo", leiloies.get(0).getNome());

	}

}
