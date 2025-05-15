package dk.au.userservice.controller;

import dk.au.userservice.dto.UserDTO;
import dk.au.userservice.model.User;
import dk.au.userservice.model.UserRole;
import dk.au.userservice.model.PaymentOptions;
import dk.au.userservice.service.UserService;
import dk.au.userservice.utils.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "ID of the user to retrieve") 
            @PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    UserDTO dto = userMapper.toDTO(user);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user. Note: role must be one of: ADMIN, USER, GUEST (case-sensitive). " +
                     "payment must be one of: PAYPAL, SMSPAY, CREDITCARD (case-sensitive)"
    )
    public ResponseEntity<UserDTO> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "User to create",
                required = true,
                content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @Schema(implementation = UserDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        value = """
                        {
                          "name": "Max Mustermann",
                          "birth": "2025-05-15",
                          "role": "USER",
                          "adress": "Birk Centerpark 120",
                          "phoneNumber": 1234567,
                          "payment": "PAYPAL"
                        }
                        """
                    )
                )
            )
            @RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userService.createUser(user);
        UserDTO responseDTO = userMapper.toDTO(savedUser);
        return ResponseEntity.ok(responseDTO);
    }
}