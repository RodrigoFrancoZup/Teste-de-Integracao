package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Leilao;

public class TestaDeletarLeilao {

	private Session session;
	private LeilaoDao leilaoDao;
	private UsuarioDao usuarioDao;

	@Before
	public void antes() {
		session = new CriadorDeSessao().getSession();
		leilaoDao = new LeilaoDao(session);
		usuarioDao = new UsuarioDao(session);

		// inicia transacao
		session.beginTransaction();
	}

	@After
	public void depois() {
		// faz o rollback
		session.getTransaction().rollback();
		session.close();
	}

	@Test
	public void temQueDeletarOLeilaoCriado() {
		Leilao leilao = new Leilao();
		leilaoDao.salvar(leilao);

		leilaoDao.deleta(leilao);

		session.flush();
		session.clear();

		Leilao busca = leilaoDao.porId(leilao.getId());

		assertNull(busca);

	}

}
