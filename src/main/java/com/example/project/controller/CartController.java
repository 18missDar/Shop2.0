package com.example.project.controller;


import com.example.project.domain.*;
import com.example.project.repository.*;
import com.example.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public void updateList(Cart myCart, Item item) {
        List<Item> items = myCart.getItems();
        items.add(item);
        itemRepo.save(item);
        myCart.setItems(items);
    }

    public void processofAddtoCart(User user, Goods good) {
        Optional<Cart> cart = cartRepo.findByUser(user);
        Cart myCart;
        if (cart.isPresent()) {
            myCart = cart.get();
            Item newItem = myCart.find(good);
            if (newItem != null) {
                int size = newItem.getQuantity();
                newItem.setQuantity(size + 1);
                updateList(myCart, newItem);
            } else {
                Item item = new Item(good.getId(), 1, user);
                updateList(myCart, item);
            }
        } else {
            Item item = new Item(good.getId(), 1, user);
            itemRepo.save(item);
            List<Item> items = new ArrayList<>();
            items.add(item);
            myCart = new Cart(items, user);
        }
        cartRepo.save(myCart);
    }

    @GetMapping("/addc/{id}")
    public String addCartFromCategories(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        Goods good = goodRepo.findById(id).get();
        String categ = good.getCategory();
        processofAddtoCart(user, good);
        return "redirect:/main/category?category=" + categ;
    }

    @GetMapping("/add/{id}")
    public String addCartFromMain(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        Goods good = goodRepo.findById(id).get();
        processofAddtoCart(user, good);
        return "redirect:/main";
    }

    public List<GoodsInCart> saveGoodInCart(Cart myCart) {
        List<GoodsInCart> messages = new ArrayList<>();
        for (Item item : myCart.getItems()) {
            Goods good = goodRepo.findById(item.getGoodID()).get();
            GoodsInCart goods = new GoodsInCart(good.getTitle(), good.getCost(), item.getQuantity(), good.getId());
            goodsInCartRepository.save(goods);
            messages.add(goods);
        }
        return messages;
    }

    @GetMapping("/cart")
    public String showCart(@AuthenticationPrincipal User user, Model model) {
        Optional<Cart> cart = cartRepo.findByUser(user);
        List<GoodsInCart> messages = new ArrayList<>();
        if (cart.isPresent()) {
            messages = saveGoodInCart(cart.get());
        }
        model.addAttribute("messages", messages);
        return "cart";
    }

    @GetMapping("/deleteitem/{id}")
    public String deletefromCart(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal User user,
                                 Model model) {

        Goods good = goodRepo.findById(id).get();
        Cart cart = cartRepo.findByUser(user).get();

        Item newItem = cart.find(good);
        itemRepo.deleteById(newItem.getId());
        List<Item> items = cart.getItems();

        cart.getItems().removeIf(item -> item.getId().equals(newItem.getId()));

        cart.setItems(items);
        cartRepo.save(cart);
        return "redirect:/cart";
    }

    private List<Usercarts> findPayedCarts(User user) {
        String userName = user.getUsername();
        return userPayedGoods.findByUsername(userName).stream()
                .filter(Usercarts::isPayed)
                .filter(cart -> !userName.equals(cart.getTitle()))
                .collect(Collectors.toList());
    }

    private boolean isFilterApplicable(Usercarts usercarts, String filter) {
        return StringUtils.isNotBlank(filter) ? usercarts.getUsername().equals(filter) : true;
    }

    @GetMapping("/usercarts")
    public String showUserCarts(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        List<Usercarts> userCartsList = userSevice.findAll().stream()
                .map(this::findPayedCarts)
                .flatMap(Collection::stream)
                .filter(userCarts -> isFilterApplicable(userCarts, filter))
                .collect(Collectors.toList());
        model.addAttribute("messages", userCartsList);
        return "usercarts";
    }

    public double formationOfPayedGoodsAndTotalSum(Cart myCart, String userName) {
        double sum = 0;
        for (Item item : myCart.getItems()) {
            Goods good = goodRepo.findById(item.getGoodID()).get();
            if (good.isActive()) {
                sum += Double.valueOf(good.getCost()) * Double.valueOf(item.getQuantity());
                Usercarts usercarts = new Usercarts(userName, good.getTitle(), good.getCost(), item.getQuantity().toString());
                userPayedGoods.save(usercarts);
            }
        }
        Usercarts usercarts = new Usercarts(userName, userName, userName, userName);
        usercarts.setTotalcost(Double.toString(sum));
        userPayedGoods.save(usercarts);
        return sum;
    }

    public List<GoodsInCart> formationofCheckoutGoods(Cart myCart) {
        List<GoodsInCart> messages = new ArrayList<>();
        for (Item item : myCart.getItems()) {
            Goods good = goodRepo.findById(item.getGoodID()).get();
            if (good.isActive()) {
                GoodsInCart goods = new GoodsInCart(good.getTitle(), good.getCost(), item.getQuantity(), good.getId());
                messages.add(goods);
            }
        }
        return messages;
    }

    @GetMapping("/checkout")
    public String showGoodsInOrder(@AuthenticationPrincipal User user, Model model) {
        Optional<Cart> cart = cartRepo.findByUser(user);
        List<GoodsInCart> messages = new ArrayList<>();
        double sum = 0;
        if (cart.isPresent()) {
            sum = formationOfPayedGoodsAndTotalSum(cart.get(), user.getUsername());
            messages = formationofCheckoutGoods(cart.get());
        }

        model.addAttribute("messages", messages);
        model.addAttribute("sum", sum);
        return "showorder";
    }


    @GetMapping("/pay")
    public String pay(@AuthenticationPrincipal User user) {
        List<Usercarts> usercartsLisPayed = userPayedGoods.findByUsername(user.getUsername());
        for (int i = 0; i < usercartsLisPayed.size(); i++) {
            usercartsLisPayed.get(i).setPayed(true);
            userPayedGoods.save(usercartsLisPayed.get(i));
        }
        userSevice.sendOrderMessage(user);
        return "pay";
    }

}

