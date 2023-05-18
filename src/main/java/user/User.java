package user;

import item.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public record User(Long id, String name, BigDecimal money, BigDecimal amount, List<Item> orderItems) {
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
        if (money.compareTo(amount) < 0) {
            throw new IllegalArgumentException("소지금이 지불금액보다 작습니다.");
        }
    }

    public boolean placeAnOrder(long productId, int quantity) {
        return orderItems().add(new Item(productId, "0", BigDecimal.ZERO, quantity));
    }

    public boolean hasSufficientStock(List<Item> items) {
        for (Item orderDetail : orderItems) {
            long productId = orderDetail.productId();
            int quantity = orderDetail.stockQuantity();
            items.stream()
                    .filter(item -> item.productId().equals(productId))
                    .findFirst()
                    .ifPresent(matchItem -> {
                        matchItem.checkProductStock(items, productId, quantity);
                    });
        }
        return true;
    }

    public User payment(BigDecimal target) {
        // new BigDecimal(0) ->  BigDecimal.ZERO
        return new User(this.id, this.name, this.money.subtract(target), BigDecimal.ZERO, new ArrayList<>());
    }
}
