package com.suraj.TaskManager.services;



        import com.suraj.TaskManager.entity.User;
        import com.suraj.TaskManager.entity.UserPrincipal;
        import com.suraj.TaskManager.repository.UserRepository;
        import org.springframework.beans.factory.annotation.Autowired;


        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.stereotype.Service;



@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= repo.findByUsername(username);

        if (user==null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(user);
    }

}
