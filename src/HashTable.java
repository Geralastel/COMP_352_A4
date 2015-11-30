package hash;

public class HashTable {

    private int entries;
    private int capacity;
    private String[] table;
    private double threshold;
    private double loadFactor;
    private char collisionType;
    private int colfreq;
    private int maxCollisionPerCell;
    private double rehashFactor;

    public HashTable() {
        entries = 0;
        capacity = 101;
        colfreq = 0;
        maxCollisionPerCell = 0;
        loadFactor = 0;
        threshold = 0.85;
        collisionType = 'd';
        rehashFactor = 1.5;
        table = new String[capacity];
    }

    public int colfreq() {
        return colfreq;
    }

    public void put(String key, String value) {
        int code = hashfun(key);
        int initialCode = code;
        int tempMaxCollision = 0;

        if (table[code] == null) {
            //System.out.println("good");
            table[code] = value;
            ++entries;
            //System.out.println(entries);
            loadFactor = (double) entries / capacity;
            if (threshold <= loadFactor) {
                //System.out.println("rehashing1");
                rehash();
            }
        } else {
            colfreq++;
            if (collisionType == 'q') {
                int factor = 1;
                //System.out.println("collide");
                while (table[code] != null) {
                    ++tempMaxCollision;
                    if (table[quadratic(code, factor)] == null) {
                        table[quadratic(code, factor)] = value;
                        ++entries;
                        loadFactor = (double) entries / capacity;
                        if (threshold <= loadFactor) {
                            //System.out.println("rehashing2");
                            rehash();
                        }
                        break;
                    }
                    factor++;
                } //End of while
            } else {
                int increment = doubleHash(code);
                code += increment;
                if (code >= capacity) {
                    code -= capacity;
                }
                while (code != initialCode) {
                    ++tempMaxCollision;
                    if (table[code] == null || table[code].equals("AVAILABLE")) {
                        table[code] = value;
                        ++entries;
                        loadFactor = (double) entries / capacity;
                        if (threshold <= loadFactor) {
                            //System.out.println("rehashing2");
                            rehash();
                        }
                        break;
                    }
                    code += increment;
                    if (code >= capacity) {
                        code -= capacity;
                    }
                }
            }
            if (tempMaxCollision > maxCollisionPerCell) {
                maxCollisionPerCell = tempMaxCollision;
            }
        }
    }

    public int quadratic(int code, int factor) {
        return (code + factor * factor) % capacity;
    }

    public int doubleHash(int hash) {
        return (hash * 31) % capacity;
    }

    public String get(String key) {
        int code = hashfun(key);
        return table[code];
    }

    public void rehash() {
        colfreq = 0;
        entries = 0; //Resetting Entries
        int oldCap = capacity;
        if (rehashFactor == Math.round(rehashFactor)) {
            capacity += rehashFactor;
        } else {
            this.capacity = (int) (capacity * rehashFactor);
        }
        String[] oldTable = new String[capacity];
        for (int i = 0; i < oldCap; i++) {
            if (table[i] != null) {
                oldTable[i] = table[i];
            }
        }

        table = new String[capacity];
        for (int i = 0; i < oldCap; i++) {
            if (oldTable[i] != null) {
                this.put(oldTable[i], oldTable[i]);
            }
        }
    }

    public int entries() {
        return entries;
    }

    public int size() {
        return capacity;
    }

    public void printHashtableStatistics() {
        System.out.println("----------------------");
        System.out.println("Size of Table: " + capacity);
        System.out.println("Collision Type: " + (collisionType == 'q' ? "Quadratic" : "Double Hashing"));
        System.out.println("Number of Elements: " + entries);
        System.out.println("Load Percentage: " + loadFactor);
        System.out.println("Total Collisions: " + colfreq);
        System.out.println("Max Number of Collisions per Cell: " + maxCollisionPerCell);
        System.out.println("Average Collisions: " + (double) maxCollisionPerCell / colfreq);
        System.out.println("----------------------");
    }

    public int hash(String key) {
        int hash = 7;
        for (int i = 0; i < key.length(); i++) {
            hash = hash * 31 + key.charAt(i);
        }
        return hash;
    }

    public int compress(int hash) {
        int val = hash;
        if (val < 0) {
            val = -hash;
        }
        return val % capacity;
    }

    public int hashfun(String key) {
        return compress(hash(key));
    }

    public void remove(String key) {
        int code = hashfun(key);
        if (table[code] != null) {
            if (table[code].equals(key)) { //First entry is a match
                table[code] = "AVAILABLE";
                --entries;
                loadFactor = (double) entries / capacity;
                --colfreq;
            } else {
                if (collisionType == 'q') { //Quadratic
                    int factor = 1;
                    //System.out.println("collide");
                    while (table[code] != null) {
                        if (table[quadratic(code, factor)] != null && table[quadratic(code, factor)].equals(key)) {
                            table[quadratic(code, factor)] = "AVAILABLE";
                            --entries;
                            loadFactor = (double) entries / capacity;
                            --colfreq;
                            break;
                        }
                        factor++;
                    }
                } else { //Double Hashing
                    int initialCode = code;
                    int increment = doubleHash(code);
                    code += increment;
                    if (code >= capacity) {
                        code -= capacity;
                    }
                    while (code != initialCode) {
                        if (table[code] != null && table[code].equals(key)) {
                            table[code] = "AVAILABLE";
                            --entries;
                            loadFactor = (double) entries / capacity;
                            --colfreq;
                            break;
                        }
                        code += increment;
                        if (code >= capacity) {
                            code -= capacity;
                        }
                    }
                }
            }
        } else {
            System.out.println(key + " not found.");
        }
    }

    public void setRehashThreshold(int loadFactor) {
        threshold = loadFactor;
    }

    public void resetHashtableStatistics() {
        entries = 0;
        capacity = 101;
        colfreq = 0;
        maxCollisionPerCell = 0;
        loadFactor = 0;
        threshold = 0.85;
        collisionType = 'q';
        rehashFactor = 1.5;
        table = new String[capacity];
    }

    public void setCollisionHandlingType(char type) {
        collisionType = type;
    }

    public void setRehashFactor(double factor) {
        rehashFactor = factor;
    }
}
