import item.Item;
import item.parser.CsvParser;
import item.parser.InputScanner;
import item.parser.ItemRepository;
import order.Order;
import user.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderStart {
    private static DecimalFormat dc = new DecimalFormat("###,###,###,###");

    public static void main(String[] args) {
        boolean startYn = true;

        ItemRepository itemRepository = new CsvParser();

        List<Item> items = itemRepository.findAll();

        User user = new User(0L, "yeop", 100_000, 0);

        List<Item> orderItems = new ArrayList<>();

        InputScanner inputScanner = new InputScanner();

        while (true) {
            String userFix = null;

            if (startYn) {
                System.out.print("입력(o[order]: 주문, q[quit]: 종료) :");
                userFix = inputScanner.checkOrderValidation();
                if (userFix.equals("q") || userFix.equals("quit")) {
                    System.out.println(" 종료합니다. ");
                    break;
                }
                System.out.println(" 상품번호  상품명  판매가격  재고수량 ");
                items.forEach(raw -> System.out.println(raw.itemInfo()));
                startYn = false;
            }

            System.out.print("상품번호 : ");
            String id = inputScanner.writeInfo();

            System.out.print("수량 : ");
            String quantity = inputScanner.writeInfo();

            if (id.isBlank() && quantity.isBlank()) {
                orderItems = itemRepository.changeItemList(orderItems);

                Order order = new Order(user,orderItems);
                user = order.totalAmountPayment();

                items = itemRepository.updateItems(orderItems);

                System.out.println("주문내역:");
                System.out.println("--------------------------------------");
                System.out.println(order.displayOrderList());
                System.out.println("--------------------------------------");
                System.out.println("주문금액: " + dc.format(order.calculateTotalPrice()) + "원");
                if(order.checkDelivery()) System.out.println("배송비: " + dc.format(order.getDeliveryFee()) + "원");
                System.out.println("--------------------------------------");

                if (itemRepository.hasSufficientStock(orderItems)) {
                    System.out.println("지불금액: " + dc.format(user.amount()) + "원");
                    System.out.println("user의 소지금: " + dc.format(user.money()) + "원");
                    System.out.println("--------------------------------------");
                    orderItems.clear();
                    startYn = true;
                }

            } else {
                orderItems.add(new Item(Long.parseLong(id), "0", 0, Integer.parseInt(quantity)));
            }
        }
    }
}