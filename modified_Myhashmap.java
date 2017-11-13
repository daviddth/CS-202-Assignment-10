import java.util.LinkedList;

public class modified_Myhashmap {

    static class MyHashMap<K, V> implements MyMap<K, V> {

        private static int DEFAULT_INITIAL_CAPACITY = 4;


        private static int MAXIMUM_CAPACITY = 1 << 30;


        private int capacity;


        private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f;


        private float loadFactorThreshold;


        private int size = 0;


        LinkedList<MyMap.People<K, V>>[] table;


        public MyHashMap() {
            this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
        }


        public MyHashMap(int initialCapacity) {
            this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
        }


        @SuppressWarnings("unchecked")
        public MyHashMap(int initialCapacity, float loadFactorThreshold) {
            
            if (initialCapacity > MAXIMUM_CAPACITY)
                
                this.capacity = MAXIMUM_CAPACITY;
            
            else
                
                this.capacity = trimToPowerOf2(initialCapacity);

            this.loadFactorThreshold = loadFactorThreshold;
            
            table = new LinkedList[capacity];
        }

        @Override
    
        public void clear() {
            
            size = 0;
            
            removeEntries();
        }

        @Override
     
        public boolean containsKey(K key) {
            
            if (get(key) != null)
                
                return true;
            
            else
                
                return false;
        }

        @Override
        
        public boolean containsValue(V value) {
            
            for (int i = 0; i < capacity; i++) {
                
                if (table[i] != null) {
                    
                    LinkedList<People<K, V>> bucket = table[i];
                    
                    for (People<K, V> entry : bucket)
                        
                        if (entry.getValue().equals(value))
                            
                            return true;
                }
            }

            return false;
        }

        @Override

        public java.util.Set<MyMap.People<K, V>> peopleSet() {
            
            java.util.Set<MyMap.People<K, V>> set = new java.util.HashSet<MyMap.People<K, V>>();

            for (int i = 0; i < capacity; i++) {
                
                if (table[i] != null) {
                    
                    LinkedList<People<K, V>> bucket = table[i];
                    
                    for (People<K, V> entry : bucket)
                        
                        set.add(entry);
                }
            }

            return set;
        }

        @Override
     
        public V get(K key) {
            
            int bucketIndex = hash(key.hashCode());
            
            if (table[bucketIndex] != null) {
                
                LinkedList<People<K, V>> bucket = table[bucketIndex];
                
                for (People<K, V> entry : bucket)
                    
                    if (entry.getKey().equals(key))
                        
                        return entry.getValue();
            }

            return null;
        }

        @Override
    
        public boolean isEmpty() {
            
            return size == 0;
        }

        @Override
     
        public java.util.Set<K> keySet() {
            
            java.util.Set<K> set = new java.util.HashSet<K>();

            for (int i = 0; i < capacity; i++) {
                
                if (table[i] != null) {
                    
                    LinkedList<People<K, V>> bucket = table[i];
                    
                    for (People<K, V> entry : bucket)
                        
                        set.add(entry.getKey());
                }
            }

            return set;
        }

        @Override
    
        public V put(K key, V value) {

            if (size >= capacity * loadFactorThreshold) {
                
                if (capacity == MAXIMUM_CAPACITY)
                    
                    throw new RuntimeException("Over maximum capacity");

                rehash();
            }

            int bucketIndex = hash(key.hashCode());

        
            if (table[bucketIndex] == null) {
                table[bucketIndex] = new LinkedList<People<K, V>>();
            }

         
            table[bucketIndex].add(new MyMap.People<K, V>(key, value));

            size++; 

            return value;
        }

        @Override
   
        public void remove(K key) {
            
            int bucketIndex = hash(key.hashCode());

         
            if (table[bucketIndex] != null) {
                
                LinkedList<People<K, V>> bucket = table[bucketIndex];
                
                for (People<K, V> entry : bucket)
                    
                    if (entry.getKey().equals(key)) {
                    
                        bucket.remove(entry);
                        size--;
                        break; 
                    }
            }
        }

