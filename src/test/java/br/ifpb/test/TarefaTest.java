package br.ifpb.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import domains.Tarefa;
import repositories.TarefaRepository;

public class TarefaTest {
	private Tarefa tarefa;
	private TarefaRepository repositoryReal, repositoryStub;

	@Before
	public void iniciaInstanciasUsadasNosTestes() {
		repositoryReal = new TarefaRepository();
		repositoryStub = Mockito.mock(TarefaRepository.class);
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
}
