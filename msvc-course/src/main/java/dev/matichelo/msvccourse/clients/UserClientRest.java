package dev.matichelo.msvccourse.clients;

import dev.matichelo.msvccourse.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// url = "${msvc.user.url}"
@FeignClient(name = "msvc-user")
public interface UserClientRest {

    @GetMapping("/{id}")
     User findById(@PathVariable  Long id);

    @PostMapping
     User save(@RequestBody User user);

    @GetMapping("/users-course")
    List<User>  listByIds(@RequestParam Iterable<Long> ids, @RequestHeader("Authorization") String token);
}
