package com.example.project.controller;


import com.example.project.domain.*;
import com.example.project.repository.*;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private GoodsRepository goodRepo;

    @Autowired
    private GoodsInCartRepository goodsInCartRepository;

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private UserService userSevice;

    @Autowired
    private UserPayedGoods userPayedGoods;



    public void updateList(Cart myCart, Item item){
        List<Item> items = myCart.getItems();
        items.add(item);
        itemRepo.save(item);
        myCart.setItems(items);
    }

    @GetMapping("/add/{id}")
    public String addBasket(@PathVariable("id") Long id, @AuthenticationPrincipal User user){
        Goods good = goodRepo.findById(id).get();
        String categ = good.getCategory();
        Optional<Cart> cart = cartRepo.findByUser(user);
        Cart myCart;
        if (cart.isPresent()){
            myCart = cart.get();
            Item newItem = myCart.find(good);
            if ( newItem != null){
                int size = newItem.getQuantity();
                newItem.setQuantity(size+1);
                updateList(myCart, newItem);
            }
            else{
                Item item = new Item(good.getId(), 1, user);
                updateList(myCart, item);
            }
        }
        else{
            Item item = new Item(good.getId(), 1, user);
            itemRepo.save(item);
            List<Item> items = new ArrayList<>();
            items.add(item);
            myCart = new Cart(items, user);
        }
        cartRepo.save(myCart);
        return "redirect:/main/category?category=" + categ;
    }

    @GetMapping("/cart")
    public String cart(@AuthenticationPrincipal User user, Model model){
        Optional<Cart> cart = cartRepo.findByUser(user);
        List<GoodsInCart> messages = new ArrayList<>();
        if (cart.isPresent()){
            Cart myCart = cart.get();
            for (Item item:myCart.getItems()){
                Goods good = goodRepo.findById(item.getGoodID()).get();
                GoodsInCart goods = new GoodsInCart(good.getTitle(), good.getCost(), item.getQuantity(), good.getId());

                goodsInCartRepository.save(goods);
                messages.add(goods);
            }
            model.addAttribute("messages", messages);
        }
        else{
            model.addAttribute("messages", new ArrayList<>());
        }
        return "cart";
    }

    @GetMapping("/deleteitem/{id}")
    public String deletefromCart(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal User user,
                                 Model model){
        Goods good = goodRepo.findById(id).get();
        Cart cart = cartRepo.findByUser(user).get();
        Item newItem = cart.find(good);
        itemRepo.deleteById(newItem.getId());
        List<Item> items = cart.getItems();
        for (int i= 0; i<items.size(); i++){
            if (items.get(i).getId() == newItem.getId()){
                items.remove(i);
            }
        }
        cart.setItems(items);
        cartRepo.save(cart);
        return "redirect:/cart";
    }

    @GetMapping("/usercarts")
    public String showUserCarts(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        List<User> users = userSevice.findAll();

        List<Usercarts> usercartsList = new ArrayList<>();

        for (int i =0; i< users.size(); i++){
            User usr = users.get(i);
            List<Usercarts> usercartsLisPayed = userPayedGoods.findByUsername(usr.getUsername());
            for (int j = 0; j< usercartsLisPayed.size(); j++){
                if (usercartsLisPayed.get(j).isPayed()){
                    usercartsList.add(usercartsLisPayed.get(j));
                }
            }

        }


        if (filter != null && !filter.isEmpty()){
            List<Usercarts> currentList = new ArrayList<>();
            for (int i=0; i<usercartsList.size();i++){
                if (usercartsList.get(i).getUsername().equals(filter)) {
                    currentList.add(usercartsList.get(i));
                }
            }
            model.addAttribute("messages", currentList);
        }
        else{
            model.addAttribute("messages", usercartsList);
        }

        return "usercarts";
    }

    @GetMapping("/checkout")
    public String showGoodsInOrder(@AuthenticationPrincipal User user, Model model){
        Optional<Cart> cart = cartRepo.findByUser(user);
        List<GoodsInCart> messages = new ArrayList<>();
        double sum = 0;
        if (cart.isPresent()){
            Cart myCart = cart.get();
            for (Item item:myCart.getItems()){
                Goods good = goodRepo.findById(item.getGoodID()).get();
                if (good.isActive()){
                    GoodsInCart goods = new GoodsInCart(good.getTitle(), good.getCost(), item.getQuantity(), good.getId());
                    sum += Double.valueOf(good.getCost()) * Double.valueOf(item.getQuantity());
                    Usercarts usercarts = new Usercarts(user.getUsername(),good.getTitle(),good.getCost(), item.getQuantity().toString());
                    userPayedGoods.save(usercarts);
                    goodsInCartRepository.save(goods);
                    messages.add(goods);
                }
            }
            Usercarts usercarts = new Usercarts(user.getUsername(), user.getUsername(), user.getUsername(), user.getUsername());
            usercarts.setTotalcost(Double.toString(sum));
            userPayedGoods.save(usercarts);
            model.addAttribute("messages", messages);
            model.addAttribute("sum", sum);
        }
        else{
            model.addAttribute("messages", new ArrayList<>());
            model.addAttribute("sum", sum);
        }
        return "showorder";
    }


    @GetMapping("/pay")
    public String pay(@AuthenticationPrincipal User user){
        List<Usercarts> usercartsLisPayed = userPayedGoods.findByUsername(user.getUsername());
        for (int i = 0; i< usercartsLisPayed.size(); i++){
            usercartsLisPayed.get(i).setPayed(true);
            userPayedGoods.save(usercartsLisPayed.get(i));
        }
        userSevice.sendOrderMessage(user);
        return "pay";
    }


}
