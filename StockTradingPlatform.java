package INTERNSHIP;

import java.util.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Transaction {
    String type; // "BUY" or "SELL"
    Stock stock;
    int quantity;
    double total;

    public Transaction(String type, Stock stock, int quantity, double total) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.total = total;
    }

    public String toString() {
        return type + " " + quantity + " shares of " + stock.getSymbol() + " at ₹" + stock.getPrice();
    }
}

class User {
    private String name;
    private double balance;
    private Map<String, Integer> portfolio;
    private List<Transaction> history;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new HashMap<>();
        this.history = new ArrayList<>();
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost > balance) {
            System.out.println(" Not enough balance to buy.");
            return;
        }

        balance -= cost;
        portfolio.put(stock.getSymbol(), portfolio.getOrDefault(stock.getSymbol(), 0) + quantity);
        history.add(new Transaction("BUY", stock, quantity, cost));
        System.out.println("Bought " + quantity + " of " + stock.getSymbol());
    }

    public void sellStock(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.getSymbol(), 0);
        if (quantity > owned) {
            System.out.println(" Not enough shares to sell.");
            return;
        }

        double earnings = stock.getPrice() * quantity;
        balance += earnings;
        if (quantity == owned) {
            portfolio.remove(stock.getSymbol());
        } else {
            portfolio.put(stock.getSymbol(), owned - quantity);
        }
        history.add(new Transaction("SELL", stock, quantity, earnings));
        System.out.println(" Sold " + quantity + " of " + stock.getSymbol());
    }

    public void viewPortfolio(Map<String, Stock> market) {
        System.out.println("\n Portfolio of " + name);
        for (String symbol : portfolio.keySet()) {
            int qty = portfolio.get(symbol);
            double value = market.get(symbol).getPrice() * qty;
            System.out.println(symbol + ": " + qty + " shares, Current Value: ₹" + value);
        }
        System.out.println("Balance: ₹" + balance);
    }

    public void viewHistory() {
        System.out.println("\n Transaction History:");
        for (Transaction t : history) {
            System.out.println(t);
        }
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Market stocks
        Map<String, Stock> market = new HashMap<>();
        market.put("TCS", new Stock("TCS", 3200));
        market.put("INFY", new Stock("INFY", 1500));
        market.put("RELIANCE", new Stock("RELIANCE", 2700));

        // Create user
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        User user = new User(name, 10000);

        int choice;
        do {
            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View History");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("\n Current Market:");
                    for (Stock s : market.values()) {
                        System.out.println(s.getSymbol() + " - ₹" + s.getPrice());
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol to BUY: ");
                    String symbolBuy = sc.nextLine().toUpperCase();
                    if (!market.containsKey(symbolBuy)) {
                        System.out.println("Stock not found.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int qtyBuy = sc.nextInt();
                    sc.nextLine();
                    user.buyStock(market.get(symbolBuy), qtyBuy);
                    break;
                case 3:
                    System.out.print("Enter stock symbol to SELL: ");
                    String symbolSell = sc.nextLine().toUpperCase();
                    if (!market.containsKey(symbolSell)) {
                        System.out.println("Stock not found.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int qtySell = sc.nextInt();
                    sc.nextLine();
                    user.sellStock(market.get(symbolSell), qtySell);
                    break;
                case 4:
                    user.viewPortfolio(market);
                    break;
                case 5:
                    user.viewHistory();
                    break;
                case 6:
                    System.out.println("Exiting. Thank you!");
                    break;
                default:
                    System.out.println(" Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}
