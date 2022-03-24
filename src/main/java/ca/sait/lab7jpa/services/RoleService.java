package ca.sait.lab7jpa.services;

import ca.sait.lab7jpa.dataaccess.RoleDB;
import ca.sait.lab7jpa.models.Role;
import java.util.List;

public class RoleService {
    private RoleDB roleDB = new RoleDB();
    
    public List<Role> getAll() throws Exception {
        List<Role> roles = this.roleDB.getAll();
        return roles;
    }
}
