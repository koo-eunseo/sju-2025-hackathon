package com.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity register(String studentId) {
        return userRepository.findByStudentId(studentId)
                .orElseGet(() ->
                        userRepository.save(
                                UserEntity.builder()
                                        .studentId(studentId)
                                        .build()
                        )
                );
    }
}
