package ca.sait.lab7jpa.services;

import ca.sait.lab7jpa.dataaccess.UserDB;
import ca.sait.lab7jpa.models.Role;
import ca.sait.lab7jpa.models.User;
import java.util.List;

public class UserService {
    private UserDB userDB = new UserDB();
    
    public User get(String email) throws Exception {
        User user = this.userDB.get(email);
        return user;
    }
    
    public List<User> getAll() throws Exception {
        List<User> users = this.userDB.getAll();
        return users;
    }
    
    public boolean insert(String email, boolean active, String firstName, String lastName, String password, Role role) throws Exception {
        User user = new User(email, active, firstName, lastName, password, role);
        return this.userDB.insert(user);
    }
    
    public boolean update(String email, boolean active, String firstName, String lastName, String password, Role role) throws Exception {
        User user = new User(email, active, firstName, lastName, password, role);
        return this.userDB.update(user);
    }
    
    public boolean delete(String email) throws Exception {
        //case 1, soft delete
        /*
        User user = new User();
        user.setEmail(email);
        */

        
        /* case 2, hard delete from the user list 'delete' */
        User user = this.get(email);
        
        return this.userDB.delete(user);
    }
}
