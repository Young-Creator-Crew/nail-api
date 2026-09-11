package sptech.school.nail_api.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.nail_api.dto.user.UpdateRequest;
import sptech.school.nail_api.dto.user.UserResponse;
import sptech.school.nail_api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable Integer id) {
        UserResponse response = userService.getProfile(id);
        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UpdateRequest request, @PathVariable Integer id) {
        UserResponse response = userService.updateUser(request, id);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.status(204).build();
    }

}