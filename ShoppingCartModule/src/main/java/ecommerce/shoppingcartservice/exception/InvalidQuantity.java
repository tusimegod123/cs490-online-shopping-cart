package ecommerce.shoppingcartservice.exception;

public class InvalidQuantity extends  RuntimeException {

    public InvalidQuantity(String message) {
        super(message);
    }
}
