package entity;

import java.util.HashMap;
import java.util.Map;

public enum PaymentMethod {
    CASH, CREDITCARD;
    static final private Map<String,PaymentMethod> ALIAS_MAP = new HashMap<>();
    static {
        ALIAS_MAP.put("CREDITCARD", CREDITCARD);
        ALIAS_MAP.put("CASH", CASH);
    }
    static public PaymentMethod fromString(String method) throws IllegalArgumentException {
        PaymentMethod paymentMethod = ALIAS_MAP.get(method.toUpperCase());
        if (paymentMethod == null)
            throw new IllegalArgumentException("Not a method: "+ method);
        return paymentMethod;
    }
}

