package order;

import item.Item;
import user.User;

import java.util.List;
import java.util.stream.Collectors;

public record Order(User user, List<Item> orderItems) {
    private static final int DELIVERY_FEE = 2_500;

    public int calculateTotalPrice(){
        return orderItems().stream()
                .mapToInt(item -> item.price() * item.stockQuantity())
                .sum();
    }

    //bad case
    //hint: I/O -> entity ?
    public String displayOrderList(){
        return orderItems.stream()
                .map(item -> item.name() + " - " + item.stockQuantity() + "개")
                .collect(Collectors.joining("\n"));
    }

    //bad case
    //totalPrice는 Order가 계산하는데 이걸 왜 또 밖에서 받아와야 함? 그렇네?
    public boolean checkDelivery() {
        return calculateTotalPrice() < 50000;
    }

    public int getDeliveryFee(){
        return DELIVERY_FEE;
    }

    //bad bad case
    public User totalAmountPayment(){
        int totalAmountIncludingDeliveryFee = calculateTotalPrice();
        if(checkDelivery()){
            totalAmountIncludingDeliveryFee += DELIVERY_FEE;
        }
        return new User(user.id(),user.name(),user.payment(totalAmountIncludingDeliveryFee),totalAmountIncludingDeliveryFee);
    }
}
