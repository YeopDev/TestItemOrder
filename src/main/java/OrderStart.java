import exception.SoldOutException;
import item.Item;
import item.yeopParser.ParserCsvStudyLast;
import order.Order;
import user.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class OrderStart {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        ParserCsvStudyLast parserCsvStudyLast = new ParserCsvStudyLast("items_list.csv");

        List<Map<String,Object>> orderList = new ArrayList<>();
        boolean startYn = true;
        String userFix = "";
        List<Item> items = parserCsvStudyLast.excute();
        Order order = new Order(items);

        while(true){
            if(startYn){
                System.out.print("입력(o[order]: 주문, q[quit]: 종료) :");
                userFix = orderValidationChk(sc.next().toLowerCase());
                if(userFix.equals("q") || userFix.equals("quit")){
                    sc.close();
                    System.out.println(" 종료합니다. ");
                    break;
                }
                System.out.println(" 상품번호  상품명  판매가격  재고수량 ");
                order.itemInfo(items);
                startYn = false;
            }
            Map<String,Object> map = new HashMap<>();
            if(userFix.equals("o") || userFix.equals("order")){
                String productId = order.pickId();
                String quantity = order.pickQuantity();
                if(chkOrderEnd(productId,quantity)){
                    System.out.println("주문내역:");
                    System.out.println("-------------------------------------- ");
                    BigDecimal totalPrice = order.productOrder(orderList);
                    System.out.println("-------------------------------------- ");
                    User yeop = new User(0L,"yeop",new BigDecimal(100000));
                    if(yeop.hasSufficientStock(items,orderList)){
                        yeop.payment(totalPrice);
                        orderList.clear();
                        startYn = true;
                    }
                }
                map.put("상품번호",productId);
                map.put("수량",quantity);
                orderList.add(map);
            }else{
                sc.close();
                System.out.println(" 종료합니다. ");
                break;
            }
        }
    }

    public static String orderValidationChk(String target){
        if(!target.matches("(o|q|order|quit)")){
            throw new IllegalArgumentException("o, order, q, quit 만 입력 가능합니다.");
        }
        return target;
    }

    public static boolean chkOrderEnd(String productId, String quantity){
        if(productId.isBlank() && quantity.isBlank()){
            return true;
        }else{
            return false;
        }
    }
}
