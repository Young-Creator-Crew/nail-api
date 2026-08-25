package sptech.school.nail_api.service;

import org.springframework.stereotype.Service;
import sptech.school.nail_api.dto.LoginRequest;
import sptech.school.nail_api.dto.RegisterRequest;
import sptech.school.nail_api.exception.*;
import sptech.school.nail_api.model.User;
import sptech.school.nail_api.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) { throw new UserNotFoundException(request.getEmail()); }

        if (!user.getUserPassword().equals(request.getPassword())) { throw new InvalidCredentialsException(); }
    }

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()) != null) { throw new UserAlreadyExistsException(); }

        userRepository.save(new User(request.getEmail(), request.getPassword(), request.getName()));
    }
}
