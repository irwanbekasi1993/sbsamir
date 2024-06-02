package sbsamir.controller.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import sbsamir.model.Role;
import sbsamir.model.User;
import sbsamir.payload.request.UserRoleView;
import sbsamir.payload.response.MessageResponse;
import sbsamir.repository.RoleRepository;
import sbsamir.repository.UserRepository;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/sbsamir/test/v1")
public class UserController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


@PostMapping("/insertRole")
    public ResponseEntity<?> insertRole(@RequestBody Role role){
        boolean flag = roleRepository.findByName(role.getName()).isPresent();
        if(!flag){
            Role cekRole = roleRepository.save(role);
            return ResponseEntity.ok(cekRole); 
        }
        return ResponseEntity.ok(new MessageResponse("Role Already Exists")); 
        
    }

    @PutMapping("/updateRole/{name}")
    public ResponseEntity<?> updateRole(@RequestBody Role role,@PathVariable("name") String name){
        boolean flag = roleRepository.findByName(role.getName()).isPresent();
        if(flag){
            Role cekRole = roleRepository.save(role);
            return ResponseEntity.ok(cekRole); 
        }
        return ResponseEntity.ok(new MessageResponse("Role Not Found")); 
     
    }

    @DeleteMapping("/deleteRole/{name}")
    public ResponseEntity<?> deleteRole(@PathVariable("name") String name){
        Role cekRole = roleRepository.findByName(name).get();
        if(cekRole!=null){
            roleRepository.delete(cekRole);
        }
        return ResponseEntity.ok(new MessageResponse("role successfully deleted"));
    }

    @RequestMapping(value = "/role/{username}",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> viewUserRole(@PathVariable("username")String username){
        String cekR = roleRepository.getRoleName(username);
        UserRoleView userRoleView = new UserRoleView();
        if(userRepository.existsByUsername(username)==true){
            userRoleView.setUsername(username);
            userRoleView.setRoleName(cekR);
        }
        if(userRepository.existsByUsername(username)==false){
            return ResponseEntity.ok(new MessageResponse("username not found"));
        }
        return ResponseEntity.ok(new MessageResponse(userRoleView));
    }

}
