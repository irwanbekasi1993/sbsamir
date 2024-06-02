package sbsamir.controller.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbsamir.model.RefreshToken;
import sbsamir.model.Role;
import sbsamir.model.User;

import sbsamir.payload.request.LoginRequest;
import sbsamir.payload.request.ResetPasswordRequest;
import sbsamir.payload.request.SignupRequest;
import sbsamir.payload.request.TokenRefreshRequest;
import sbsamir.payload.response.JwtResponse;
import sbsamir.payload.response.MessageResponse;
import sbsamir.payload.response.TokenRefreshResponse;
import sbsamir.repository.RoleRepository;
import sbsamir.repository.UserRepository;

import sbsamir.service.impl.RefreshTokenService;
import sbsamir.service.impl.UserDetailsImpl;
import sbsamir.util.JwtTokenUtil;



@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/sbsamir/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenUtil jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;


 
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
        
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        JwtResponse response = new JwtResponse(jwt,refreshToken.getToken(),userDetails.getId(),userDetails.getUsername(),userDetails.getEmail(),roles);
        
        TokenRefreshRequest requestToken = new TokenRefreshRequest();
        requestToken.setRefreshToken(response.getToken());

        refreshTokenService.findByToken(requestToken.getRefreshToken()).map(refreshTokenService::verifyExpiration);
        User getUser = refreshToken.getUser();
//        if(getUser!=null){
//            kafkaProcessing("signin using username: "+getUser.getUsername());
//        }
        String obtainToken = jwtUtils.generateTokenFromUsername(getUser.getUsername());
        TokenRefreshResponse tokenResponse = new TokenRefreshResponse(obtainToken,requestToken.getRefreshToken(),getUser.getUsername(),getUser.getRoles());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        if(userRepository.existsByUsername(signupRequest.getUsername())){
//            kafkaProcessing("username is exists");
            return ResponseEntity.badRequest().body(new MessageResponse("username is exists"));
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())){
//            kafkaProcessing("email is exists");
            return ResponseEntity.badRequest().body(new MessageResponse("email is exists"));
        }
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles==null){
            Role userRole = roleRepository.findByName(user.getRoles().stream().map(x->x.getName()).toString()).orElseThrow(()-> new RuntimeException("role not found"));
            roles.add(userRole);
        }
        // else{
            // strRoles.forEach(role->{
            //     switch(role){
            //         case "admin": 
            //         Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN.name()).orElseThrow(()-> new RuntimeException("role not found"));
            //         roles.add(adminRole);
            //         break;
            //         case "mod": 
            //         Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR.name()).orElseThrow(()-> new RuntimeException("role not found"));
            //         roles.add(modRole);
            //         break;
            //         default: 
            //         Role userRole = roleRepository.findByName(ERole.ROLE_USER.name()).orElseThrow(()-> new RuntimeException("role not found"));
            //         roles.add(userRole);
                    
            //     }
            // });
        // }
        for(String strRole: strRoles){
            switch(strRole){

                case "ROLE_ASSIGN":
                    Role roleAssign = roleRepository.findByName("ROLE_ASSIGN").get();
                    User userAssign = new User();
                    userAssign.setUsername(signupRequest.getUsername());
                    userAssign.setEmail(signupRequest.getEmail());
                    userAssign.setPassword(encoder.encode(signupRequest.getPassword()));
                    roles.add(roleAssign);
                    break;

                case "ROLE_OWNER":
                    Role roleOwner = roleRepository.findByName("ROLE_OWNER").get();
                    User userOwner = new User();
                    userOwner.setUsername(signupRequest.getUsername());
                    userOwner.setEmail(signupRequest.getEmail());
                    userOwner.setPassword(encoder.encode(signupRequest.getPassword()));
                    roles.add(roleOwner);
                    break;

                default:
                Role role = roleRepository.findByName(strRole).get();
            roles.add(role);
            }
            
        }
        user.setRoles(roles);
        userRepository.save(user);
//        kafkaProcessing("user registered successfully");
        return ResponseEntity.ok(new MessageResponse("user registered successfully"));
    }


        @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPassword){
        boolean flag = userRepository.existsByUsername(resetPassword.getUsername());
        if(!flag){
            return ResponseEntity.ok(new MessageResponse("User not exists"));
        }
            userRepository.resetPassword(encoder.encode(resetPassword.getNewPassword()), resetPassword.getUsername());
            return ResponseEntity.ok(new MessageResponse("Password Changed Successfully"));
        
    }

}
