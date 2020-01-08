package posjavamavenhibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;
import java.util.List;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TestHibernate {

	@Test
	public void testHibernateUtil() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();

		pessoa.setLogin("steve");
		pessoa.setSenha("423");
		pessoa.setEmail("stevek@tst.com");
		pessoa.setNome("steve");
		pessoa.setSobrenome("jobs");
		pessoa.setIdade(30);

		daoGeneric.salvar(pessoa);

	}

	@Test
	public void testBuscar() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(2L);

		pessoa = daoGeneric.pesquisar(pessoa);

		System.out.println(pessoa);

	}

	@Test
	public void testBuscar2() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(3L, UsuarioPessoa.class);

		System.out.println(pessoa);

	}

	@Test
	public void testUpdate() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(1L, UsuarioPessoa.class);

		pessoa.setEmail("updtess@update.com");
		pessoa.setNome("New name");

		pessoa = daoGeneric.updateMerge(pessoa);

		System.out.println(pessoa);

	}

	@Test
	public void testDelete() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(6L, UsuarioPessoa.class);

		daoGeneric.deletarPoId(pessoa);

	}

	@Test
	public void testConsultar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.listar(UsuarioPessoa.class);

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("----------------------");
		}
	}

	@Test
	public void testQueryList() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa where nome = 'test'")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}

	@Test
	public void testQueryListMaxResult() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa order by id")
				.setMaxResults(2).getResultList();// this setMaxResults(user will input here on the system)

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}

	@Test
	public void testQuerylistParameter() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery("from UsuarioPessoa where nome = :nome and sobrenome = :sobrenome")
				.setParameter("nome", "Paul").setParameter("sobrenome", "Finnegan").getResultList(); //(user will input  surname, login, the parameter to search on the system)

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}
	
	@Test
	public void testQuerySomaidade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		Long somaIdade = (Long) daoGeneric.getEntityManager().createQuery("select sum(u.idade) from UsuarioPesssoa u ").getSingleResult();
		System.out.println("Soma de todas idades e =====" + somaIdade);
		
	}

	
	@Test
	public void testNameQuery1() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		//default uses like this, query centralized and run here UsuarioPessoa.findAll
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.findAll").getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
		
	}
		
		@Test
		public void testNameQuery2() {
			DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
			
			//default uses like this, query centralized and run here UsuarioPessoa.findName
			List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.findName").setParameter("nome", "steve").getResultList();
			
			for (UsuarioPessoa usuarioPessoa : list) {
				System.out.println(usuarioPessoa);
			}
	}
		
		@Test
		public void testSaveTelefone() {
			DaoGeneric daoGeneric = new DaoGeneric();
			
			UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(4L, UsuarioPessoa.class);
			
			TelefoneUser telefoneuser = new TelefoneUser();
			telefoneuser.setTipo("Mobile");
			telefoneuser.setNumero("232323232");
			telefoneuser.setUsuarioPessoa(pessoa);
			
			daoGeneric.salvar(telefoneuser);
			
		}
		
		@Test
		public void testConsultaTelefone() {
			DaoGeneric daoGeneric = new DaoGeneric();
			
			UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(8L, UsuarioPessoa.class);
			
			for (TelefoneUser fone : pessoa.getTelefoneUser()) {
				System.out.println(fone.getNumero());
				System.out.println(fone.getTipo());
				System.out.println(fone.getUsuarioPessoa().getNome());
				System.out.println("----------------------------");
			}
			
		}
}
