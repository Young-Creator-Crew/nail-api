package sptech.school.nail_api.service;

import org.springframework.stereotype.Service;
import sptech.school.nail_api.dto.auth.LoginRequest;
import sptech.school.nail_api.dto.auth.RegisterRequest;
import sptech.school.nail_api.dto.user.UserResponse;
import sptech.school.nail_api.exception.*;
import sptech.school.nail_api.model.User;
import sptech.school.nail_api.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null || !user.getPassword().equals(request.getPassword())) { throw new InvalidCredentialsException(); }

        return new UserResponse(user.getId(), user.getEmail(), user.getUsername());
    }

    public Integer register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()) != null) { throw new UserAlreadyExistsException(); }
        User user = new User(request.getEmail(), request.getPassword(), request.getName());
        return userRepository.save(user);
    }
}
