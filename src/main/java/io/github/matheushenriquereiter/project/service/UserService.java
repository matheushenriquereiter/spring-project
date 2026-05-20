package io.github.matheushenriquereiter.project.service;

import io.github.matheushenriquereiter.project.dto.UserDTO;
import io.github.matheushenriquereiter.project.model.User;
import io.github.matheushenriquereiter.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();

        return userList.stream().map(this::convertToDTO).toList();
    }

    public UserDTO getFirstUser() {
        return convertToDTO(userRepository.getFirstById(1L));
    }

    public UserDTO saveUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO is null");
        }

        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO is null");
        }

        User user = convertToEntity(userDTO);
        User updatedUser = userRepository.save(user);

        return convertToDTO(updatedUser);
    }

    public String deleteUser(int userId) {
        userRepository.deleteById(userId);
        return "User has been deleted";
    }

    private UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        return new User(dto.getId(), dto.getName(), dto.getEmail());
    }
}
