import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {

    private int size = 0;
    private HashSet<K> _keySet;
    private Node node;
    private Node temp;
    private ArrayList<Node<K, V>> _hs;
    private float _loadFactor;
    private int _arraySize;
    private boolean cached;

    public MyHashMap(int initialSize, float loadFactor) {
        this._hs = new ArrayList<Node<K, V>>(initialSize);
        this._arraySize = initialSize;
        this._loadFactor = loadFactor;
        for (int i = 0; i < _arraySize; i++) {
            _hs.add(null);
        }
    }

    public MyHashMap() {
        this(10, 0.75f);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75f);
    }

    public void clear() {
        _hs.clear();
    }

    /* Returns true if this map contains a mapping for the specified key. 
     * Should run on average constant time when called on a HashMap. 
     */
    public boolean containsKey(K key) {
        int code = key.hashCode();
        int position = code % _arraySize;
        // System.out.println("Position: " + position);
        // System.out.println("CONTAINSKEY Array Size: " + _arraySize);
        // System.out.println("Actual Size: " + _hs.size());
        Node doesItContain = _hs.get(position);
        if (doesItContain != null) {
            return true;
        }
        return false;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. Should run on average constant time
     * when called on a HashMap. 
     */
    public V get(K key) {
        LinkedList<V> chain = getChain(key);
        if (chain == null) {
            return null;
        }
        return chain.peekLast();
    }

    private LinkedList<V> getChain(K key) {
        if (key == null) {
            return null;
        }
        // System.out.println("GETCHAIN Array Size: " + _arraySize);
        int code = key.hashCode();
        int position = key.hashCode() % _arraySize;
        Node doesItContain = _hs.get(position);
        // System.out.println("DOESITCONTAIN: " + doesItContain);
        if (doesItContain == null) {
            return null;
        }
        return doesItContain._list;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. 
     * Should run on average constant time when called on a HashMap. */
    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        if (containsKey(key)) {
            LinkedList<V> chain = getChain(key);
            chain.add(value);
        } else {
            Node result = new Node(key, value);
            int code = key.hashCode();
            int position = key.hashCode() % _arraySize;
            _hs.add(position, result);
        }
        size += 1;
        float f = (float) size / _arraySize;
        if (f > _loadFactor) {
            // System.out.println(">>> RESIZING <<<");
            _hs.ensureCapacity(_arraySize * 2);
            _arraySize = _arraySize * 2;
        }
        cached = false;
    }

    /* Removes the mapping for the specified key from this map if present. 
     * Should run on average constant time when called on a HashMap. */
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        if (containsKey(key)) {
            int code = key.hashCode();
            int position = key.hashCode() % _arraySize;
            size -= 1;
            Node<K, V> node = _hs.remove(position);
            LinkedList<V> chain = node._list;
            if (_keySet != null) {
                _keySet.remove(key);
            }
            return chain.peekLast();
        } 
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Should run on average constant time when called on 
     * a HashMap. */
    public V remove(K key, V value) {
        if (key == null) {
            return null;
        }
        LinkedList<V> list = this.getChain(key);
        if (list.remove(value)) {
            return value;
        }
        return null;
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        if (cached) {
            return _keySet;
        }
        _keySet = new HashSet<K>();
        for (Node node : _hs) {
            if (node != null && !_keySet.contains(node._key)) {
                _keySet.add((K) node._key);
            }
        }
        cached = true;
        return _keySet;
    }


    private class Node<K, V> {

        /* Constructor for a node of a HashMap with 
         * KEY/VAL pair. */        
        public Node(K key, V val) {
            _key = key;
            _list.add(val);
            _code = key.hashCode();
        }

        private LinkedList<V> _list = new LinkedList<V>();
        private int _code;
        private K _key;
    }
}