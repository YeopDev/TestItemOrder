package order;

import item.Item;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;


public record Order(List<Item> items) {
    private static final Scanner sc = new Scanner(System.in);
    private static final BigDecimal DELIVERY_FEE = new BigDecimal(2500);

    private static DecimalFormat dc = new DecimalFormat("###,###,###,###");

    public void itemInfo(List<Item> items){
        items.forEach(raw -> System.out.println(raw.itemInfo()));
    }

    public String pickId(){
        System.out.print("상품번호 : ");
        return sc.nextLine();
    }

    public String pickQuantity(){
        System.out.print("수량 : ");
        return sc.nextLine();
    }

    public BigDecimal productOrder(List<Map<String,Object>> orderList){
        if(orderList.isEmpty()){
            throw new IllegalArgumentException("주문리스트가 비어있습니다.");
        }
        BigDecimal totalPrice = new BigDecimal(0);

        for (Map<String, Object> order : orderList) {
            long productId = Long.parseLong(order.get("상품번호").toString());
            int quantity = Integer.parseInt(order.get("수량").toString());

            Optional<Item> matchingItem = items.stream()
                    .filter(item -> item.productId() == productId)
                    .findFirst();

            if (matchingItem.isPresent()) {
                Item item = matchingItem.get();
                String productName = item.productName();
                BigDecimal price = item.price();

                System.out.println(productName + " - " + quantity + "개");
                BigDecimal totalItemPrice = price.multiply(BigDecimal.valueOf(quantity));
                totalPrice = totalPrice.add(totalItemPrice);
            }
        }
        System.out.println("-------------------------------------- ");
        System.out.println("주문금액: " + dc.format(chkDelivery(totalPrice)) +"원");
        return chkDelivery(totalPrice);
    }


    public BigDecimal chkDelivery(BigDecimal totalPrice){
        if (totalPrice.compareTo(new BigDecimal(50000)) < 0) {
            return totalPrice.add(DELIVERY_FEE);
        }else{
         return totalPrice;
        }
    }

}
