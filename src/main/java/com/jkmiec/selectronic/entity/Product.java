package com.jkmiec.selectronic.entity;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Category category;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<Parameters> parameters = new HashSet<>();
    private Boolean state;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<Comment> comment = new HashSet<>();

    public Product() {
    }

    public Product(Category category, Set<Parameters> parameters, Boolean state, Set<Comment> comment) {
        this.category = category;
        this.parameters = parameters;
        this.state = state;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Parameters> getParameters() {
        return parameters;
    }

    public void setParameters(Set<Parameters> parameters) {
        this.parameters = parameters;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Set<Comment> getComment() {
        return comment;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category=" + category +
                ", parameters=" + parameters +
                ", state=" + state +
                ", comment=" + comment +
                '}';
    }
}
