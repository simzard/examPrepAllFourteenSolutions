/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Restaurant;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author simon
 */
public class RestaurantFacade {

    private EntityManagerFactory emf;

    public RestaurantFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManger() {
        return emf.createEntityManager();
    }

    public List<Restaurant> getRestaurants() {
        EntityManager em = emf.createEntityManager();
        List<Restaurant> restaurants = null;
        try {
            Query query = em.createQuery("select r from Restaurant r");
            restaurants = query.getResultList();
        } finally {
            em.close();
        }
        return restaurants;
    }

    public static void main(String[] args) {
        RestaurantFacade rf = new RestaurantFacade(Persistence.createEntityManagerFactory("examORM_Rest_AngularPU"));
        List<Restaurant> restaurants = rf.getRestaurants();
        for (Restaurant r : restaurants) {
            System.out.println("name: " + r.getName());
        }
    }
}
