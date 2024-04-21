package com.amdiatou.atiservices.ville;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VilleRowMapper implements RowMapper<Ville> {
    @Override
    public Ville mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Ville(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getInt("code_postal"),
                rs.getString("departement")
        );
    }
}
