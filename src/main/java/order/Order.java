package order;

import item.Item;
import user.User;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static final int DELIVERY_FEE = 2_500;
    private static final int FREE_DELIVERY_STANDARD_FEE = 50_000;

    private User user;
    private List<Item> items;

    public Order(User user, List<Item> items) {
        if (user == null) {
            throw new IllegalArgumentException("user 값이 없습니다.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("items가 비어있습니다.");
        }
        this.user = user;
        this.items = new ArrayList<>(items);
        user.paymentProgress(totalAmountPayment());
    }

    public int totalPrice() {
        return this.items.stream()
                .mapToInt(item -> item.price() * item.stockQuantity())
                .sum();
    }

    public int totalAmountPayment() {
        int totalPrice = totalPrice();
        if (totalPrice < FREE_DELIVERY_STANDARD_FEE) {
            return totalPrice + DELIVERY_FEE;
        }
        return totalPrice;
    }

    public boolean hasDeliveryFee() {
        return totalPrice() < FREE_DELIVERY_STANDARD_FEE;
    }

    public int deliveryFee() {
        return DELIVERY_FEE;
    }
}
