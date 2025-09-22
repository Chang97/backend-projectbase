package base.work.user;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService; // final로 불변성 보장

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 진단용
    @GetMapping("/ping")
    public Map<String,Object> ping(){ return Map.of("ok", true); }
    
    // 숫자만 매칭되게 제약
    @GetMapping("/{id:\\d+}")  
    public ResponseEntity<UserDTO> get(@PathVariable("id") Long id){
        var dto = userService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }



}
