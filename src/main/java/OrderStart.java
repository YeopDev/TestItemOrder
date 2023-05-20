import item.Item;
import item.parser.CsvParser;
import item.parser.ItemRepository;
import order.Order;
import user.User;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderStart {
    private static DecimalFormat dc = new DecimalFormat("###,###,###,###");
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        ItemRepository itemRepository = new CsvParser();
        User user = new User(0L, "yeop", 10_000_000);

        while (true) {
            System.out.print("입력(o[order]: 주문, q[quit]: 종료) :");
            if (List.of("q", "quit").contains(inputCommend())) {
                System.out.println(" 종료합니다. ");
                break;
            }
            List<Item> items = itemRepository.findAll();
            System.out.println(" 상품번호  상품명  판매가격  재고수량 ");
            items.forEach(item -> System.out.println(item.id() + "\t" + item.name() + "\t" + item.price() + "\t" + item.stockQuantity()));

            List<Item> orderItems = new ArrayList<>();
            while (true) {
                System.out.print("상품번호 : ");
                String id = sc.nextLine();

                System.out.print("수량 : ");
                String quantity = sc.nextLine();

                if (id.isBlank() && quantity.isBlank()) {
                    break;
                }

                Item item = itemRepository.findById(Long.parseLong(id))
                        .orElseThrow(() -> new IllegalArgumentException("상품 번호 잘못댐"));

                orderItems.add(item.checkStockQuantity(Integer.parseInt(quantity)));
            }

            Order order = new Order(user, orderItems);
            orderItems.forEach(itemRepository::updateItems);

            System.out.println("""
                    주문 내역:
                    --------------------------------------
                    %s
                    --------------------------------------
                    주문 금액: %s원
                    %s
                    --------------------------------------
                    지불 금액: %s원
                    소지 금액: %s원
                    """.strip().formatted(
                    orderItems.stream().map(item -> "%s - %s개".formatted(item.name(), item.stockQuantity())).collect(Collectors.joining()),
                    dc.format(order.totalPrice()),
                    order.hasDeliveryFee() ? "배송비: %s원".formatted(dc.format(order.deliveryFee())) : "",
                    dc.format(order.totalAmountPayment()),
                    dc.format(user.money()))
            );
        }
    }

    public static String inputCommend() {
        String target = sc.nextLine().toLowerCase();
        if (!target.matches("(o|q|order|quit)")) {
            throw new IllegalArgumentException("o, order, q, quit 만 입력 가능합니다.");
        }
        return target;
    }
}