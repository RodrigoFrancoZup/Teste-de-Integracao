package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Usuario;

public class TesteDoDeletaUsuarioDao {

	private UsuarioDao usuarioDao;
	private Session session;

	@Before
	public void setUp() {
		this.session = new CriadorDeSessao().getSession();
		this.usuarioDao = new UsuarioDao(session);
	}

	@Test
	public void verificaSeUsuarioFoiDeletado() {

		Usuario u = new Usuario("Rodrigo", "rodrigo@email.com");
		usuarioDao.salvar(u);

		usuarioDao.deletar(u);

		session.flush();
		session.clear();

		Usuario busca = usuarioDao.porNomeEEmail("Rodrigo", "rodrigo@email.com");

		assertNull(busca);

	}
}
