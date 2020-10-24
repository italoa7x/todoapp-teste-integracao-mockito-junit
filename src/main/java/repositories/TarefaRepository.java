package repositories;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import domains.Tarefa;


/*
 * Autor: Italo Alves <contato.italo1020@gmail.com>
 *
 **/

public class TarefaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager entityManager = Persistence.createEntityManagerFactory("pu").createEntityManager();

	public Tarefa salvar(Tarefa t) throws Exception  {
		try {
			if (t == null) {
				throw new Exception("Dados inválidos!");
			}
			entityManager.getTransaction().begin();
			entityManager.persist(t);
			entityManager.getTransaction().commit();
			return t;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new Exception("Erro ao salvar o tarefa. Verifique seus dados");
		}
	}

	public Tarefa atualizarTarefa(Tarefa t) throws Exception {
		try {
			if (t == null) {
				throw new Exception("Verifique os dados da tarefa.");
			}
			entityManager.getTransaction().begin();
			entityManager.merge(t);
			entityManager.getTransaction().commit();
			return t;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new Exception("Erro ao salvar a tarefa. Verifique os dados");
		}
	}

	public boolean excluirTarefa(Integer idTarefa) throws Exception {
		try {
			if (idTarefa == null) {
				throw new Exception("ID inválido.");
			}
			Tarefa encontrada = entityManager.find(Tarefa.class, idTarefa);
			entityManager.remove(encontrada);
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new Exception("Erro ao excluir tarefa.");
		}
	}
}
