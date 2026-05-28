package com.postgres.demopg.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 40)
    private String rareza;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer costoElixir;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String urlImagen;

    @Column(nullable = false)
    private Long totalLikes = 0L;

    @Column(nullable = false)
    private Long totalDislikes = 0L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TweetReaction> reactions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TweetComment> comments = new ArrayList<>();

    public Tweet() {
    }

    public Tweet(String nombre, String rareza, Integer costoElixir, String urlImagen) {
        this.nombre = nombre;
        this.rareza = rareza;
        this.costoElixir = costoElixir;
        this.urlImagen = urlImagen;
        this.totalLikes = 0L;
        this.totalDislikes = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRareza() {
        return rareza;
    }

    public void setRareza(String rareza) {
        this.rareza = rareza;
    }

    public Integer getCostoElixir() {
        return costoElixir;
    }

    public void setCostoElixir(Integer costoElixir) {
        this.costoElixir = costoElixir;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Long getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Long totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Long getTotalDislikes() {
        return totalDislikes;
    }

    public void setTotalDislikes(Long totalDislikes) {
        this.totalDislikes = totalDislikes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TweetReaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<TweetReaction> reactions) {
        this.reactions = reactions;
    }

    public List<TweetComment> getComments() {
        return comments;
    }

    public void setComments(List<TweetComment> comments) {
        this.comments = comments;
    }
}