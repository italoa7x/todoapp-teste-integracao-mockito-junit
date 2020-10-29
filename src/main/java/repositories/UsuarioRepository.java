package repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import domains.Tarefa;
import domains.Usuario;

/*
 * Autor: Italo Alves <contato.italo1020@gmail.com>
 *
 **/
public class UsuarioRepository {
	private EntityManager entityManager = Persistence.createEntityManagerFactory("pu").createEntityManager();

	public boolean salvar(Usuario u) throws Exception {
		try {
			if (u.getLogin() == null || u.getSenha() == null || u.getNome() == null) {
				throw new Exception("Usuário inválido!");
			}
			entityManager.getTransaction().begin();
			entityManager.persist(u);
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new Exception("Erro ao salvar o usuário. Verifique seus dados\n" + e);
		} finally {
			entityManager.close();
		}
	}

	public Usuario logar(String login, String senha) throws Exception {
		try {
			if (login == null || senha == null) {
				throw new Exception("Dados inválidos!");
			}
			Usuario usuarioEncontrado = (Usuario) entityManager
					.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha")
					.setParameter("login", login).setParameter("senha", senha).getSingleResult();

			return usuarioEncontrado;
		} catch (Exception e) {
			throw new Exception("Erro ao logar no sistema", e.getCause());
		}
	}

	public boolean excluirMinhaConta(Long idConta) throws Exception {
		try {
			Usuario minhaConta = entityManager.find(Usuario.class, idConta);
			entityManager.remove(minhaConta);
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new Exception("Erro ao excluir minha conta", e.getCause());
		}
	}

	public Usuario atualizarDados(Usuario usuario) throws Exception {
		try {
			entityManager.getTransaction().begin();
			usuario = entityManager.merge(usuario);
			entityManager.getTransaction().commit();
			return usuario;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new Exception("Erro ao atualizar os dados do usuário. " + e);
		}
	}

	public Usuario buscaUsuarioPeloLogin(String login) throws Exception {
		try {
			Usuario usuarioEncontrado = (Usuario) entityManager
					.createQuery("SELECT u FROM Usuario u WHERE u.login= :login").setParameter("login", login)
					.getSingleResult();

			return usuarioEncontrado;
		} catch (Exception e) {
			throw new Exception("Erro ao buscar usuario. " + e);
		}
	}

	public List<Tarefa> listarMinhasTarefas(Integer usuarioId) throws Exception{
		try {
			Usuario usuario = (Usuario) entityManager.createQuery("SELECT u FROM Usuario u WHERE u.id = :id")
					.setParameter("id", usuarioId).getSingleResult();
			return usuario.getTarefa();
		}catch(Exception e) {
			throw new Exception("Erro ao buscar usuario. " + e);
		}
	}
}
