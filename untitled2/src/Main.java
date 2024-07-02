import java.util.*;

// Enumeration for product type
enum ProductType {
    ELECTRONIC,
    FURNITURE
}

// Product class
class Product {
    private String name;
    private double price;
    private ProductType type;

    public Product(String name, double price, ProductType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Product: " + name + ", Price: " + price + ", Type: " + type;
    }
}

// Interface for product builder
interface ProductBuilder {
    void setName(String name);
    void setPrice(double price);
    void setType(ProductType type);
    Product build();
}

// Electronic product builder
class ElectronicProductBuilder implements ProductBuilder {
    private String name;
    private double price;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(ProductType type) {
        this.name = type.name();
    }

    public Product build() {
        return new Product(name, price, ProductType.ELECTRONIC);
    }
}

// Furniture product builder
class FurnitureProductBuilder implements ProductBuilder {
    private String name;
    private double price;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(ProductType type) {
        this.name = type.name();
    }

    public Product build() {
        return new Product(name, price, ProductType.FURNITURE);
    }
}

// Director class
class ProductDirector {
    private ProductBuilder builder;

    public void setBuilder(ProductBuilder builder) {
        this.builder = builder;
    }

    public Product buildProduct(String name, double price) {
        builder.setName(name);
        builder.setPrice(price);
        return builder.build();
    }
}

// Interface for expression
interface Expression {
    boolean interpret(String context);
}

// Class for interpreting
class PriceExpression implements Expression {
    private double priceThreshold;

    public PriceExpression(double priceThreshold) {
        this.priceThreshold = priceThreshold;
    }

    public boolean interpret(String context) {
        double price = Double.parseDouble(context);
        return price > priceThreshold;
    }
}

// Interface for product printer
interface ProductPrinter {
    void print(Product product);
}

// Adapter for printing list of products
class ProductPrinterAdapter implements ProductPrinter {
    public void print(Product product) {
        System.out.println(product);
    }
}

// Interface for decorator
interface ProductDecoratorInterface {
    void decorate();
}

// Decorator for additional information
class ProductDecorator implements ProductDecoratorInterface {
    private Product product;

    public ProductDecorator(Product product) {
        this.product = product;
    }

    public void decorate() {
        System.out.println("Product Details: " + product);
    }
}

// Iterator for product list
class ProductIterator implements Iterator<Product> {
    private List<Product> products;
    private int position;

    public ProductIterator(List<Product> products) {
        this.products = products;
    }

    public boolean hasNext() {
        return position < products.size();
    }

    public Product next() {
        return products.get(position++);
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();


        ProductDirector director = new ProductDirector();
        ProductBuilder electronicBuilder = new ElectronicProductBuilder();
        ProductBuilder furnitureBuilder = new FurnitureProductBuilder();
        Expression isExpensive = new PriceExpression(1000.0);

        for (int i = 1; i < 11; ++i) {
            boolean expensive = isExpensive.interpret(Double.toString(i * 150.0));
            Product product;
            if (expensive) {
                director.setBuilder(electronicBuilder);
                product = director.buildProduct("Electronics " + i, i * 150.0);
            } else {
                director.setBuilder(furnitureBuilder);
                product = director.buildProduct("Furniture " + i, i * 150.0);
            }
            products.add(product);
        }

        ProductPrinterAdapter printerAdapter = new ProductPrinterAdapter();
        for (Product product : products) {
            printerAdapter.print(product);
        }

        // Using iterator
        ProductIterator iterator = new ProductIterator(products);
        while (iterator.hasNext()) {
            Product product = iterator.next();
            ProductDecorator decorator = new ProductDecorator(product);
            decorator.decorate();
        }
    }
}
