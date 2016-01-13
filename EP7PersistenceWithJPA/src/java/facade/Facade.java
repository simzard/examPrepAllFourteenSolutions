/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.Project;
import entities.ProjectUser;
import entities.Task;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

    public ProjectUser createProjectUser(String userName, String email, String created) {
        EntityManager em = getEntityManager();
        ProjectUser pu = new ProjectUser(userName, email, created);

        try {
            em.getTransaction().begin();
            em.persist(pu);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return pu;
    }

    public ProjectUser getProjectUser(Long id) {
        EntityManager em = getEntityManager();
        ProjectUser pu = em.find(ProjectUser.class, id);
        return pu;
    }

    public ProjectUser getProjectUser(String userName) {
        EntityManager em = getEntityManager();
        List<ProjectUser> pus = null;
        try {
            Query query = em.createQuery("select pu from ProjectUser pu where pu.userName = :un ");
            query.setParameter("un", userName);
            pus = query.getResultList();
        } finally {
            em.close();
        }
        return pus.get(0);
    }
    
    public List<ProjectUser> getAllProjectUsers() {
        EntityManager em = getEntityManager();
        List<ProjectUser> pus = null;
        try {
            Query query = em.createQuery("select pu from ProjectUser pu");
            pus = query.getResultList();
        } finally {
            em.close();
        }
        return pus;
    }

    public Project createProject(String name, String description, String created, String lastModified) {
        EntityManager em = getEntityManager();
        Project p = new Project(name, description, created, lastModified);
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return p;
    }
    
    public void assignProjectUserToProject(ProjectUser pu, Project p) {
        EntityManager em = getEntityManager();
        ProjectUser realPu = em.find(ProjectUser.class, pu.getId());
        Project realP = em.find(Project.class, p.getId());
        
        realPu.addProject(realP);
        realP.addProjectUser(realPu);
        
        try {
            em.getTransaction().begin();
            em.persist(realPu);
            em.persist(realP);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public Project getProject(String name) {
        EntityManager em = getEntityManager();
        List<Project> ps = null;
        try {
            Query query = em.createQuery("select p from Project p where p.name = :n ");
            query.setParameter("n", name);
            ps = query.getResultList();
        } finally {
            em.close();
        }
        return ps.get(0);
    }
    
    public void createTaskAndAssignToProject(String taskName, String taskDescription, int taskHoursAssigned, int taskHoursUsed, Project p) {
        Task t = new Task(taskName, taskDescription, taskHoursAssigned, taskHoursUsed);
        EntityManager em = getEntityManager();
        Project realP = em.find(Project.class, p.getId());
        realP.addTask(t);
        t.setProject(realP);
        
        try {
            em.getTransaction().begin();
            em.persist(realP);
            em.persist(t);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
    }
    
    public static void main(String[] args) {
        Facade f = new Facade(Persistence.createEntityManagerFactory("PersistenceWithJPAPU"));
        f.createProjectUser("Simon", "thatguy@simonsteinaa.dk", "07/01/2016");
        f.createProjectUser("Martin", "flimflam@smth.dk", "07/01/2016");
        f.createProjectUser("Ib", "henrikjensen@123.dk", "07/01/2016");
        
        Project gameProject = f.createProject("Game Project", "Cool HTML 5 game", "07/01/2016", "07/01/2016");
        Project examProject = f.createProject("Exam", "Cool angular app", "07/01/2016", "07/01/2016");
        
        ProjectUser simon = f.getProjectUser("Simon");
        ProjectUser martin = f.getProjectUser("Martin");
        ProjectUser ib = f.getProjectUser("Ib");
        
        
        List<ProjectUser> pus = f.getAllProjectUsers();
        for (ProjectUser pu : pus) {
            System.out.println(pu.getUserName());
        }
        
        f.assignProjectUserToProject(simon, gameProject);
        f.assignProjectUserToProject(martin, gameProject);
        f.assignProjectUserToProject(ib, gameProject);
        
        f.createTaskAndAssignToProject("animationTask", "Basic animation", 5, 1, gameProject);
    }
}
