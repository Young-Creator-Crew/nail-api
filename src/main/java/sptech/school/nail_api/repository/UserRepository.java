package sptech.school.nail_api.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sptech.school.nail_api.exception.DataBaseAccessException;
import sptech.school.nail_api.model.User;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByEmail(String email) {
        String sql = "SELECT id, email, user_password AS password, username FROM Users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Error retrieving user by email: " + email, e);
        }
    }

    public User findById(Integer id) {
        String sql = "SELECT id, email, username FROM Users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Error retrieving user by id: " + id, e);
        }
    }

    public Integer save(User user) {
        String sql = "INSERT INTO Users (email, user_password, username) VALUES (?, ?, ?)";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getUsername());
                return ps;
            }, keyHolder);

            return keyHolder.getKeyAs(Integer.class);
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Error registering the user", e);
        }
    }

    public void update(User user) {
        String sql = "UPDATE Users SET email = ?, username = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, user.getEmail(), user.getUsername(), user.getId());
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Error updating the user", e);
        }
    }

    public void remove(Integer id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Error removing the user", e);
        }
    }

    public Boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) FROM Users WHERE id = ?";
        try {
            Integer count =  jdbcTemplate.queryForObject(sql, Integer.class, id);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Error retrieving user by id: " + id, e);
        }
    }
}