package hello.itemservice.web.basic;

import hello.itemservice.domin.item.Item;
import hello.itemservice.domin.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping(value = "/add")
    public String addForm() {
        return "basic/addForm";
    }

    //@PostMapping(value = "/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);


        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    //@PostMapping(value = "/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {

        itemRepository.save(item);
        /*
            @ModelAttribute("item") Item item은 Item객체를 생성하고, 요청 파라미터의 값을
            프로퍼티 접근법으로 입력해준다. 그리고 model.addAttribute("item", item)을 자동으로 수행해준다.

            @ModelAttribute("item") Item item -> 이름을 item으로 지정.
            model.addAttribute("item", item) -> 모델에 item 이름으로 저장.

            model.addAttribute("item", item); // 자동으로 수행되기 때문에 생략이 가능.
         */

        return "basic/item";
    }

    //@PostMapping(value = "/add")
    public String addItemV3(@ModelAttribute Item item) {
        /*
            @ModelAttribute에 이름을 생략할 경우 클래스명 첫글자만 소문자로 하여 등록한다.
            ex) 클래스명이 Young 일 경우 young으로 등록. 위의 경우는 Item -> item
         */
        itemRepository.save(item);
        return "basic/item";
    }

    //@PostMapping(value = "/add")
    public String addItemV4(Item item) {
        /*
            @ModelAttribute를 생략하여 사용하면 기본형 타입일 경우 @RequestParam이 동작한다.
            그 외에는 @ModelAttribute가 동작한다.
         */
        itemRepository.save(item);
        return "basic/item";
    }

    @PostMapping(value = "/add")
    public String addItemV5(Item item) {
        /*
            @ModelAttribute를 생략하여 사용하면 기본형 타입일 경우 @RequestParam이 동작한다.
            그 외에는 @ModelAttribute가 동작한다.
         */
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @GetMapping(value = "/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId); // Get 요청일 때는 제품 상세보기와 거의 유사하다.
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping(value = "/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.updateItem(itemId, item); // ModelAttribute에 item값으로 제품을 수정한다. (요청 파라미터)
        return "redirect:/basic/items/{itemId}"; // 경로변수를 리다이렉트에도 사용할 수 있다.
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
