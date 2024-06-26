package com.librarymanagementsystem.service;

import com.librarymanagementsystem.model.User;
import com.librarymanagementsystem.repository.UserRepository;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUserById(){

        User user = Instancio.of(User.class)
                .set(Select.field(User::getName), "Guilherme")
                .set(Select.field(User::getEmail), "guilherme@gmail.com")
                        .create();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(1L);

        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals("Guilherme", retrievedUser.getName());
        Assertions.assertEquals("guilherme@gmail.com", retrievedUser.getEmail());
    }

    @Test
    public void testGetUsersById_UserNotFound(){
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User userTeste = userService.getUserById(2L);

        Assertions.assertNull(userTeste);
    }

    @Test
    public void testRegisterUser(){
        User userToRegister = Instancio.of(User.class)
                        .set(Select.field(User::getName), "Guilherme")
                        .set(Select.field(User::getEmail), "gui@gmail.com")
                        .set(Select.field(User::getRole), "Cliente")
                                .create();

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocation ->{
                    User user = invocation.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        User registeredUser = userService.register(userToRegister);

        Assertions.assertNotNull(registeredUser.getId(), "ID do usuário não pode ser nulo");
        Assertions.assertEquals("Guilherme", registeredUser.getName(), "Nome de usuário registrado incorretamente");
        Assertions.assertEquals("gui@gmail.com", registeredUser.getEmail(), "Email não registrado direito");
        Assertions.assertEquals("Cliente", registeredUser.getRole(), "não registado corretamente");
    }

    @Test
    public void testGetAllUsers() {

        Stream<User> userStream = Instancio.stream(User.class).limit(10);

        List<User> expectedUsers = userStream.collect(Collectors.toList());

        Mockito.when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAllUsers();

        Assertions.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testUpdateUser() {
        User userOld = Instancio.of(User.class)
                .set(Select.field(User::getId), 1L) // Define o ID para 1L
                .set(Select.field(User::getName), "Guilherme")
                .set(Select.field(User::getEmail), "guilherme@gmail.com")
                .set(Select.field(User::getRole), "Cliente")
                .create();

        User userNew = Instancio.of(User.class)
                .set(Select.field(User::getId), 1L) // Define o ID para 1L
                .set(Select.field(User::getName), "Guilherme ATT")
                .set(Select.field(User::getEmail), "guilhermenovo@gmail.com")
                .set(Select.field(User::getRole), "Cliente")
                .create();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userOld));
        Mockito.when(userRepository.save(userOld)).thenReturn(userNew);

        User result = userService.updateUser(userNew);

        Assertions.assertNotNull(result, "Updated user cannot be null");
        Assertions.assertEquals("Guilherme ATT", result.getName(), "Incorrect updated username");
        Assertions.assertEquals("guilhermenovo@gmail.com", result.getEmail(), "Incorrect updated user email");
    }

    @Test
    public void testDeleteUser(){
        User user = Instancio.create(User.class);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).deleteById(1L);

        boolean isDeleted = userService.deleteUser(1L);

        Assertions.assertTrue(isDeleted, "User should have be deleted");
            Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }

}