package com.amdiatou.atiservices.ville;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "ville",
        uniqueConstraints = {
                @UniqueConstraint(name = "ville_code_postal_unique", columnNames = "code_postal")
        }
)
public class Ville {

    @Id
    @SequenceGenerator(
            name = "ville_id_seq",
            sequenceName = "ville_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ville_id_seq")
    private Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false, unique = true)
    private Integer codePostal;
    @Column(nullable = false)
    private String departement;

    public Ville() {
    }

    public Ville(Long id, String nom, Integer codePostal, String departement) {
        this.id = id;
        this.nom = nom;
        this.codePostal = codePostal;
        this.departement = departement;
    }

    public Ville(String nom, Integer codePostal, String departement) {
        this.nom = nom;
        this.codePostal = codePostal;
        this.departement = departement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        if (!(o instanceof Ville ville)) return false;
        return Objects.equals(getId(), ville.getId()) && Objects.equals(getNom(), ville.getNom()) && Objects.equals(getCodePostal(), ville.getCodePostal()) && Objects.equals(getDepartement(), ville.getDepartement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNom(), getCodePostal(), getDepartement());
    }

    @Override
    public String toString() {
        return "Ville{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", codePostal=" + codePostal +
                ", departement='" + departement + '\'' +
                '}';
    }
}
