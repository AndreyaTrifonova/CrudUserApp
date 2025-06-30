package com.example.CrudUserApp.service;

import com.example.CrudUserApp.dto.UserDto;
import com.example.CrudUserApp.model.User;
import com.example.CrudUserApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        return dto;
    }

    private User mapToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User savedUser = userRepository.save(mapToEntity(userDto));
        return mapToDto(savedUser);
    }

    @Override
    public UserDto getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public Page<UserDto> getAllUsers(String search, int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<User> userPage;

        if (search != null && !search.trim().isEmpty()) {
            userPage = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(search, search, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }

        return userPage.map(this::mapToDto);
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existing.setFirstName(userDto.getFirstName());
        existing.setLastName(userDto.getLastName());
        existing.setDateOfBirth(userDto.getDateOfBirth());
        existing.setPhoneNumber(userDto.getPhoneNumber());
        existing.setEmail(userDto.getEmail());

        return mapToDto(userRepository.save(existing));
    }

    @Override
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
