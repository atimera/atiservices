package com.amdiatou.atiservices.commune;

import java.util.Objects;

public class Commune {

    private Integer id;
    private String nom;
    private Integer codePostal;
    private String departement;

    public Commune() {
    }

    public Commune(Integer id, String nom, Integer codePostal, String departement) {
        this.id = id;
        this.nom = nom;
        this.codePostal = codePostal;
        this.departement = departement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commune commune)) return false;
        return Objects.equals(getId(), commune.getId()) && Objects.equals(getNom(), commune.getNom()) && Objects.equals(getCodePostal(), commune.getCodePostal()) && Objects.equals(getDepartement(), commune.getDepartement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNom(), getCodePostal(), getDepartement());
    }

    @Override
    public String toString() {
        return "Commune{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", codePostal=" + codePostal +
                ", departement='" + departement + '\'' +
                '}';
    }
}
