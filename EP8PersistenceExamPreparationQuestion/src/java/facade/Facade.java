/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.Employee;
import entities.Person;
import entities.PhDStudent;
import entities.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author simon
 */
public class Facade {

    private EntityManagerFactory emf;

    public Facade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Person addPerson(Person p) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            p = em.merge(p);
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return p;
    }

    public Person findPersonById(int id) {
        EntityManager em = getEntityManager();
        Person personToFind = null;
        try {
            personToFind = em.find(Person.class, id);
        } finally {
            em.close();
        }

        return personToFind;
    }

    public Person findPersonByName(String name) {
        String fname = "";
        String lname = "";
        String tokens[] = name.split(" ");

        fname = tokens[0];
        if (tokens.length == 2) {
            lname = tokens[1];
        }

        EntityManager em = getEntityManager();
        List<Person> foundPersons;
        try {
            Query query = null;
            if (tokens.length == 1) {
                query = em.createQuery("select p from Person p where p.firstName = :fn");
                query.setParameter("fn", fname);
            } else {
                query = em.createQuery("select p from Person p where p.firstName = :fn and p.lastName = :ln");
                query.setParameter("fn", fname);
                query.setParameter("ln", lname);
            }

            foundPersons = query.getResultList();
        } finally {
            em.close();
        }
        return foundPersons.size() > 0 ? foundPersons.get(0) : null;
    }

    public Person deletePersonById(int id) {
        EntityManager em = getEntityManager();
        Person personToDelete = findPersonById(id);

        if (personToDelete != null) {
            try {
                em.getTransaction().begin();
                personToDelete = em.merge(personToDelete);
                em.remove(personToDelete);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return personToDelete;
    }

    public Person deletePersonByName(String name) {
        Person personToDelete = findPersonByName(name);

        EntityManager em = getEntityManager();

        if (personToDelete != null) {
            try {
                em.getTransaction().begin();
                personToDelete = em.merge(personToDelete);
                em.remove(personToDelete);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return personToDelete;
    }

    // basically overwrittes the person with the id
    // with the new person data given in the object passed
    // the method figures out which type of object it is 
    // and persists it accordingly
    public Person editPerson(Person p) {
        Person personToEdit = findPersonById(p.getId());
        Student studentToEdit = null;
        Employee employeeToEdit = null;
        PhDStudent phdStudentToEdit = null;

        if (personToEdit != null) {
            if (p.getClass() == Person.class) {
                personToEdit.setFirstName(p.getFirstName());
                personToEdit.setLastName(p.getLastName());
                personToEdit.setBirthDate(p.getBirthDate());
                personToEdit.setAge(p.getAge());
                personToEdit.setIsMarried(p.isIsMarried());

                personToEdit.setSupervised(p.getSupervised());
                personToEdit.setSupervisor(p.getSupervisor());
                personToEdit.setGrades(p.getGrades());
            } else if (p.getClass() == Student.class) {
                Student sp = (Student) p;
                studentToEdit = (Student) personToEdit;
                studentToEdit.setMatNr(sp.getMatNr());
                studentToEdit.setMatDate(sp.getMatDate());
                
                studentToEdit.setFirstName(p.getFirstName());
                studentToEdit.setLastName(p.getLastName());
                studentToEdit.setBirthDate(p.getBirthDate());
                studentToEdit.setAge(p.getAge());
                studentToEdit.setIsMarried(p.isIsMarried());

                studentToEdit.setSupervised(p.getSupervised());
                studentToEdit.setSupervisor(p.getSupervisor());
                studentToEdit.setGrades(p.getGrades());
                
            } else if (p.getClass() == Employee.class) {
                Employee ep = (Employee) p;
                employeeToEdit = (Employee) personToEdit;
                employeeToEdit.setSoSecNr(ep.getSoSecNr());
                employeeToEdit.setWage(ep.getWage());
                employeeToEdit.setTaxClass(ep.getTaxClass());

                employeeToEdit.setFirstName(p.getFirstName());
                employeeToEdit.setLastName(p.getLastName());
                employeeToEdit.setBirthDate(p.getBirthDate());
                employeeToEdit.setAge(p.getAge());
                employeeToEdit.setIsMarried(p.isIsMarried());

                employeeToEdit.setSupervised(p.getSupervised());
                employeeToEdit.setSupervisor(p.getSupervisor());
                employeeToEdit.setGrades(p.getGrades());
                
            } else if (p.getClass() == PhDStudent.class) {
                PhDStudent pp = (PhDStudent) p;
                phdStudentToEdit = (PhDStudent) personToEdit;
                phdStudentToEdit.setSoSecNr(pp.getSoSecNr());
                phdStudentToEdit.setWage(pp.getWage());
                phdStudentToEdit.setTaxClass(pp.getTaxClass());
                phdStudentToEdit.setDissSubject(pp.getDissSubject());
                
                phdStudentToEdit.setFirstName(p.getFirstName());
                phdStudentToEdit.setLastName(p.getLastName());
                phdStudentToEdit.setBirthDate(p.getBirthDate());
                phdStudentToEdit.setAge(p.getAge());
                phdStudentToEdit.setIsMarried(p.isIsMarried());

                phdStudentToEdit.setSupervised(p.getSupervised());
                phdStudentToEdit.setSupervisor(p.getSupervisor());
                phdStudentToEdit.setGrades(p.getGrades());
            }

            // determine how much to copy when doing the update/edit
            // first copy generel person attributes
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                if (p.getClass() == Person.class) {
                    personToEdit = em.merge(personToEdit);
                    em.persist(personToEdit);
                } else if (p.getClass() == Student.class) {
                    studentToEdit = em.merge(studentToEdit);
                    em.persist(studentToEdit);
                } else if (p.getClass() == Employee.class) {
                    employeeToEdit = em.merge(employeeToEdit);
                    em.persist(employeeToEdit);
                } else if (p.getClass() == PhDStudent.class) {
                    phdStudentToEdit = em.merge(phdStudentToEdit);
                    em.persist(phdStudentToEdit);
                }
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        
        if (p.getClass() == Person.class) {
            return personToEdit;
        } else if (p.getClass() == Student.class) {
            return studentToEdit;
        } else if (p.getClass() == Employee.class) {
            return employeeToEdit;
        } else if (p.getClass() == PhDStudent.class) {
            return phdStudentToEdit;
        }
        return null;
    }
}
