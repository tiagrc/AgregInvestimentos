package dev.tiago.agregadorInvestimentos.service;

import dev.tiago.agregadorInvestimentos.controller.CreateUserDTO;
import dev.tiago.agregadorInvestimentos.entity.User;
import dev.tiago.agregadorInvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    // Triple array -> Arrange / Act and Assert

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Nested
    class createUser {

        @Test
        @DisplayName("Usuário criado com sucesso!")
        void deveCriarUsuarioComSucesso() {
            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(user).when(userRepository).save(any());

            var input = new CreateUserDTO(
                    "username",
                    "email@email.com",
                    "1234"
            );
        }
    }
}