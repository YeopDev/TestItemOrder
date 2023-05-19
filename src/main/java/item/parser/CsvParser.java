package item.parser;

import exception.SoldOutException;
import item.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CsvParser implements ItemRepository {
    private static final String DEFAULT_PATH = "./src/main/resources";
    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    private final String fileName = "items_list.csv";
    private List<Item> items;

    @Override
    public List<Item> findAll() {
        this.items = read().stream()
                .skip(1)
                .map(s -> s.split(REGEX))
                .map(row -> new Item(Long.parseLong(row[0]), row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3])))
                .toList();
        return items;
    }

    //??
    @Override
    public List<Item> changeItemList(List<Item> orderItems) {
        return orderItems.stream()
                .flatMap(orderItem -> this.items.stream()
                        .filter(item -> item.id() == orderItem.id())
                        .map(item -> new Item(item.id(), item.name(), item.price(), orderItem.stockQuantity())))
                .collect(Collectors.toList());
    }

    //??
    @Override
    public List<Item> updateItems(List<Item> orderItems) {
        return items.stream()
                .map(item -> {
                    Optional<Item> matchingOrderItem = orderItems.stream()
                            .filter(orderItem -> orderItem.id() == item.id())
                            .findFirst();
                    if (matchingOrderItem.isPresent()) {
                        int orderQuantity = matchingOrderItem.get().stockQuantity();
                        int updatedStockQuantity = item.stockQuantity() - orderQuantity;
                        return new Item(item.id(), item.name(), item.price(), updatedStockQuantity);
                    } else {
                        return item;
                    }
                })
                .collect(Collectors.toList());
    }

    //?????
    @Override
    public boolean hasSufficientStock(List<Item> orderItems) {
        return orderItems.stream()
                .allMatch(orderItem -> {
                    long id = orderItem.id();
                    int quantity = orderItem.stockQuantity();
                    Optional<Item> matchingItem = items.stream().filter(item -> item.id().equals(id)).findFirst();
                    return matchingItem.map(item -> {
                        checkProductStock(item.id(), quantity);
                        return item.stockQuantity() >= quantity;
                    }).orElse(false);
                });
    }

    @Override
    public void checkProductStock(long productId, int quantity) {
        Optional<Item> matchingItem = items.stream().filter(item -> item.id() == productId).findFirst();
        if (matchingItem.isPresent()) {
            Item item = matchingItem.get();
            int stockQuantity = item.stockQuantity();
            if (stockQuantity < quantity) {
                throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
            }
        }
    }

    private List<String> read() {
        try {
            return Files.readAllLines(Paths.get(DEFAULT_PATH + "/" + fileName));
        } catch (IOException e) {
            //Exception -> Concrete Exception
            throw new RuntimeException(e);
        }
    }
}