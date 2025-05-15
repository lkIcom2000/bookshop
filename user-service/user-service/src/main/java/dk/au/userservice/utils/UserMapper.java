package dk.au.userservice.utils;

import dk.au.userservice.dto.UserDTO;
import dk.au.userservice.model.PaymentOptions;
import dk.au.userservice.model.User;
import dk.au.userservice.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getBirth(),
                user.getRole().name(),  // Enum zu String konvertieren
                user.getAdress(),
                user.getPhoneNumber(),
                user.getPayment().name()  // Enum zu String konvertieren
        );
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User(
                userDTO.getName(),
                userDTO.getBirth(),
                UserRole.valueOf(userDTO.getRole()),  // String zu Enum konvertieren
                userDTO.getAdress(),
                userDTO.getPhoneNumber(),
                PaymentOptions.valueOf(userDTO.getPayment()) // String zu Enum konvertieren
        );
        if (userDTO.getId() != null) {
            // If ID is provided, set it (useful for updates)
            user.setId(userDTO.getId());
        }
        return user;
    }
}