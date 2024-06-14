package br.com.kevenaugusto.miniauthorizer.singleton;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public final class TransactionMap {

    private static volatile TransactionMap instance;
    private final HashMap<String, Long> cardLock = new HashMap<>();

    public static TransactionMap getInstance() {
        TransactionMap transactionMap = instance;
        if (transactionMap != null) {
            return transactionMap;
        }
        synchronized (TransactionMap.class) {
            if (instance == null) {
                instance = new TransactionMap();
            }
            return instance;
        }
    }

    public boolean lockCard(String cardNumber) {
        if (cardLock.containsKey(cardNumber)) {
            return false;
        }
        cardLock.put(cardNumber, System.currentTimeMillis());
        return true;
    }

    public Object unlockCard(String cardNumber) {
        return cardLock.remove(cardNumber);
    }

}
