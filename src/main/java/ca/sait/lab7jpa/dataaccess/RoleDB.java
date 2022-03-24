package ca.sait.lab7jpa.dataaccess;

import ca.sait.lab7jpa.models.Role;
import java.util.List;
import javax.persistence.*;

public class RoleDB {

    public List<Role> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Query query = em.createNamedQuery("Role.findAll");
            //User user = em.find(User.class, email);
            return query.getResultList();
        } finally {
            em.close();
        }
        
        /* JDBC
        List<Role> roles = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM role";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            //rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                
                Role role = new Role(id, name);
                roles.add(role);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return roles;
        */
    }
}
