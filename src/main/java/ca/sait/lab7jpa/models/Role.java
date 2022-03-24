package ca.sait.lab7jpa.models;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * Represents a role
 * @author Alan(Dong O) Kim
 */
@Entity
@Table(name = "role")
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
    })
public class Role implements Serializable{
    @Id
    @Basic(optional = false)
    @Column(name = "role_id")
    private int id;         // role_id

    @Column(name = "role_name")
    private String name;    // role_name
    
    @OneToMany
    private List<User> user;
    
    public Role() {
        
    }
    
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}