        public void removeAll(K key) {
            
            int bucketIndex = hash(key.hashCode());

     
            if (table[bucketIndex] != null) {
                
                LinkedList<People<K, V>> bucket = table[bucketIndex];

                boolean deleted = true;
                
                while(deleted) {
                    
                    deleted = false;
                    
                    for (People<K, V> entry : bucket)
                        
                        if (entry.getKey().equals(key)) {
                        
                            bucket.remove(entry);
                            
                            size--;
                            
                            deleted = true;
                            
                            break;
                        }
                }
            }
        }

        @Override
     
        public int size() {
            
            return size;
        }

        @Override
   
        public java.util.Set<V> values() {
            
            java.util.Set<V> set = new java.util.HashSet<V>();

            for (int i = 0; i < capacity; i++) {
                
                if (table[i] != null) {
                    
                    LinkedList<People<K, V>> bucket = table[i];
                    
                    for (People<K, V> entry : bucket)
                        
                        set.add(entry.getValue());
                }
            }

            return set;
        }

  
        private int hash(int hashCode) {
            
            return supplementalHash(hashCode) & (capacity - 1);
        }

    
        private static int supplementalHash(int s) {
            
            s ^= (s >>> 20) ^ (s >>> 12);
            return s ^ (s >>> 7) ^ (s >>> 4);
        }

   
        private int trimToPowerOf2(int initialCapacity) {
            
            int capacity = 1;
            
            while (capacity < initialCapacity) {
                
                capacity <<= 1;
            }

            return capacity;
        }

     
        private void removeEntries() {
            
            for (int i = 0; i < capacity; i++) {
                
                if (table[i] != null) {
                    
                    table[i].clear();
                }
            }
        }

    
        
        @SuppressWarnings("unchecked")
        
        private void rehash() {
            
            java.util.Set<People<K, V>> set = peopleSet();

            capacity <<= 1;

            table = new LinkedList[capacity];
            size = 0;

            for (People<K, V> entry : set) {

                put(entry.getKey(), entry.getValue());
            }
        }

        @Override
        public String toString() {

            StringBuilder builder = new StringBuilder("[");

            for (int i = 0; i < capacity; i++) {

                if (table[i] != null && table[i].size() > 0)

                    for (People<K, V> entry : table[i])

                        builder.append(entry);
            }

            builder.append("]");

            return builder.toString();
        }
    }

    interface MyMap<K, V> {

        public void clear();


        public boolean containsKey(K key);


        public boolean containsValue(V value);


        public java.util.Set<People<K, V>> peopleSet();


        public V get(K key);


        public boolean isEmpty();


        public java.util.Set<K> keySet();


        public V put(K key, V value);


        public void remove(K key);


        public int size();


        public java.util.Set<V> values();


        public static class People<K, V> {

            K key;

            V value;

            public People(K key, V value) {

                this.key = key;

                this.value = value;
            }

            public K getKey() {

                return key;
            }

            public V getValue() {

                return value;
            }

            @Override
            public String toString() {

                return "[" + key + ", " + value + "]";
            }
        }
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<String, Integer>();
        map.put("David", 90);
        map.put("Michael", 24);
        map.put("Grant", 8);
        map.put("David", 19);
        map.put("Danny", 12);
        map.put("David", 55);
        map.put("Park", 30);


        System.out.println("People in map: " + map);

        System.out.println("The age for " + "Park is " + map.get("Park"));

        System.out.println("Is David in map " + map.containsKey("David"));

        System.out.println("Is age 10 in map " + map.containsValue(33));

        map.remove("David");

        System.out.println("People in map: " + map);

        map.removeAll("David");

        System.out.println("People in map: " + map);

        map.clear();

        System.out.println("People in map: " + map);
    }

}