package dev.tiago.agregadorInvestimentos.service;

import dev.tiago.agregadorInvestimentos.controller.dto.CreateUserDTO;
import dev.tiago.agregadorInvestimentos.controller.dto.UpdateUserDTO;
import dev.tiago.agregadorInvestimentos.entity.User;
import dev.tiago.agregadorInvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    // Triple way -> Arrange / Act and Assert

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

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

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDTO(
                    "username",
                    "email@email.com",
                    "123"
            );

            // Act
            var output = userService.createUser(input);

            // Assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @DisplayName("Houve um erro/Execption")
        @Test
        void deveMostrarExcecaoQuandoHouverErros() {
            // Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO(
                    "username",
                    "email@email.com",
                    "123"
            );

            // Act with Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));

        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Busca por USU via ID com sucesso!")
        void deveBuscarUserPorId() {
            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            // Act
            var output = userService.getUserById(user.getUserId().toString());

            // Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }
        @Test
        @DisplayName("Deve buscar o usuario pelo ID vazio")
        void deveBuscarUserPorIdVazio() {
            // Arrange // Act // Assert

            // Arrange
            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            // Act
            var output = userService.getUserById(userId.toString());

            // Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class listUsers {

        @Test
        @DisplayName("Retornou todos os usuarios")
        void deveRetornarTodosUsuarios() {
            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(userList)
                    .when(userRepository)
                    .findAll();
            // Act
            var output = userService.listUsers();

            // Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }
    
    @Nested
    class deleteById{

        @Test
        @DisplayName("Deve Deletar Usuario quando existir")
        void deveDeletarUsuarioQuandoExistir() {
            // Arrange
            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());
            var userID = UUID.randomUUID();

            // Act
            userService.deleteById(userID.toString());

            // Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userID, idList.get(0));
            assertEquals(userID, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Nao deve Deletar Usuario quando não existir")
        void naoDeveDeletarUsuarioQuandoNaoExistir() {
            // Arrange
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());
            var userID = UUID.randomUUID();

            // Act
            userService.deleteById(userID.toString());

            // Assert
            assertEquals(userID, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1))
                    .existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0))
                    .deleteById(any());
        }
    }

    @Nested
    class updateUserById{

        @Test
        @DisplayName("Buscar user via ID quando existir e username e senha estiverem preenchidos")
        void deveBuscarUserPorIdSeUsernameEPasswordEstiveremPreenchidos() {
            // Arrange
            var updateUserDTO = new UpdateUserDTO(
                    "newUsername",
                    "NewPassword"
            );
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());
            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());
            // Act
            userService.updateUserById(user.getUserId().toString(), updateUserDTO);
            // Assert
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(updateUserDTO.username(), userCaptured.getUsername());
            assertEquals(updateUserDTO.password(), userCaptured.getPassword());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1))
                    .save(user);
        }

        @Test
        @DisplayName("Não deve buscar usuário caso ele não exista")
        void naoDeveBuscarUsuarioCasoEleNaoExista() {
            // Arrange
            var updateUserDTO = new UpdateUserDTO(
                    "newUsername",
                    "NewPassword"
            );
            var userId = UUID.randomUUID();

            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            // Act
            userService.updateUserById(userId.toString(), updateUserDTO);
            // Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());


            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0))
                    .save(any());
        }
    }
}