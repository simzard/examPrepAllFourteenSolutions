/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep9persistenceexampreparationquestion;

import entities.Student;
import entities.Studypoint;
import facades.Facade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author simon
 */
public class EP9PersistenceExamPreparationQuestion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Facade f = new Facade(Persistence.createEntityManagerFactory("EP9PersistenceExamPreparationQuestionPU"));
        
        List<Student> students = f.findAllStudents();
        
        for (Student s : students) {
            System.out.println(s.getFirstname());
        }
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EP9PersistenceExamPreparationQuestionPU");
        
        
        EntityManager em = emf.createEntityManager();
        
        // find all Students in the system with the first name jan
        Query query = em.createQuery("select s from Student s where s.firstname = :fn");
        query.setParameter("fn", "jan");
        students = query.getResultList();
        for (Student s : students) {
            System.out.println(s.getFirstname());
        }
        
        // find all Students in the system with the last name Olsen
        query = em.createQuery("select s from Student s where s.lastname = :ln");
        query.setParameter("ln", "Olsen");
        students = query.getResultList();
        for (Student s : students) {
            System.out.println(s.getLastname());
        }
        
        // find the total sum of study point scores for a student given the student id
        System.out.println(f.findTotalStudyPointSumOfStudent(2));
        
        // find the total sum of study point scores given to all students
        System.out.println(f.findTotalStudyPointSumOfAllStudents());
        
        
        // find those students that has the greatest total of studypoint scores
        List<String> strings = f.findAllStudentsWithLowestTotalScore();
        System.out.println("STUDENTS with greatest total studypoint score:");
        System.out.println("----------------------------------------------");
        
        for (String s : strings) {
            System.out.println(s);
        }
        
        Student simon = new Student("Simon", "Steinaa");
        Studypoint sp = new Studypoint("Semester Project", 80, 50);
        simon.addStudyPoint(sp);
        f.addStudent(simon);
        
        strings = f.findAllStudentsWithGreatestTotalScore();
        System.out.println("STUDENTS with greatest total studypoint score:");
        System.out.println("----------------------------------------------");
        
        for (String s : strings) {
            System.out.println(s);
        }
                
    }
    
    
}
