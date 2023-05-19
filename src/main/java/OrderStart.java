import item.Item;
import item.parser.CsvParser;
import item.parser.InputScanner;
import item.parser.ItemRepository;
import order.Order;
import user.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderStart {
    private static DecimalFormat dc = new DecimalFormat("###,###,###,###");
    private static final BigDecimal DELIVERY_FEE = new BigDecimal(2500);

    public static void main(String[] args) throws IOException {
        boolean startYn = true;

        ItemRepository itemRepository = new CsvParser();

        List<Item> items = itemRepository.findAll();
        User user = new User(0L, "yeop", new BigDecimal(100_000), BigDecimal.ZERO, new ArrayList<>());

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
            String productId = inputScanner.writeInfo();

            System.out.print("수량 : ");
            String quantity = inputScanner.writeInfo();

            if (productId.isBlank() && quantity.isBlank()) {
                System.out.println("주문내역:");
                System.out.println("--------------------------------------");

                Order order = new Order(user);
                List<Item> orderDetails = order.orderDetail();

                BigDecimal totalPrice = itemRepository.totalPrice(orderDetails, items);
                List<Item> detailInfo = itemRepository.detailInfo(orderDetails,items);

                for (Item item : detailInfo) {
                    System.out.println(item.productName() + " - " + item.stockQuantity() + "개");
                }

                System.out.println("--------------------------------------");
                System.out.println("주문금액: " + dc.format(totalPrice) + "원");

                if (order.checkDelivery(totalPrice)) {
                    System.out.println("배송비: " + dc.format(DELIVERY_FEE) + "원");
                    totalPrice = totalPrice.add(DELIVERY_FEE);
                }
                System.out.println("--------------------------------------");

                if (user.hasSufficientStock(items)) {
                    user = user.payment(totalPrice);
                    System.out.println("지불금액: " + dc.format(totalPrice) + "원");
                    System.out.println("user.money() = " + user.money());
                    System.out.println("--------------------------------------");
                    startYn = true;
                }

            }else{
                user.placeAnOrder(Long.parseLong(productId),Integer.parseInt(quantity));
            }
        }
    }
}