package hello.itemservice.domin.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest   {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void saveItem() {
        // given
        Item item = new Item("itemA", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(savedItem.getId());

        assertThat(findItem.getId()).isEqualTo(savedItem.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        // given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 20000, 10);

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        // when
        List<Item> findItems = itemRepository.findAll();

        // then
        assertThat(findItems.size()).isEqualTo(2);
        assertThat(findItems).contains(itemA, itemB);

    }

    @Test
    void updateItem() {
        // given
        Item item = new Item("itemA", 10000, 10);

        Item savedItem = itemRepository.save(item);

        Long id = savedItem.getId();

        // when
        Item updateParam = new Item("update", 50000, 50);
        itemRepository.updateItem(id, updateParam);

        // then
        Item findItem = itemRepository.findById(id);

        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

}