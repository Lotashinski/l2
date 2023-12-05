package by.grsu.les2.controller;

import by.grsu.les2.entity.Item;
import by.grsu.les2.entity.Order;
import by.grsu.les2.repository.ItemRepository;
import by.grsu.les2.service.FileStorage;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/items")
public class ItemController {

    private ItemRepository itemRepository;

    private FileStorage fileStorage;


    @GetMapping
    public String items(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "items";
    }

    @GetMapping("/new")
    public String itemsCreate(Model model) {
        model.addAttribute("newItem", new Item());
        return "items_new";
    }

    @PostMapping("/new")
    public String itemsConfirm(Item item,
                               @RequestParam(name = "image1", required = false) MultipartFile image1,
                               @RequestParam(name = "image2", required = false) MultipartFile image2,
                               @RequestParam(name = "image3", required = false) MultipartFile image3,
                               @RequestParam(name = "image4", required = false) MultipartFile image4) throws IOException {
        storeFileAndSetItem(item, image1);
        storeFileAndSetItem(item, image2);
        storeFileAndSetItem(item, image3);
        storeFileAndSetItem(item, image4);

        itemRepository.save(item);
        return "redirect:/items/new";
    }

    private void storeFileAndSetItem(Item item, MultipartFile multipartFile) throws IOException {
        if (null == multipartFile || multipartFile.isEmpty()) {
            return;
        }

        item.addImage(fileStorage.store(multipartFile));
    }
}
