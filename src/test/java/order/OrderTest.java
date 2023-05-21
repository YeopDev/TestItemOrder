package order;

import item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import user.User;

import java.util.ArrayList;
import java.util.List;

class OrderTest {

    @Test
    @DisplayName("생성자에 올바른 값이 들어왔을 경우")
    void constructorNoThrownBy() {
        Assertions.assertThatCode(
                () -> {
                    List<Item> items = new ArrayList<>();
                    Item item1 = new Item(14L, "무설탕 프로틴 초콜릿 틴볼스", 12900, 3);
                    items.add(item1);
                    Item item2 = new Item(15L, "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 24900, 2);
                    items.add(item2);

                    User user = new User(0L, "yeop", 10_000_000);

                    Order order = new Order(user, items);
                }
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("생성자에 올바르지 않은 user 값이 들어왔을 경우")
    void constructorUserThrownBy() {
        Assertions.assertThatThrownBy(
                () -> {
                    List<Item> items = new ArrayList<>();
                    Item item1 = new Item(14L, "무설탕 프로틴 초콜릿 틴볼스", 12900, 3);
                    items.add(item1);
                    Item item2 = new Item(15L, "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 24900, 2);
                    items.add(item2);
                    User user = null;
                    Order order = new Order(user, items);
                }
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("user 값이 없습니다.");
    }

    @Test
    @DisplayName("생성자에 올바르지 않은 items 값이 들어왔을 경우")
    void constructorItemsThrownBy() {
        Assertions.assertThatThrownBy(
                () -> {
                    List<Item> items = new ArrayList<>();
                    User user = new User(0L, "yeop", 10_000_000);
                    Order order = new Order(user, items);
                }
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("items가 비어있습니다.");
    }

    @Test
    @DisplayName("주문에 총 금액을 구하는 경우")
    void orderMethodTotalPriceNoThrowBy() {
        Assertions.assertThatCode(
                () -> {
                    List<Item> items = new ArrayList<>();
                    Item item1 = new Item(14L, "무설탕 프로틴 초콜릿 틴볼스", 12_900, 3);
                    items.add(item1);
                    Item item2 = new Item(15L, "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 24_900, 2);
                    items.add(item2);

                    User user = new User(0L, "yeop", 10_000_000);

                    Order order = new Order(user, items);

                    int totalPrice = order.totalPrice();
                    Assertions.assertThat(totalPrice).isEqualTo(88_500);
                }
        ).doesNotThrowAnyException();
    }
}