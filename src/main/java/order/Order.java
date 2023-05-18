package order;

import item.Item;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public record Order(List<OrderDetail> orderDetails, List<Item> items) {

    public BigDecimal totalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);

        for (OrderDetail orderDetail : orderDetails) {
            long productId = orderDetail.productId();
            int quantity = orderDetail.stockQuantity();

            Optional<Item> matchingItem = items.stream().filter(item -> item.productId() == productId).findFirst();
            if (matchingItem.isPresent()) {
                Item item = matchingItem.get();
                BigDecimal price = item.price();
                BigDecimal totalItemPrice = price.multiply(BigDecimal.valueOf(quantity));
                totalPrice = totalPrice.add(totalItemPrice);
            }
        }
        return totalPrice;
    }

    public List<OrderDetail> detailItemInfo() {
        return orderDetails.stream()
                .map(orderDetail -> {
                    long productId = orderDetail.productId();
                    int quantity = orderDetail.stockQuantity();
                    Optional<Item> matchingItem = items.stream().filter(item -> item.productId() == productId).findFirst();
                    if (matchingItem.isPresent()) {
                        Item item = matchingItem.get();
                        return new OrderDetail(item.productId(), item.productName(), item.price(), quantity);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public boolean hasSufficientStock() { //재고수량 체크
        for (OrderDetail orderDetail : orderDetails) {
            long productId = orderDetail.productId();
            int quantity = orderDetail.stockQuantity();

            items.stream()
                    .filter(item -> item.productId().equals(productId))
                    .findFirst()
                    .ifPresent(matchItem -> {
                        matchItem.CheckProductStock(productId, quantity);
                    });
        }
        return true;
    }

    public boolean checkDelivery(BigDecimal totalPrice) {
        return totalPrice.compareTo(new BigDecimal(50000)) < 0;
    }
}
