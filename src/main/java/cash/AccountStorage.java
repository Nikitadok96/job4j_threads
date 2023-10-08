package cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean rsl = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        boolean rsl = false;
        Optional<Account> findAccount = getById(account.id());
        if (findAccount.isPresent()) {
            delete(findAccount.get().id());
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> account = Optional.empty();
        for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
            if (entry.getKey() == id) {
                account = Optional.of(entry.getValue());
                break;
            }
        }
        return account;
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> fromAccount = getById(fromId);
        Optional<Account> toAccount = getById(toId);
        if (fromAccount.isPresent() && toAccount.isPresent()) {
            if (fromAccount.get().amount() >= amount) {
                Account newFromAccount = new Account(
                        fromAccount.get().id(),
                        fromAccount.get().amount() - amount);
                Account newToAccount = new Account(
                        toAccount.get().id(),
                        toAccount.get().amount() + amount);
                update(newFromAccount);
                update(newToAccount);
                rsl = true;
            }
        }
        return rsl;
    }
}
