package TrainTicketingSystem.userManager.user;

import TrainTicketingSystem.exceptions.customExceptions.ForbiddenException;
import TrainTicketingSystem.userManager.user.dto.UserRegistrationReq;
import TrainTicketingSystem.userManager.user.dto.UserResponse;
import TrainTicketingSystem.userManager.user.dto.UserUpdateReq;
import TrainTicketingSystem.utils.CurrentlyLoggedInUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User API.")
public class UserController {

    private final UserService userService;
    private final CurrentlyLoggedInUser loggedInUser;

    public UserController(UserService userService, CurrentlyLoggedInUser loggedInUser) {
        this.userService = userService;
        this.loggedInUser = loggedInUser;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.ok(userService.me());
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(required = false) String role) {
        return ResponseEntity.ok(userService.getAllUsers(role));
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRegistrationReq userReq) {
        UserResponse user = userService.register(userReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping
    public ResponseEntity<UserResponse> editUser(@RequestBody @Valid UserUpdateReq updateReq) {
        return ResponseEntity.ok(userService.editUser(updateReq));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> editUser(@PathVariable Long userId, @RequestBody @Valid UserUpdateReq updateReq) {
        return ResponseEntity.ok(userService.editUser(userId, updateReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}


