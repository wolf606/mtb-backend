package com.encefalos97.mtb.service;

import com.encefalos97.mtb.dto.UserCreateDTO;
import com.encefalos97.mtb.dto.UserDTO;
import com.encefalos97.mtb.exception.UserExceptions;
import com.encefalos97.mtb.model.Role;
import com.encefalos97.mtb.model.User;
import com.encefalos97.mtb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.encefalos97.mtb.config.AppConfig;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppConfig appConfig;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AppConfig appConfig
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appConfig = appConfig;
    }

    public UserDTO saveUser(UserCreateDTO userCreateDTO, String adminPass) {
        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new UserExceptions.EmailAlreadyExistsException(userCreateDTO.getEmail());
        }

        User user = new User();
        user.setName(userCreateDTO.getName());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        if (userCreateDTO.getRole().equals("ADMIN")) {
            user.setRole( appConfig.getAdminPassword().equals(adminPass) ? Role.ADMIN : Role.USER);
        } else {
            user.setRole(Role.USER);
        }

        User savedUser = userRepository.save(user);

        return new UserDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    public UserDTO getUserById(Long id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new UserExceptions.UserNotFoundException(id));
        return new UserDTO(
                found.getId(),
                found.getName(),
                found.getEmail(),
                found.getRole()
        );
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = userRepository.findAll().stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
        return usersDTO;
    }

    public UserDTO updateUser(Long id, UserCreateDTO userUpdateDTO) {
        if (!userRepository.existsById(id)) {
            throw new UserExceptions.UserNotFoundException(id);
        }

        User user = new User();
        user.setId(id);
        user.setName(userUpdateDTO.getName());
        user.setEmail(userUpdateDTO.getEmail());
        user.setPassword(userUpdateDTO.getPassword());
        user.setRole(Role.valueOf(userUpdateDTO.getRole()));

        User updated = userRepository.save(user);
        return new UserDTO(
                updated.getId(),
                updated.getName(),
                updated.getEmail(),
                updated.getRole()
        );
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserExceptions.UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
