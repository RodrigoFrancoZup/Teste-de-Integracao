package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Usuario;

public class UsuarioDaoTests {

	@Test
	public void porNomeEEmail() {
		// Motando cenario

		Session session = new CriadorDeSessao().getSession();
		UsuarioDao usuarioDao = new UsuarioDao(session);

		Usuario usuario = new Usuario("Rodrigo", "rodrigo@email.com");
		usuarioDao.salvar(usuario);

		// Executando

		Usuario usuarioConsultado = usuarioDao.porNomeEEmail("Rodrigo", "rodrigo@email.com");

		// Verificando
		assertEquals("Rodrigo", usuarioConsultado.getNome());
		assertEquals("rodrigo@email.com", usuarioConsultado.getEmail());

		// Após o teste
		session.close();

	}

	@Test
	public void buscaPorUsuarioInexistente() {

		Session session = new CriadorDeSessao().getSession();
		UsuarioDao usuarioDao = new UsuarioDao(session);

		Usuario usuarioConsultado = usuarioDao.porNomeEEmail("Fred", "fred@email.com");

		assertNull(usuarioConsultado);

		session.close();
	}

}
