package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != value.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base newBase = new Base(model.getId(), model.getVersion() + 1);
            newBase.setName(model.getName());
            memory.replace(model.getId(), newBase);
            return newBase;
        }) != null;
    }

    public void delete(Base model) {
        memory.entrySet().removeIf(p -> memory.get(model.getId()) != null
                && memory.get(model.getId()).getVersion() == model.getVersion());
    }

    public Base getBase(int key) {
        return memory.get(key);
    }
}
