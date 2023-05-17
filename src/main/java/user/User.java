package user;

import exception.SoldOutException;
import item.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

public record User(Long id, String name, BigDecimal money) {
    public User {
        if (isNull(id) || id < 0) {
            throw new IllegalArgumentException("id가 올바른 값이 아닙니다.");
        }
        if (isNull(name) || name.equals("")) {
            throw new IllegalArgumentException("이름이 올바른 값이 아닙니다.");
        }
        if (isNull(money)) {
            throw new IllegalArgumentException("소지금이 올바른 값이 아닙니다.");
        }
    }

    public User payment(BigDecimal target) {
        int compareResult = money().compareTo(target);
        if (compareResult < 0) {
            throw new IllegalArgumentException("소지금이 주문금액보다 적습니다.");
        }
        return new User(this.id, this.name, this.money.subtract(target));
    }

    public static boolean hasSufficientStock(List<Item> items, List<Map<String, Object>> orderList) {
        for (Map<String, Object> order : orderList) {
            long productId = Long.parseLong(order.get("상품번호").toString());
            int quantity = Integer.parseInt(order.get("수량").toString());

            Optional<Item> matchItem = items.stream()
                    .filter(item -> item.productId().equals(productId))
                    .findFirst();

            if (matchItem.isPresent()) {
                Item item = matchItem.get();
                if (item.stockQuantity() < quantity) {
                    throw new SoldOutException("재고 수량이 부족합니다.");
                }
            }
        }
        return true;
    }
}
