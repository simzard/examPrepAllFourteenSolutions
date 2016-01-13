/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author simon
 */
@Entity
public class PhDStudent extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dissSubject;

    public PhDStudent() {
        
    }
    
    public PhDStudent(String dissSubject, int soSecNr, float wage, String taxClass, String firstName, String lastName, Date birthDate, int age, boolean isMarried) {
        super(soSecNr, wage, taxClass, firstName, lastName, birthDate, age, isMarried);
        this.dissSubject = dissSubject;
    }

    public PhDStudent(Integer id, String dissSubject, int soSecNr, float wage, String taxClass, String firstName, String lastName, Date birthDate, int age, boolean isMarried) {
        super(soSecNr, wage, taxClass, firstName, lastName, birthDate, age, isMarried);
        this.id = id;
        this.dissSubject = dissSubject;
    }

    public String getDissSubject() {
        return dissSubject;
    }

    public void setDissSubject(String dissSubject) {
        this.dissSubject = dissSubject;
    }
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PhDStudent)) {
            return false;
        }
        PhDStudent other = (PhDStudent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PhDStudent[ id=" + id + " ]";
    }
    
}
