package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;
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
	}

	@After
	public void depois() {
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

}
