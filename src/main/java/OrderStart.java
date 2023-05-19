import item.Item;
import item.parser.CsvParser;
import item.parser.ItemRepository;
import order.Order;
import user.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderStart {
    private static DecimalFormat dc = new DecimalFormat("###,###,###,###");
    private static final int DELIVERY_FEE = 2_500;

    public static void main(String[] args) {
        boolean startYn = true;

        ItemRepository itemRepository = new CsvParser();

        List<Item> items = itemRepository.findAll();

        User user = new User(0L, "yeop", 10_000_000, 0);

        List<Item> orderList = new ArrayList<>();

        while (true) {
            String userFix = null;

            if (startYn) {
                System.out.print("입력(o[order]: 주문, q[quit]: 종료) :");
                userFix = user.checkOrderValidation();
                if (userFix.equals("q") || userFix.equals("quit")) {
                    System.out.println(" 종료합니다. ");
                    break;
                }
                System.out.println(" 상품번호  상품명  판매가격  재고수량 ");
                items.forEach(raw -> System.out.println(raw.itemInfo()));
                startYn = false;
            }

            System.out.print("상품번호 : ");
            String id = user.writeInfo();

            System.out.print("수량 : ");
            String quantity = user.writeInfo();

            if (id.isBlank() && quantity.isBlank()) {
                System.out.println("주문내역:");
                System.out.println("--------------------------------------");

                Order order = new Order(orderList);
                int totalPrice = order.calculateTotalPrice(itemRepository);
                List<Item> detailInfo = order.displayItems(itemRepository);

                for (Item item : detailInfo) {
                    System.out.println(item.name() + " - " + item.stockQuantity() + "개");
                }

                System.out.println("--------------------------------------");
                System.out.println("주문금액: " + dc.format(totalPrice) + "원");

                if (order.checkDelivery(totalPrice)) {
                    System.out.println("배송비: " + dc.format(DELIVERY_FEE) + "원");
                    totalPrice += DELIVERY_FEE;
                }

                System.out.println("--------------------------------------");

                if (itemRepository.hasSufficientStock(orderList)) {
                    user = user.payment(totalPrice);
                    System.out.println("지불금액: " + dc.format(totalPrice) + "원");
                    System.out.println("yeop의 소지금: " + dc.format(user.money()));
                    System.out.println("--------------------------------------");
                    orderList.clear();
                    startYn = true;
                }

            } else {
                orderList.add(new Item(Long.parseLong(id), "0", 0, Integer.parseInt(quantity)));
            }
        }
    }
}