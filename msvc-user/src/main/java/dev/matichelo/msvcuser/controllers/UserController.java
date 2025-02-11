package dev.matichelo.msvcuser.controllers;

import dev.matichelo.msvcuser.models.entity.User;
import dev.matichelo.msvcuser.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment env;

    @GetMapping("/crash")
    public void crash(){
        ((ConfigurableApplicationContext)applicationContext).close();
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        Map<String, Object> body = new HashMap<>();
//        return ResponseEntity.ok(userService.findAll());
        body.put("text", env.getProperty("config.text"));
        body.put("pod_info", env.getProperty("MY_POD_NAME") + " - " + env.getProperty("MY_POD_IP"));
        body.put("users", userService.findAll());
//                return Collections.singletonMap("users", userService.findAll());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users-course")
    public ResponseEntity<?> listByIds(@RequestParam List<Long> ids){
        return ResponseEntity.ok(userService.listByIds(ids));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@Valid @RequestBody User user, BindingResult result){

        if (result.hasErrors()){
            return messagesErrors(result);
        }
        if(!user.getEmail().isEmpty() && userService.existsByEmail(user.getEmail())){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("message", "Email already exists"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result,@PathVariable Long id){
        Optional<User> userOptional = userService.findById(id);
        if (result.hasErrors()){
            return messagesErrors(result);
        }
        if(userOptional.isPresent()){
            if(!user.getEmail().isEmpty() &&  !user.getEmail().equalsIgnoreCase(userOptional.get().getEmail()) && userService.findByEmail(user.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("message", "Email already exists"));
            }
            User userObj = userOptional.get();
            userObj.setName(user.getName());
            userObj.setEmail(user.getEmail());
//            userObj.setPassword(user.getPassword());
            userObj.setPassword(passwordEncoder.encode(user.getPassword()));
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userObj));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Map<String, String>> messagesErrors(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "The Filed "+ err.getField()+ " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping("/authorized")
    public Map<String, Object> authorized(@RequestParam(name = "code") String code) {
        return Collections.singletonMap("code", code);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginByEmail(@RequestParam(name = "email") String email){
        Optional<User> userOptional = userService.findByEmail(email);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }


}
