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
import org.springframework.util.StringUtils;

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
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Our Online Shop. Please, visit next link: https://daronlineshop.herokuapp.com/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public void sendOrderMessage(User user) {
        String payed = "";
        List<Usercarts> usercartsLisPayed = userPayedGoods.findByUsername(user.getUsername());
        for (int i = 0; i< usercartsLisPayed.size(); i++){
            Usercarts usercarts = usercartsLisPayed.get(i);
            if (!usercarts.isMailed()){
                if (usercarts.getTitle().equals(user.getUsername())){
                    payed += "Total cost: ";
                    payed += usercarts.getTotalcost();
                }
                else{
                    payed += "Title: ";
                    payed += usercarts.getTitle();
                    payed += "  ";
                    payed += " Cost: ";
                    payed += usercarts.getCost();
                    payed += "  ";
                    payed += " Amount: ";
                    payed += usercarts.getAmount();
                    payed += "  \n";
                }

            }
            usercarts.setMailed(true);
            userPayedGoods.save(usercarts);
        }
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Your order was successful framed. Payed goods : \n" + payed,
                    user.getUsername()
            );

            mailSender.send(user.getEmail(), "Order message", message);
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

        user.setActivationCode(null);

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