import item.Item;
import item.yeopParser.PassingFile;
import order.Order;
import user.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class OrderStart {
    private static final Scanner sc = new Scanner(System.in);
    private static DecimalFormat dc = new DecimalFormat("###,###,###,###");

    private static final BigDecimal DELIVERY_FEE = new BigDecimal(2500);

    public static void main(String[] args) throws IOException {
        PassingFile passingFile = new PassingFile("items_list.csv");
        List<String> passingList = passingFile.passingExcute();

        Order order = new Order();
        boolean startYn = true;
        String userFix = "";
        List<Item> items = order.makeItemList(passingList);

        List<Map<String, Object>> orderList = new ArrayList<>();

        while (true) {
            if (startYn) {
                System.out.print("입력(o[order]: 주문, q[quit]: 종료) :");
                userFix = orderValidationChk(sc.next().toLowerCase());
                if (userFix.equals("q") || userFix.equals("quit")) {
                    sc.close();
                    System.out.println(" 종료합니다. ");
                    break;
                }
                System.out.println(" 상품번호  상품명  판매가격  재고수량 ");
                order.itemInfo(items);
                startYn = false;
            }
            Map<String, Object> map = new HashMap<>();
            if (userFix.equals("o") || userFix.equals("order")) {
                String productId = order.pickId();
                String quantity = order.pickQuantity();
                if (chkOrderEnd(productId, quantity)) {
                    System.out.println("주문내역:");
                    System.out.println("-------------------------------------- ");
                    BigDecimal totalPrice = order.orderTotalPrice(items, orderList);
                    List<Map<String,Object>> itemView = order.orderItemView(items, orderList);
                    for(Map<String,Object> key : itemView){
                        System.out.println(key.get("상품이름") + " - " + key.get("수량") + "개");
                    }
                    System.out.println("-------------------------------------- ");
                    System.out.println("주문금액: " + dc.format(totalPrice) + "원");
                    if (order.chkDelivery(totalPrice)) {
                        System.out.println("배송비: " + dc.format(DELIVERY_FEE) + "원");
                        totalPrice = totalPrice.add(DELIVERY_FEE);
                    }
                    System.out.println("-------------------------------------- ");
                    User yeop = new User(0L, "yeop", new BigDecimal(100000));
                    if (yeop.hasSufficientStock(items, orderList)) {
                        yeop.payment(totalPrice);
                        System.out.println("지불금액: " + dc.format(totalPrice) + "원");
                        System.out.println("-------------------------------------- ");
                        orderList.clear();
                        startYn = true;
                    }
                }
                map.put("상품번호", productId);
                map.put("수량", quantity);
                orderList.add(map);
            } else {
                sc.close();
                System.out.println(" 종료합니다. ");
                break;
            }
        }
    }

    public static String orderValidationChk(String target) {
        if (!target.matches("(o|q|order|quit)")) {
            throw new IllegalArgumentException("o, order, q, quit 만 입력 가능합니다.");
        }
        return target;
    }

    public static boolean chkOrderEnd(String productId, String quantity) {
        if (productId.isBlank() && quantity.isBlank()) {
            return true;
        } else {
            return false;
        }
    }
}
