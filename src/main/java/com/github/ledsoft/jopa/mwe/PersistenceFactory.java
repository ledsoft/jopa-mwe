package com.github.ledsoft.jopa.mwe;

import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.ontodriver.config.OntoDriverProperties;
import cz.cvut.kbss.ontodriver.sesame.SesameDataSource;
import cz.cvut.kbss.ontodriver.sesame.config.SesameOntoDriverProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PersistenceFactory {

    private static boolean initialized = false;

    private static EntityManagerFactory emf;

    private PersistenceFactory() {
        throw new AssertionError();
    }

    public static void init(Map<String, String> overrides) {
        final Properties config = loadConfiguration(overrides);
        final Map<String, String> props = new HashMap<>();
        // Here we set up basic storage access properties - driver class, physical location of the storage
        props.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, config.getProperty("repositoryUrl", "mem:test"));
        props.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS, SesameDataSource.class.getName());
        props.put(JOPAPersistenceProperties.SCAN_PACKAGE, "com.github.ledsoft.jopa.mwe.model");
        // View transactional changes during transaction
        props.put(OntoDriverProperties.USE_TRANSACTIONAL_ONTOLOGY, Boolean.TRUE.toString());
        if (Boolean.parseBoolean(config.getProperty("inMemory", Boolean.toString(false)))) {
            // Use in-memory storage if not remote or local file path specified
            props.put(SesameOntoDriverProperties.SESAME_USE_VOLATILE_STORAGE, Boolean.TRUE.toString());
        }
        // Don't use Sesame inference
        props.put(SesameOntoDriverProperties.SESAME_USE_INFERENCE, Boolean.FALSE.toString());
        // Ontology language
        props.put(JOPAPersistenceProperties.LANG, "en");
        if (overrides != null) {
            props.putAll(overrides);
        }
        // Persistence provider name
        props.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());

        emf = Persistence.createEntityManagerFactory("jopaMWE-PU", props);
        initialized = true;
    }

    private static Properties loadConfiguration(Map<String, String> overrides) {
        final Properties props = new Properties();
        try {
            props.load(PersistenceFactory.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration.", e);
        }
        props.putAll(overrides);
        return props;
    }

    public static EntityManager createEntityManager() {
        if (!initialized) {
            throw new IllegalStateException("Factory has not been initialized.");
        }
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}
