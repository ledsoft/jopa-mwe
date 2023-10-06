package com.github.ledsoft.jopa.mwe;

import com.github.ledsoft.jopa.mwe.model.Person;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.descriptors.EntityDescriptor;

import java.net.URI;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class MinimalWorkingExample {

    public static void main(String[] args) {
        PersistenceFactory.init(Collections.emptyMap());
        try {
            new MinimalWorkingExample().execute();
        } finally {
            PersistenceFactory.close();
        }
    }

    public void execute() {
        final EntityManager em = PersistenceFactory.createEntityManager();
        final EntityDescriptor descriptor = getEntityDescriptor();
        final Person halsey = new Person();
        halsey.setFirstName("Catherine");
        halsey.setLastName("Halsey");
        final Person keyes = new Person();
        keyes.setUri(URI.create("http://example.org/individuals/jacob-keyes"));
        keyes.setFirstName("Jacob");
        keyes.setLastName("Keyes");
        em.getTransaction().begin();
        em.persist(halsey, descriptor);
        em.persist(keyes, descriptor);
        em.getTransaction().commit();

        final Person pOne = em.find(Person.class, halsey.getUri(), descriptor);
        assertNotNull(pOne);
        assertNull(pOne.getHasFriends());
        em.clear();

        pOne.setFriend(keyes.getUri());
        em.getTransaction().begin();
        em.merge(pOne, descriptor);
        em.getTransaction().commit();

        final Person pTwo = em.find(Person.class, halsey.getUri(), descriptor);
        assertTrue(pTwo.getHasFriends());
        em.clear();
        pTwo.setFriend(null);

        em.getTransaction().begin();
        em.merge(pTwo, descriptor);
        em.getTransaction().commit();

        final Person pThree = em.find(Person.class, halsey.getUri(), descriptor);
        assertNull(pThree.getHasFriends());
    }

    public EntityDescriptor getEntityDescriptor() {
        return new EntityDescriptor(URI.create("http://example.org/graphs/example"));
    }
}
