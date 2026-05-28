package com.postgres.demopg.payload.response;

import com.postgres.demopg.models.Tweet;

public class TweetResponseDTO {
    private Long id;
    private String nombre;
    private String rareza;
    private Integer costoElixir;
    private String urlImagen;
    private Long totalLikes;
    private Long totalDislikes;
    private String username;

    public TweetResponseDTO(Tweet tweet) {
        this.id = tweet.getId();
        this.nombre = tweet.getNombre();
        this.rareza = tweet.getRareza();
        this.costoElixir = tweet.getCostoElixir();
        this.urlImagen = tweet.getUrlImagen();
        this.totalLikes = tweet.getTotalLikes();
        this.totalDislikes = tweet.getTotalDislikes();
        this.username = tweet.getUser() != null ? tweet.getUser().getUsername() : "";
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}