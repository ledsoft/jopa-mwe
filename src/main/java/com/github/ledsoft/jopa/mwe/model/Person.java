package com.github.ledsoft.jopa.mwe.model;

import cz.cvut.kbss.jopa.model.annotations.*;

import java.net.URI;
import java.util.Set;

@OWLClass(iri = "http://xmlns.com/foaf/0.1/Person")
public class Person {

    @Id(generated = true)
    private URI uri;

    @OWLDataProperty(iri = "http://xmlns.com/foaf/0.1/firstName")
    private String firstName;

    @OWLDataProperty(iri = "http://xmlns.com/foaf/0.1/lastName")
    private String lastName;

    @OWLObjectProperty(iri = "http://xmlns.com/foaf/0.1/knows")
    private URI friend;

    @Inferred
    @OWLDataProperty(iri = "http://example.org/properties/has-friends")
    private Boolean hasFriends;

    @Types
    private Set<String> types;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public URI getFriend() {
        return friend;
    }

    public void setFriend(URI friend) {
        this.friend = friend;
    }

    public Boolean getHasFriends() {
        return hasFriends;
    }

    public void setHasFriends(Boolean hasFriends) {
        this.hasFriends = hasFriends;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }
}
