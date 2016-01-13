/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entities.Employee;
import entities.Grade;
import entities.Person;
import entities.PhDStudent;
import entities.Student;
import facade.Facade;
import javax.persistence.Persistence;

/**
 *
 * @author simon
 */
public class Tester {
    public void test() {
        Facade f = new Facade(Persistence.createEntityManagerFactory("PersistenceExamPreparationQuestion_1PU"));
        
        Student simon = new Student("Simon", "Steinaa", null, 33, false, 1, null);
        Grade gradeProgramming = new Grade("Programming 101", 12);
        Grade gradeMovies = new Grade("Movies 101", 12);
        simon.addGrade(gradeProgramming);
        simon.addGrade(gradeMovies);
        
        f.addPerson(simon);
        Person findMe = f.findPersonByName("Simon Steinaa");
        System.out.println(findMe.getFirstName());
        //Student student = (Student)f.deletePersonByName("Simon");
        
        Student editSimon = new Student(1,"HULA", "StBeinaa", null, 33, false, 7365, null);
        //System.out.println(f.deletePersonByName("Simon").getLastName());
        f.editPerson(editSimon);
        
        PhDStudent boss = new PhDStudent("databases", 1493, 1000000.0f, "40%", "Evil", "Boss", null, 52, true);
        Employee worker = new Employee(1234, 5000f, "%50", "Bob", "Workman", null, 25, false);
        boss.addSupervisee(worker);
        worker.setSupervisor(boss);
        
        f.addPerson(boss);
        f.addPerson(worker);
        
    }
    public static void main(String[] args) {
        new Tester().test();
    }
}
