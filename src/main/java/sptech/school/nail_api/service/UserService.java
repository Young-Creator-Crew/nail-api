package sptech.school.nail_api.service;

import org.springframework.stereotype.Service;
import sptech.school.nail_api.dto.user.UpdateRequest;
import sptech.school.nail_api.dto.user.UserResponse;
import sptech.school.nail_api.exception.UserAlreadyExistsException;
import sptech.school.nail_api.exception.UserNotFoundException;
import sptech.school.nail_api.model.User;
import sptech.school.nail_api.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getProfile(Integer id) {
        User user = userRepository.findById(id);

        if (user == null) { throw new UserNotFoundException(id); }

        return new UserResponse(user.getId(), user.getEmail(), user.getUsername());
    }

    public UserResponse updateUser(UpdateRequest request, Integer id) {
        User user = userRepository.findById(id);

        if (user == null) { throw new UserNotFoundException(id); }

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            User duplicateEmail = userRepository.findByEmail(request.getEmail());
            if (duplicateEmail != null) {
                throw new UserAlreadyExistsException();
            }
            user.setEmail(request.getEmail());
        }

        if (request.getName() != null && !request.getName().equals(user.getUsername())) {
            user.setUsername(request.getName());
        }

        userRepository.update(user);

        return new UserResponse(user.getId(), user.getEmail(), user.getUsername());
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.remove(id);
    }
}
