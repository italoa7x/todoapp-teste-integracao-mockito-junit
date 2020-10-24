package br.ifpb.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import domains.Tarefa;
import domains.Usuario;
import repositories.UsuarioRepository;

public class UsuarioTest {

	private Usuario usuario;
	private UsuarioRepository repositoryReal, repositoryStub;

	@Before
	public void iniciaInstanciasUsadasNosTestes() {

		repositoryReal = new UsuarioRepository();
		repositoryStub = Mockito.mock(UsuarioRepository.class);
	}

	@Test
	public void testaSalvarUmUsuarioPassandoTodosOsDadosMockados() throws Exception {
		usuario = Mockito.mock(Usuario.class);
		usuario.setNome("usuario teste 1");
		usuario.setLogin("usuario");
		usuario.setSenha("123");

		repositoryStub = Mockito.mock(UsuarioRepository.class);
		Mockito.when(repositoryStub.salvar(usuario)).thenReturn(true);
		assertTrue(repositoryStub.salvar(usuario));
	}

	@Test
	public void testaSalvarUmUsuarioComTodosOsDadosUsandoORepositorioReal() throws Exception {
		usuario = new Usuario();
		usuario.setNome("usuario teste");
		usuario.setLogin("usuario");
		usuario.setSenha("123");

		repositoryReal = new UsuarioRepository();
		assertTrue(repositoryReal.salvar(usuario));
	}

	@Test
	public void testaSalvarUmUsuarioMockadoComDadosIncorretos() throws Exception {
		usuario = Mockito.mock(Usuario.class);
		repositoryStub = Mockito.mock(UsuarioRepository.class);

		usuario.setNome("incorreto");

		Mockito.when(repositoryStub.salvar(usuario)).thenReturn(false);

		assertFalse(repositoryStub.salvar(usuario));
	}

	@Test(expected = Exception.class)
	public void testaSalvarUmUsuarioRealComDadosIncorretos() throws Exception {
		usuario = new Usuario();
		usuario.setNome("incorreto");

		Mockito.when(repositoryReal.salvar(usuario)).thenReturn(false);
		assertFalse(repositoryReal.salvar(usuario));
	}

	@Test
	public void testaAtualizarOsDadosDeUmUsuarioQueJaExiste() throws Exception {
		usuario = new Usuario();
		usuario.setNome("usuario teste atualizado");
		usuario.setLogin("usuario");
		usuario.setSenha("123");
		usuario.setId(22);

		assertTrue(repositoryReal.atualizarDados(usuario).getNome().equals(usuario.getNome()));
	}

	@Test
	public void testaAtualizarDadosDeUmUsuarioQueAindaNaoExisteNoBancoReal() throws Exception {
		usuario = new Usuario();
		usuario.setNome("jubilei");
		usuario.setLogin("ju");
		usuario.setSenha("123");
		usuario.setId(1);

		// VAI DAR 'TRUE' PORQUE O USUARIO ERÁ PERSISTIDO
		assertTrue(repositoryReal.atualizarDados(usuario).getNome().equals(usuario.getNome()));
	}

	@Test
	public void testaSalvarUmUsuarioComTodosOsDadosEUmaTarefaValidaNoBancoReal() throws Exception {
		usuario = new Usuario();
		usuario.setNome("sivirino");
		usuario.setLogin("sivirino");
		usuario.setSenha("123455");

		Tarefa tarefa = new Tarefa();
		tarefa.setNome("tarefa 1");
		tarefa.setDescricao("descricao");
		tarefa.setDtCriacao(new Date());

		usuario.getTarefa().add(tarefa);

		assertTrue(repositoryReal.salvar(usuario));
	}

	@Test
	public void testaSalvarUmUsuarioComTodosOsDadosEUmaTarefaValidaNoBancoStub() throws Exception {
		usuario = new Usuario();
		usuario.setNome("sivirino");
		usuario.setLogin("sivirino");
		usuario.setSenha("123455");

		Tarefa tarefa = new Tarefa();
		tarefa.setNome("tarefa 1");
		tarefa.setDescricao("descricao");
		tarefa.setDtCriacao(new Date());

		usuario.getTarefa().add(tarefa);

		Mockito.when(repositoryStub.salvar(usuario)).thenReturn(true);
		assertTrue(repositoryStub.salvar(usuario));
	}
}
