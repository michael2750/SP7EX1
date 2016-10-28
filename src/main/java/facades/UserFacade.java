package facades;

import security.IUserFacade;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import security.IUser;
import security.PasswordStorage;

public final class UserFacade implements IUserFacade {

    /*When implementing your own database for this seed, you should NOT touch any of the classes in the security folder
    Make sure your new facade implements IUserFacade and keeps the name UserFacade, and that your Entity User class implements 
    IUser interface, then security should work "out of the box" with users and roles stored in your database */
    //private final Map<String, IUser> users = new HashMap<>();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("yolo");

    public UserFacade() {
        //Test Users
//        User user = new User("user", "test");
//        user.addRole("User");
//        users.put(user.getUserName(), user);
//        User admin = new User("admin", "test");
//        admin.addRole("Admin");
//        users.put(admin.getUserName(), admin);
//
//        User both = new User("user_admin", "test");
//        both.addRole("User");
//        both.addRole("Admin");
//        users.put(both.getUserName(), both);
    }

    @Override
    public IUser getUserByUserId(String id) {
        return null;
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    @Override
    public List<String> authenticateUser(String userName, String password) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            IUser user = getUserByName(userName);
            String correctHash = user.getPassword();

            boolean PS = PasswordStorage.verifyPassword(password, correctHash);
            em.close();
          if (PS) {
            return user.getRolesAsStrings();
        }

        }  catch(PasswordStorage.CannotPerformOperationException ex){
            
        } catch (PasswordStorage.InvalidHashException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public IUser addUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            java.util.logging.Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return user;

    }

    public User getUserByName(String userName) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> user = em.createQuery("SELECT u FROM User u WHERE u.userName = :name", User.class);
        user.setParameter("name", userName);
        User use = user.getSingleResult();
        return use;
    }

}
