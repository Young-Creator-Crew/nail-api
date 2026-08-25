package sptech.school.nail_api.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sptech.school.nail_api.exception.DataBaseAccessException;
import sptech.school.nail_api.model.User;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Error retrieving user by email: " + email, e);
        }
    }

    public void save(User user) {
        String sql = "INSERT INTO Users (email, user_password, username) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, user.getEmail(), user.getUserPassword(), user.getUsername());
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Error registering the user", e);
        }
    }
}