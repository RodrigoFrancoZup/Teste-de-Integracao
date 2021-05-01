package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Usuario;

public class UsuarioDaoTests {

	private Session session;
	private UsuarioDao usuarioDao;

	@Before
	public void antes() {
		this.session = new CriadorDeSessao().getSession();
		this.usuarioDao = new UsuarioDao(session);

		this.session.beginTransaction();
	}

	@After
	public void depois() {

		this.session.getTransaction().rollback();
		this.session.close();
	}

	@Test
	public void porNomeEEmail() {
		// Motando cenario

		Usuario usuario = new Usuario("Rodrigo", "rodrigo@email.com");
		usuarioDao.salvar(usuario);

		// Executando

		Usuario usuarioConsultado = usuarioDao.porNomeEEmail("Rodrigo", "rodrigo@email.com");

		// Verificando
		assertEquals("Rodrigo", usuarioConsultado.getNome());
		assertEquals("rodrigo@email.com", usuarioConsultado.getEmail());

	}

	@Test
	public void buscaPorUsuarioInexistente() {

		Usuario usuarioConsultado = usuarioDao.porNomeEEmail("Fred", "fred@email.com");

		assertNull(usuarioConsultado);

	}

	@Test
	public void testaOUpdate() {

		Usuario usuario = new Usuario("Rodrigo", "rodrigo@email.com");
		usuarioDao.salvar(usuario);

		usuario.setNome("Opa");
		usuario.setEmail("opa@gmail.com");
		usuarioDao.atualizar(usuario);

		session.flush();
		session.clear();

		usuarioDao.atualizar(usuario);

		Usuario buscaNull = usuarioDao.porNomeEEmail("Rodrigo", "rodrigo@email.com");
		Usuario busca = usuarioDao.porNomeEEmail("Opa", "opa@gmail.com");

		assertNull(buscaNull);
		assertNotNull(busca);
	}

}
