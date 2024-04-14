package com.amdiatou.atiservices.ville;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class VilleJDBCDataAccessService implements VilleDao {

    private final JdbcTemplate jdbcTemplate;
    private final VilleRowMapper villeRowMapper;

    public VilleJDBCDataAccessService(JdbcTemplate jdbcTemplate,
                                      VilleRowMapper villeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.villeRowMapper = villeRowMapper;
    }


    @Override
    public List<Ville> selectAllVilles() {
        var sql = """
                SELECT id, nom, code_postal, departement
                FROM ville
                """;

        return jdbcTemplate.query(sql, villeRowMapper);

    }

    @Override
    public Optional<Ville> selectVilleById(Long id) {
        var sql = """
                SELECT id, nom, code_postal, departement
                FROM ville
                WHERE id = ?
                """;
        return jdbcTemplate
                .query(sql, villeRowMapper, id)
                .stream().findFirst();
    }

    @Override
    public void addVille(Ville ville) {
        var sql = """
                INSERT INTO ville (nom, code_postal, departement)
                VALUES (?, ?, ?)
                """;
        int result = jdbcTemplate.update(sql, ville.getNom(), ville.getCodePostal(), ville.getDepartement());
        System.out.println("jdbcTemplate.update = "+ result);
    }

    @Override
    public void updateVille(Ville ville) {
        var sql = """
                UPDATE ville
                SET nom = ?, code_postal = ?, departement = ?
                WHERE id = ?
                """;

        int result = jdbcTemplate.update(sql, ville.getNom(), ville.getCodePostal(), ville.getDepartement(), ville.getId());
        System.out.println("jdbcTemplate.update = "+ result);
    }

    @Override
    public void deleteVilleById(Long id) {
        var sql = """
                DELETE FROM ville
                WHERE id = ?
                """;

        int result = jdbcTemplate.update(sql, id);
        System.out.println("jdbcTemplate.update = "+ result);
    }

    @Override
    public boolean existsByCodePostal(Integer codePostal) {
        var sql = """
                SELECT id, nom, code_postal, departement
                FROM ville
                WHERE code_postal = ?
                """;
        return jdbcTemplate
                .query(sql, villeRowMapper, codePostal)
                .stream().findFirst().isPresent();
    }

    @Override
    public boolean existsById(Long id) {
        var sql = """
                SELECT id, nom, code_postal, departement
                FROM ville
                WHERE id = ?
                """;
        return jdbcTemplate
                .query(sql, villeRowMapper, id)
                .stream().findFirst().isPresent();
    }
}
