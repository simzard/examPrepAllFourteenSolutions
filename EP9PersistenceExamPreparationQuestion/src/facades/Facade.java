/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<Student> findAllStudents() {
        EntityManager em = getEntityManager();

        Query query = em.createNamedQuery("Student.findAll");
        return query.getResultList();
    }

    public Long findTotalStudyPointSumOfStudent(int id) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("select sum(s.score) from Studypoint s where s.studentId.id = :id");
        query.setParameter("id", id);

        return (Long) query.getSingleResult();
    }

    public Long findTotalStudyPointSumOfAllStudents() {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("select sum(s.score) from Studypoint s");
        return (Long) query.getSingleResult();
    }

    // helper method: returns a map of student and his total score associated
    public Map<String, Long> findAllStudentsWithTotalScore() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select s.firstname, sum(sp.score) as SUMS"
                + " from Studypoint sp, Student s"
                + " where sp.studentId is not null and s.id = sp.studentId.id"
                + " group by s.firstname");

        List<Object[]> objects = query.getResultList();
        System.out.println("length: " + objects.size());

        Map<String, Long> studentScore = new HashMap();

        for (int i = 0; i < objects.size(); i++) {
            String name = (String) (objects.get(i)[0]);
            long score = (Long) (objects.get(i)[1]);
            studentScore.put(name, score);
            System.out.println(name + ": " + score);

        }

        return studentScore;
    }

    public List<String> findAllStudentsWithGreatestTotalScore() {
        EntityManager em = emf.createEntityManager();

        String sumViewNativeQuery
                = " create or replace view SUMVIEW as"
                + " select s.FIRSTNAME, sum(sp.SCORE) as SCORESUM"
                + " from STUDYPOINT sp, STUDENT s"
                + " where sp.STUDENT_ID is not null and sp.STUDENT_ID = s.ID\n"
                + " group by s.FIRSTNAME";
        em.getTransaction().begin();
        em.createNativeQuery(sumViewNativeQuery).executeUpdate();
        em.getTransaction().commit();

        String nativeQueryString
                = " select FIRSTNAME"
                + " from SUMVIEW"
                + " where SCORESUM = (select max(SCORESUM)"
                + "                   from SUMVIEW)";

        em.getTransaction().begin();
        Query query = em.createNativeQuery(nativeQueryString);
        em.getTransaction().commit();

        List<String> students = new ArrayList();

        for (Object o : query.getResultList()) {
            String value = (String) o;
            students.add((value));
        }

        return students;
    }

    public List<String> findAllStudentsWithLowestTotalScore() {
        EntityManager em = emf.createEntityManager();

        String sumViewNativeQuery
                = " create or replace view SUMVIEW as"
                + " select s.FIRSTNAME, sum(sp.SCORE) as SCORESUM"
                + " from STUDYPOINT sp, STUDENT s"
                + " where sp.STUDENT_ID is not null and sp.STUDENT_ID = s.ID\n"
                + " group by s.FIRSTNAME";
        em.getTransaction().begin();
        em.createNativeQuery(sumViewNativeQuery).executeUpdate();
        em.getTransaction().commit();

        String nativeQueryString
                = " select FIRSTNAME"
                + " from SUMVIEW"
                + " where SCORESUM = (select min(SCORESUM)"
                + "                   from SUMVIEW)";

        em.getTransaction().begin();
        Query query = em.createNativeQuery(nativeQueryString);
        em.getTransaction().commit();

        List<String> students = new ArrayList();

        for (Object o : query.getResultList()) {
            String value = (String) o;
            students.add((value));
        }

        return students;
    }

    public Student addStudent(Student s) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return s;
    }

}
