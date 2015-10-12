package ejb;

import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import entity.User;

@LocalBean
@Stateless
// @Singleton
public class UserEJB {

	@PersistenceUnit(name = "RESTfulWebServer")
	EntityManagerFactory emf;

	// @Resource
	// UserTransaction utx;

	@PersistenceContext(unitName = "RESTfulWebServer")
	EntityManager em;

	public void saveUser(User user) {
		em.persist(user);
	}

	public void updateUser(String id) {
		User u = em.find(User.class, id);
		u.setName("NewName");
	}

	public void deleteUser(String id) {
		User u = em.find(User.class, id);
		em.remove(u);
	}

	public List<User> getAll() {
		Query q = em.createQuery("select u from User u");
		return (List<User>) q.getResultList();
	}

	public boolean checkLogin(String uname, String pwd) {

		// Test name query start
		Query q1 = em.createNamedQuery("User.findAll");
		Iterator users1 = q1.getResultList().iterator();
		while (users1.hasNext()) {
			User user = (User) users1.next();
			System.out.printf(user.getId() + " -- " + " -- " + user.getName());
		}
		// Test name query end
		boolean isUserAvailable = false;
		Query q = em
				.createQuery("SELECT user FROM User user WHERE user.username = :username and user.password= :password");
		q.setParameter("username", uname);
		q.setParameter("password", pwd);
		System.out.println("sdfsdfsdf");

		Iterator users = q.getResultList().iterator();
		while (users.hasNext()) {
			User user = (User) users.next();
			System.out.printf(user.getId() + " -- " + " -- " + user.getName());
		}

		try {
			Object obj = q.getSingleResult();
			if (obj != null) {
				isUserAvailable = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return isUserAvailable;

	}
}
