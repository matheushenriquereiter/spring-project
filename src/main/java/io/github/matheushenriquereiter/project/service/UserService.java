package io.github.matheushenriquereiter.project.service;

import io.github.matheushenriquereiter.project.dto.UserDTO;
import io.github.matheushenriquereiter.project.model.User;
import io.github.matheushenriquereiter.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);

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
        dto.setPassword(user.getPassword());

        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        return new User(dto.getId(), dto.getName(), dto.getEmail(), dto.getPassword());
    }

    /**
     * MÉTODOS DE AUTENTICAÇÃO DE VERDADE AQUI:
     */
    public boolean validarCredenciais(String email, String rawPassword) {
        // 1. Busca o usuário no banco de dados (MySQL) pelo e-mail
        Optional<User> userOptional = userRepository.findByEmail(email);

        // 2. Se não encontrou o usuário, retorna false (Login inválido)
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        // 3. Compara a senha em texto puro com o Hash criptografado do banco
        // O método "matches" faz a matemática do BCrypt para ver se elas batem.
        // NUNCA faça: rawPassword.equals(user.getPassword())
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
