package dk.au.userservice.utils;

import dk.au.userservice.dto.UserDTO;
import dk.au.userservice.model.PaymentOptions;
import dk.au.userservice.model.User;
import dk.au.userservice.model.UserRole;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getName(),
                user.getBirth(),
                user.getRole().name(),  // Enum zu String konvertieren
                user.getAdress(),
                user.getPhoneNumber(),
                user.getPayment().name()  // Enum zu String konvertieren
        );
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(
                userDTO.getName(),
                userDTO.getBirth(),
                UserRole.valueOf(userDTO.getRole()),  // String zu Enum konvertieren
                userDTO.getAdress(),
                userDTO.getPhoneNumber(),
                PaymentOptions.valueOf(userDTO.getPayment()) // String zu Enum konvertieren
        );
    }
}