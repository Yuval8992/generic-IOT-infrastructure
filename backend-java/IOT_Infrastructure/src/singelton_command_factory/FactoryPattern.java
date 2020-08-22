package singelton_command_factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FactoryPattern<T, K, A> {
	private Map<K, Function<A ,T>> hashMap = new HashMap<>();
	
	public void add(K key, Function<A ,T> creationFactoryInterface) {
		hashMap.put(key, creationFactoryInterface);
	}
	
	public T create(K key, A args) {
		if (hashMap.get(key) == null) {
			throw new IllegalArgumentException();
		}
		
		return hashMap.get(key).apply(args);
	}
}

