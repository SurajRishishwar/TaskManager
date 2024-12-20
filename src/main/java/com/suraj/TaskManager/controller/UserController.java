package com.suraj.TaskManager.controller;





        import com.suraj.TaskManager.entity.User;
        import com.suraj.TaskManager.services.JwtService;
        import com.suraj.TaskManager.services.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
        import org.springframework.security.core.Authentication;
        import org.springframework.web.bind.annotation.CrossOrigin;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RestController;

        import java.util.HashMap;
        import java.util.Map;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("register")
    public User register(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()){
//            return jwtService.generateToken(user.getUsername());
            Map<String,Object> tokenmap =new HashMap<>();
            tokenmap.put("token",jwtService.generateToken(user.getUsername()));
            return new ResponseEntity<>(tokenmap, HttpStatus.OK);
        }else {
            Map<String, Object> errorCode = new HashMap<>();
            errorCode.put("Error","Login Failed");
            return new ResponseEntity<>(errorCode, HttpStatus.BAD_REQUEST);
        }

    }

}
