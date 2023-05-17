package order;

import item.Item;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public record Order() {
    private static final Scanner sc = new Scanner(System.in);

    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    public Order {

    }

    public void itemInfo(List<Item> items) {
        items.forEach(raw -> System.out.println(raw.itemInfo()));
    }

    public String pickId() {
        System.out.print("상품번호 : ");
        return sc.nextLine();
    }

    public String pickQuantity() {
        System.out.print("수량 : ");
        return sc.nextLine();
    }

    public List<Item> makeItemList(List<String> passingList) {
        return IntStream.range(1, passingList.size())
                .mapToObj(index -> byRaw(passingList.get(index).split(REGEX)))
                .collect(Collectors.toList());
    }

    public Item byRaw(String... raws) {
        return new Item(
                Long.parseLong(raws[0]),
                raws[1],
                new BigDecimal(raws[2]),
                Integer.parseInt(raws[3])
        );
    }

    public BigDecimal orderTotalPrice(List<Item> itmes, List<Map<String, Object>> orderList) {
        if (orderList.isEmpty()) {
            throw new IllegalArgumentException("주문리스트가 비어있습니다.");
        }
        BigDecimal totalPrice = new BigDecimal(0);

        for (Map<String, Object> order : orderList) {
            long productId = Long.parseLong(order.get("상품번호").toString());
            int quantity = Integer.parseInt(order.get("수량").toString());

            Optional<Item> matchingItem = itmes.stream().filter(item -> item.productId() == productId).findFirst();

            if (matchingItem.isPresent()) {
                Item item = matchingItem.get();
                BigDecimal price = item.price();
                BigDecimal totalItemPrice = price.multiply(BigDecimal.valueOf(quantity));
                totalPrice = totalPrice.add(totalItemPrice);
            }
        }
        return totalPrice;
    }

    public List<Map<String, Object>> orderItemView(List<Item> itmes, List<Map<String, Object>> orderList) {
        if (orderList.isEmpty()) {
            throw new IllegalArgumentException("주문리스트가 비어있습니다.");
        }
        List<Map<String, Object>> viewList = new ArrayList<>();
        for (Map<String, Object> order : orderList) {
            long productId = Long.parseLong(order.get("상품번호").toString());
            int quantity = Integer.parseInt(order.get("수량").toString());
            Optional<Item> matchingItem = itmes.stream().filter(item -> item.productId() == productId).findFirst();
            if (matchingItem.isPresent()) {
                Item item = matchingItem.get();
                String productName = item.productName();
                Map<String, Object> map = new HashMap<>();
                map.put("상품이름", productName);
                map.put("수량", quantity);
                viewList.add(map);
            }
        }
        return viewList;
    }

    public boolean chkDelivery(BigDecimal totalPrice) {
        if (totalPrice.compareTo(new BigDecimal(50000)) < 0) {
            return true;
        } else {
            return false;
        }
    }

}
