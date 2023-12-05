package by.grsu.les2.controller;

import by.grsu.les2.entity.Item;
import by.grsu.les2.entity.Order;
import by.grsu.les2.entity.User;
import by.grsu.les2.repository.ItemRepository;
import by.grsu.les2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @ModelAttribute(name = "order")
    public Order newOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByEmailIgnoreCase(authentication.getName()).get();

        return Order.builder()
                .customer(user)
                .build();
    }

    @PostMapping("/add/{itemId}")
    public String addOrder(
            @ModelAttribute Order order,
            @PathVariable String itemId
    ){
        Long id = Long.parseLong(itemId);
        Optional<Item> oItem = itemRepository.findById(id);
        Item item = oItem.orElseThrow();
        order.addItem(item, 1L);

        return "redirect:/items";
    }
}
