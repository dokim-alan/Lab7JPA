package ca.sait.lab7jpa.dataaccess;

import ca.sait.lab7jpa.models.User;
import java.util.List;
import javax.persistence.*;

public class UserDB {

    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Query query = em.createNamedQuery("User.findAll");
            //User user = em.find(User.class, email);
            return query.getResultList();
        } finally {
            em.close();
        }
    
        /* 1. delete whole of JDBC
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //String sql = "SELECT * FROM user";
        //String sql = "SELECT * FROM user INNER JOIN role ON role.role_id = user.role WHERE active != 0"; // = 1;
        String sql = "SELECT * FROM user INNER JOIN role ON role.role_id = user.role";
        //(1)email (2)active (3) firstName (4) lastName (5)password (6)role_id (7) role_name
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            //rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                String email = rs.getString(1);  // or rs.getString("email")
                boolean active = rs.getBoolean(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String password = rs.getString(5);
                int roleId = rs.getInt(6);
                String roleName = rs.getString(7);
                
                Role role = new Role(roleId, roleName);
                User user = new User(email, active, firstName, lastName, password, role);
                
                users.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return users;
        */
    }

    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    
        /*
        User user = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //String sql = "SELECT * FROM user WHERE email=?";
        String sql = "SELECT * FROM user INNER JOIN role ON role.role_id = user.role WHERE email = ? LIMIT 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                boolean active = rs.getBoolean(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String password = rs.getString(5);
                int roleId = rs.getInt(6);
                String roleName = rs.getString(7);
                
                Role role = new Role(roleId, roleName);
                user = new User(email, active, firstName, lastName, password, role);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return user;
        */
    }

    public boolean insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(user);
            em.merge(user);
            trans.commit();
            
            return true;
        } catch (Exception ex) {
            trans.rollback();
            
            return false;
        } finally {
            em.close();
        }
        
        /* JDBC
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        //String sql = "INSERT INTO note (title, contents, owner) VALUES (?, ?, ?)";
        //String sql = "INSERT INTO user ('email', 'firstName', 'lastName', 'password', 'role') VALUES (?, ?, ?, ?, ?)";
        String sql = "INSERT INTO user (`email`, `first_name`, `last_name`, `password`, `role`) VALUES (?, ?, ?, ?, ?)";
        
        boolean inserted = false;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getId());
            
            //inserted = ps.executeUpdate() != 0 ? true : false;
            inserted = ps.executeUpdate() != 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return inserted;
        */
    }

    public boolean update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
            
            return true;
        } catch (Exception ex) {
            trans.rollback();
            
            return false;
        } finally {
            em.close();
        }
        
        /* JDBC
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        //String sql = "UPDATE user SET title=?, contents=? WHERE note_id=?";
        String sql = "UPDATE user SET `first_name` = ?, `last_name` = ?, `password` = ?, `role` = ? WHERE `email`=?";
        
        boolean updated = false;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getId());
            ps.setString(5, user.getEmail());

            // updated = ps.executeUpdate() != 0; or
            updated = ps.executeUpdate() != 0 ? true : false;
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return updated;
        */
    }

    public boolean delete(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            
            /* for Soft delete
            //em.remove(em.merge(user));
            Query query = em.createNamedQuery("User.softDelete", User.class);
            query.setParameter("email", user.getEmail());
            query.executeUpdate();
            */
            
            em.remove(em.merge(user));
            trans.commit();
 
            return true;
        } catch (Exception ex) {
            trans.rollback();
            
            return false;
        } finally {
            em.close();
        }
        
        /* JDBC
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM user WHERE email = ?";
        //String sql = "UPDATE user SET active = 0 WHERE email = ?";
        
        boolean deleted = false;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            
            deleted = ps.executeUpdate() != 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return deleted;
        */
    }

}
