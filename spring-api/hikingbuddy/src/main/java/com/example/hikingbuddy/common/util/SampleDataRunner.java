package com.example.hikingbuddy.common.util;

// import com.example.shop.cart.Cart;
// import com.example.shop.cart.CartItem;
// import com.example.shop.common.constants.CartStatus;
// import com.example.shop.item.Item;
// import com.example.shop.item.ItemRepository;
// import com.example.shop.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


import java.sql.Date;
import java.util.ArrayList;

import com.example.hikingbuddy.user.UserRepository;
import com.example.hikingbuddy.event.EventRepository;
import com.example.hikingbuddy.friend.Friend;
import com.example.hikingbuddy.friend.FriendRepository;
import com.example.hikingbuddy.event.Event;
import com.example.hikingbuddy.user.User;

@Component
public class SampleDataRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final FriendRepository friendRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public SampleDataRunner(UserRepository userRepository, EventRepository eventRepository, FriendRepository friendRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.friendRepository = friendRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        User admin = new User(
            Long.valueOf(1),
            "admin@admin.com",
            bCryptPasswordEncoder.encode("password"),
            "Admin Adminson",
            new java.util.Date(),
            1,
            "1234567890",
            true,
            "78613",
            "I am an admin",
            "Lots of stuff"
        );
        userRepository.save(admin);

        User someone = new User(
            Long.valueOf(2),
            "someone@someone.com",
            bCryptPasswordEncoder.encode("password"),
            "Someone Adminson",
            new java.util.Date(),
            1,
            "1234567890",
            true,
            "78613",
            "I am an admin",
            "Lots of stuff"
        );
        userRepository.save(someone);

        friendRepository.save(new Friend(admin, someone));

        eventRepository.save(new Event(
            Long.valueOf(1),
            admin,
            new java.util.Date(),
            "Hike @ Mountains",
            "Will be hiking at the mountains",
            "78613"
        ));

        // Item item1 = itemRepository.save(new Item (
        // "Sriracha",
        // "The original Huy Fong sauce",
        // 3.00
        // ));
        // Item item2 = itemRepository.save(new Item (
        // "Tabasco",
        // "An American classic",
        // 5.99
        // ));
        // itemRepository.save(new Item (
        // "Yellowbird Hot Sauce",
        // "It's got a habanero kick",
        // 6.99
        // ));

        // Cart cart = new Cart();
        // cart.setCartStatus(CartStatus.COMPLETE);
        // ArrayList<CartItem> orderItems = new ArrayList<>();
        // orderItems.add(new CartItem(cart, itemRepository.findById(1L).orElseThrow(),
        // 3));
        // orderItems.add(new CartItem(cart, itemRepository.findById(2L).orElseThrow(),
        // 1));
        // cart.setCartItems(orderItems);
        // cartRepository.save(cart);
    }

}
