package com.example.CrudUserApp.service;

import com.example.CrudUserApp.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto createUser(UserDto userDto);
    UserDto getUserById(int id);
    Page<UserDto> getAllUsers(String search, int page, int size, String sortBy, String sortDir);
    UserDto updateUser(int id, UserDto userDto);
    void deleteUser(int id);
}
