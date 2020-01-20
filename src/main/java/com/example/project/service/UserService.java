package com.example.project.service;


import com.example.project.domain.Role;
import com.example.project.domain.User;
import com.example.project.domain.Usercarts;
import com.example.project.repository.UserPayedGoods;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserPayedGoods userPayedGoods;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    private void sendMessage(User user) {
        if ((!StringUtils.isNotBlank(user.getEmail()))) {
            StringBuilder messages = new StringBuilder();
            messages.append("Hello, ")
                    .append(user.getUsername())
                    .append(" ! \n")
                    .append("Welcome to Our Online Shop. Please, visit next link: https://daronlineshop.herokuapp.com/activate/")
                    .append(user.getActivationCode());

            mailSender.send(user.getEmail(), "Activation code", messages.toString());
        }
    }

    public void sendDiscountMessage(User user, String category, String discount) {
        if ((!StringUtils.isNotBlank(user.getEmail()))) {
            StringBuilder messages = new StringBuilder();
            messages.append("Hello, ")
                    .append(user.getUsername())
                    .append(" ! \n \n")
                    .append("Our store makes a ")
                    .append(discount)
                    .append(" percent discount on category ")
                    .append(category)
                    .append(" products.\n \nWe will be glad to see you in our store. ")
                    .append("Don't miss your chance and visit next link: https://daronlineshop.herokuapp.com/");

            mailSender.send(user.getEmail(), "Sale!!!", messages.toString());
        }
    }

    public void sendOrderMessage(User user) {
        StringBuilder payed = new StringBuilder();
        String thanks = "\n \n" +
                "Thank you for your purchase in our store, we will always collect the best products for you, we are waiting for you again!";
        List<Usercarts> usercartsLisPayed = userPayedGoods.findByUsername(user.getUsername());
        int k = 0;
        for (int i = 0; i< usercartsLisPayed.size(); i++){
            Usercarts usercarts = usercartsLisPayed.get(i);
            if (usercarts.isMailed() == false){
                if (usercarts.getTitle().equals(user.getUsername())){
                    payed.append("Total cost: ")
                            .append(usercarts.getTotalcost())
                            .append("  \n");
                }
                else{
                    k += 1;
                    payed.append(Integer.toString(k))
                            .append(". Title: ")
                            .append(usercarts.getTitle())
                            .append("  \n")
                            .append("    Cost: ")
                            .append(usercarts.getCost())
                            .append("  \n")
                            .append("    Amount: ")
                            .append(usercarts.getAmount())
                            .append("  \n \n");
                }

            }
            usercarts.setMailed(true);
            userPayedGoods.save(usercarts);
        }
        if (!StringUtils.isNotBlank(user.getEmail())) {
            StringBuilder messages = new StringBuilder();
            messages.append("Hello, ")
                    .append(user.getUsername())
                    .append(" ! \n \n")
                    .append("Your order was successful framed. Payed goods : \n \n")
                    .append(payed)
                    .append(thanks);
            mailSender.send(user.getEmail(), "Order message", messages.toString());
        }
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        sendMessage(user);

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(code);

        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }
}