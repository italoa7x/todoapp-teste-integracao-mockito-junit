package br.ifpb.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import domains.Tarefa;
import repositories.TarefaRepository;
import repositories.UsuarioRepository;

public class TarefaTest {
	private Tarefa tarefa;
	private TarefaRepository repositoryReal, repositoryStub;
	private UsuarioRepository repositoryUsuarioStub, repositoryUsuarioReal;

	@Before
	public void iniciaInstanciasUsadasNosTestes() {
		repositoryReal = new TarefaRepository();
		repositoryStub = Mockito.mock(TarefaRepository.class);
		repositoryUsuarioStub = Mockito.mock(UsuarioRepository.class);
		repositoryUsuarioReal = new UsuarioRepository();
	}

	@Test
	public void testaSalvarUmaTarefaComTodosOsDadosNoRepositorioReal() throws Exception {
		tarefa = new Tarefa();
		tarefa.setNome("tarefa real");
		tarefa.setDescricao("descricao da tarefa");
		tarefa.setDtCriacao(new Date());

		assertTrue(repositoryReal.salvar(tarefa).getNome().equals(tarefa.getNome()));
	}

	@Test
	public void testaSalvarUmaTarefaComTodosOsDadosNoRepositorioStub() throws Exception {
		tarefa = new Tarefa();
		tarefa.setNome("tarefa real");
		tarefa.setDescricao("descricao da tarefa");
		tarefa.setDtCriacao(new Date());

		Mockito.when(repositoryStub.salvar(tarefa)).thenReturn(tarefa);
		assertTrue(repositoryStub.salvar(tarefa).getNome().equals(tarefa.getNome()));

	}

	@Test
	public void testaAtualizarUmaTarefaQueExisteNoBancoReal() throws Exception {
		tarefa = new Tarefa();
		tarefa.setNome("tarefa real atualizada");
		tarefa.setDescricao("descricao da tarefa");
		tarefa.setDtCriacao(new Date());
		tarefa.setDtFinalizacao(new Date());
		tarefa.setId(1);

		assertTrue(repositoryReal.atualizarTarefa(tarefa).getNome().equals(tarefa.getNome()));
	}

	@Test
	public void testaExcluirUmaTarefaComIdInvalidoNoRepositorioStub() throws Exception {
		int idInvalido = 100;
		Mockito.when(repositoryStub.excluirTarefa(idInvalido)).thenReturn(false);
		assertFalse(repositoryStub.excluirTarefa(idInvalido));
	}

	@Test(expected = Exception.class)
	public void testaCapturaDeExcecaoAoCadastrarUmaTarefaInvalidaNoRepositorioStub() throws Exception {
		Mockito.when(repositoryStub.salvar(tarefa)).then(null);
		assertNull(repositoryStub.salvar(tarefa));
	}

	@Test(expected = Exception.class)
	public void testaCapturaDeExcecaoAoCadastrarUmaTarefaInvalidaNoRepositorioReal() throws Exception {
		repositoryReal.salvar(tarefa);
	}

	@Test
	public void testaListarTarefasNoBancoStub() {
		List<Tarefa> tarefas = Arrays.asList(new Tarefa(1, "tarefa 1", "descricao 1"),
				new Tarefa(2, "tarefa 2", "descricao 2"), new Tarefa(3, "tarefa 3", "descricao 3"),
				new Tarefa(4, "tarefa 4", "descricao 4"));
		Mockito.when(repositoryStub.listarTarefas()).thenReturn(tarefas);

		assertTrue(repositoryStub.listarTarefas().size() > 0);
	}

	@Test
	public void testaListarTarefasNoBancoReal() {
		assertTrue(repositoryReal.listarTarefas().size() > 0);
	}

	@Test
	public void testaBuscarTarefasDeUmUsuarioQueNaoExisteComOBancoStub() throws Exception {
		Mockito.when(repositoryUsuarioStub.listarMinhasTarefas(1)).thenReturn(null);
		assertNull(repositoryUsuarioStub.listarMinhasTarefas(1));
	}

	@Test
	public void testaBuscarTarefasDeUmUsuarioQueExisteNoBancoReal() throws Exception {
		assertTrue(repositoryUsuarioReal.listarMinhasTarefas(2).size() > 0);
	}
}
