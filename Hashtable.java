class structNode {
    int dt;
    int num;
    structNode before;
    structNode after;
    structNode(int dt, int num) {
        this.dt = dt;
        this.num = num;
    }
}
class HashTable {
    structNode[] Table;
    int size;
    int n;
    HashTable(int size) {
        this.size = size;
        this.n = 0;
        Table = new structNode[size];
    }
    int hash(int dt) {
        double A = 0.6180339887;
        double fract = A * dt - (int)(A * dt);
        return (int)(size * fract);
    }
    void insert(int dt, int num) {
        int idx = hash(dt);
        structNode newNode = new structNode(dt, num);
        if (Table[idx] == null) {
            Table[idx] = newNode;
        }
        else {
            structNode present = Table[idx];
            while (present.after != null) {
                present = present.after;
            }
            present.after = newNode;
            newNode.before = present;
        }
        n++;
        if (n >= size * 3 / 4) {
            int newsize = size * 2;
            resize(newsize);
        }
    }
    void resize(int newsize) {
        HashTable newTable = new HashTable(newsize);
        for (int i = 0; i < size; i++) {
            structNode present = Table[i];
            while (present != null) {
                newTable.insert(present.dt, present.num);
                structNode temp = present;
                present = present.after;
                temp = null;
            }
        }
        size = newTable.size;
        n = newTable.n;
        Table = newTable.Table;
    }
    int search(int dt) {
        int idx = hash(dt);
        structNode present = Table[idx];
        while (present != null) {
            if (present.dt == dt) {
                return present.num;
            }
            present = present.after;
        }
        return -1;
    }
    void remove(int dt) {
        int idx = hash(dt);
        structNode present = Table[idx];
        while (present != null) {
            if (present.dt == dt) {
                if (present.before == null) {
                    Table[idx] = present.after;
                }
                else {
                    present.before.after = present.after;
                }
                if (present.after != null) {
                    present.after.before = present.before;
                }
                present = null;
                n--;
                if (n <= size / 4) {
                    int newsize = size / 2;
                    resize(newsize);
                }
                return;
            }
            present = present.after;
        }
    }
    void destroy() {
        for (int i = 0; i < size; i++) {
            structNode present = Table[i];
            while (present != null) {
                structNode temp = present;
                present = present.after;
                temp = null;
            }
        }
        Table = null;
    }
    void display() {
        System.out.println("Hash Table:");
        for (int i = 0; i < size; i++) {
            System.out.print("[" + i + "]: ");
            structNode present = Table[i];
            if (present == null) {
                System.out.println("NULL");
            } else {
                while (present != null) {
                    System.out.print("(" + present.dt + ", " + present.num + ") ");
                    present = present.after;
                }
                System.out.println();
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {
        HashTable table = new HashTable(10);
        table.insert(6, 60);
        table.insert(12, 120);
        table.insert(30, 300);
        table.insert(40, 450);
        table.insert(50, 500);
        table.display();
        System.out.println("\nValue at key 40: " + table.search(40));
        table.remove(40);
        System.out.println("\nHash Table after removing value of key 40:");
        table.display();
        table.destroy();
    }
}



OUTPUT:
Hash Table:
[0]: NULL
[1]: NULL
[2]: NULL
[3]: NULL
[4]: (12, 120) 
[5]: (30, 300) 
[6]: NULL
[7]: (6, 60) (40, 450) 
[8]: NULL
[9]: (50, 500) 

Value at key 40: 450

Hash Table after removing value of key 40:
Hash Table:
[0]: NULL
[1]: NULL
[2]: NULL
[3]: NULL
[4]: (12, 120) 
[5]: (30, 300) 
[6]: NULL
[7]: (6, 60) 
[8]: NULL
[9]: (50, 500) 
