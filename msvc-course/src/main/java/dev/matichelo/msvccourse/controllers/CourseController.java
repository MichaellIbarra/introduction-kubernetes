package dev.matichelo.msvccourse.controllers;

import dev.matichelo.msvccourse.models.User;
import dev.matichelo.msvccourse.models.entity.Course;
import dev.matichelo.msvccourse.services.CourseService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;



    @GetMapping
    public ResponseEntity<?> findAll(){
        // new HashMap es un objeto de tipo Map para almacenar datos en forma de clave y valor
        Map<String, Object> body = new HashMap<>();
        body.put("course", courseService.findAll());
        return ResponseEntity.ok(body);
//        return body;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, @RequestHeader("Authorization") String token){
//        Optional<Course> courseOptional = courseService.findById(id);
        Optional<Course> courseOptional = courseService.listByIds(id, token);
        if(courseOptional.isPresent()){
            return ResponseEntity.ok(courseOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Course course, BindingResult result){
        if (result.hasErrors()){
            return messagesErrors(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result,@PathVariable Long id){

        Optional<Course> courseOptional = courseService.findById(id);

        if (result.hasErrors()){
            return messagesErrors(result);
        }
        if(courseOptional.isPresent()){
            Course courseObj = courseOptional.get();
            courseObj.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseObj));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<Course> courseOptional = courseService.findById(id);
        if(courseOptional.isPresent()){
            courseService.deleteById(courseOptional.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-courser-user/{id}")
    public ResponseEntity<?> deleteCourseUserByUserId(@PathVariable Long id){
        courseService.deleteCourseUserByUserId(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Map<String, String>> messagesErrors(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "The Filed "+ err.getField()+ " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long courseId){
       Optional<User> userOptional = null;
       try{
           userOptional = courseService.assignUser(user, courseId);
       }catch (FeignException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "User not found: "+ e.getMessage()));
       }

       if(userOptional.isPresent()){
           return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
       }
       return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/unassign-user/{courseId}")
    public ResponseEntity<?> unassignUser(@RequestBody User user, @PathVariable Long courseId){
       Optional<User> userOptional = null;
       try{
           userOptional = courseService.unassignUser(user, courseId);
       }catch (FeignException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "User not found: "+ e.getMessage()));
       }

       if(userOptional.isPresent()){
           return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
       }
       return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long courseId){
       Optional<User> userOptional = null;
       try{
           userOptional = courseService.createUser(user, courseId);
       }catch (FeignException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Error creating user: "+ e.getMessage()));
       }

       if(userOptional.isPresent()){
           return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
       }
       return ResponseEntity.notFound().build();
    }

}
