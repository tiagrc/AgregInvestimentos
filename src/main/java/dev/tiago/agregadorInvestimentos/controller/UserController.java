package dev.tiago.agregadorInvestimentos.controller;

import dev.tiago.agregadorInvestimentos.controller.dto.CreateAccountDto;
import dev.tiago.agregadorInvestimentos.controller.dto.CreateUserDTO;
import dev.tiago.agregadorInvestimentos.controller.dto.UpdateUserDTO;
import dev.tiago.agregadorInvestimentos.entity.User;
import dev.tiago.agregadorInvestimentos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO){
        var userId = userService.createUser(createUserDTO);

        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
        var user = userService.getUserById(userId);

        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId, @RequestBody UpdateUserDTO updateUserDTO){
        userService.updateUserById(userId, updateUserDTO);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable ("userId") String userId){
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> deleteById(@PathVariable ("userId") String userId,
                                           @RequestBody CreateAccountDto createAccountDto){
        userService.createAccount(userId, createAccountDto);
        return ResponseEntity.ok().build();
    }
}
