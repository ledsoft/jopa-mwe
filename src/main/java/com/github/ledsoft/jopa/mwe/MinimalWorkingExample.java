package com.github.ledsoft.jopa.mwe;

import cz.cvut.kbss.jopa.model.EntityManager;

import java.util.Collections;

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
        // TODO Implement as needed
        final EntityManager em = PersistenceFactory.createEntityManager();
        System.out.println("Works perfectly!");
    }
}
