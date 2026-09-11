package sptech.school.nail_api.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("No user exists with this email: " + email);
    }

    public UserNotFoundException(Integer id) {
        super("No user exists with this id: " + id);
    }
}