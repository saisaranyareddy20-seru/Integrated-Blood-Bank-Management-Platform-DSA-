import java.util.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

// ============================================================
//  BLOOD BANK DONOR INFORMATION SYSTEM
//  Covers: CO1-CO4 | DS Topics: LL, Stack, Queue, Hash, Sort, Search
//  Interactive Menu Version — runs in VS Code terminal
// ============================================================

// ─────────────────────────────────────────────────
// CO2: ADT — Donor Node for Doubly Linked List
// ─────────────────────────────────────────────────
class DonorNode {
    int donorId;
    String name;
    String bloodGroup;
    int age;
    String contactNumber;
    String city;
    int unitsDonated;
    String lastDonationDate;
    boolean isUrgent;
    DonorNode prev, next;

    public DonorNode(int donorId, String name, String bloodGroup,
                     int age, String contactNumber,
                     String city, int unitsDonated,
                     String lastDonationDate, boolean isUrgent) {
        this.donorId          = donorId;
        this.name             = name;
        this.bloodGroup       = bloodGroup;
        this.age              = age;
        this.contactNumber    = contactNumber;
        this.city             = city;
        this.unitsDonated     = unitsDonated;
        this.lastDonationDate = lastDonationDate;
        this.isUrgent         = isUrgent;
        this.prev = this.next = null;
    }

    @Override
    public String toString() {
        return String.format(
            "| %-6d | %-20s | %-10s | %-3d | %-13s | %-12s | %-5d | %-12s | %-6s |",
            donorId, name, bloodGroup, age,
            contactNumber, city, unitsDonated,
            lastDonationDate, isUrgent ? "YES" : "NO"
        );
    }
}

// ─────────────────────────────────────────────────
// CO2: Doubly Linked List — Primary Donor Registry
// ─────────────────────────────────────────────────
class DonorLinkedList {
    private DonorNode head, tail;
    private int size;

    public DonorLinkedList() { head = tail = null; size = 0; }

    public void insertAtEnd(DonorNode node) {
        if (head == null) { head = tail = node; }
        else { tail.next = node; node.prev = tail; tail = node; }
        size++;
    }

    public void insertAtBeginning(DonorNode node) {
        if (head == null) { head = tail = node; }
        else { node.next = head; head.prev = node; head = node; }
        size++;
    }

    public boolean deleteById(int donorId) {
        DonorNode cur = head;
        while (cur != null) {
            if (cur.donorId == donorId) {
                if (cur.prev != null) cur.prev.next = cur.next; else head = cur.next;
                if (cur.next != null) cur.next.prev = cur.prev; else tail = cur.prev;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public DonorNode searchByName(String name) {
        DonorNode cur = head;
        while (cur != null) {
            if (cur.name.equalsIgnoreCase(name)) return cur;
            cur = cur.next;
        }
        return null;
    }

    public DonorNode searchById(int id) {
        DonorNode cur = head;
        while (cur != null) {
            if (cur.donorId == id) return cur;
            cur = cur.next;
        }
        return null;
    }

    public List<DonorNode> searchByBloodGroup(String bg) {
        List<DonorNode> result = new ArrayList<>();
        DonorNode cur = head;
        while (cur != null) {
            if (cur.bloodGroup.equalsIgnoreCase(bg)) result.add(cur);
            cur = cur.next;
        }
        return result;
    }

    public List<DonorNode> searchByCity(String city) {
        List<DonorNode> result = new ArrayList<>();
        DonorNode cur = head;
        while (cur != null) {
            if (cur.city.equalsIgnoreCase(city)) result.add(cur);
            cur = cur.next;
        }
        return result;
    }

    public void traverseForward() {
        printHeader();
        DonorNode cur = head;
        while (cur != null) { System.out.println(cur); cur = cur.next; }
        printFooter();
    }

    public void traverseBackward() {
        printHeader();
        DonorNode cur = tail;
        while (cur != null) { System.out.println(cur); cur = cur.prev; }
        printFooter();
    }

    public void reverse() {
        DonorNode cur = head;
        while (cur != null) {
            DonorNode temp = cur.prev; cur.prev = cur.next; cur.next = temp;
            cur = cur.prev;
        }
        DonorNode temp = head; head = tail; tail = temp;
    }

    public DonorNode[] toArray() {
        DonorNode[] arr = new DonorNode[size];
        DonorNode cur = head; int i = 0;
        while (cur != null) { arr[i++] = cur; cur = cur.next; }
        return arr;
    }

    public void fromArray(DonorNode[] arr) {
        head = tail = null; size = 0;
        for (DonorNode n : arr) { n.prev = n.next = null; insertAtEnd(n); }
    }

    public int getSize()        { return size; }
    public DonorNode getHead()  { return head; }

    private void printHeader() {
        String line = "+" + "-".repeat(8) + "+" + "-".repeat(22) + "+" + "-".repeat(12) + "+"
                    + "-".repeat(5) + "+" + "-".repeat(15) + "+" + "-".repeat(14) + "+"
                    + "-".repeat(7) + "+" + "-".repeat(14) + "+" + "-".repeat(8) + "+";
        System.out.println(line);
        System.out.printf("| %-6s | %-20s | %-10s | %-3s | %-13s | %-12s | %-5s | %-12s | %-6s |%n",
            "ID","Name","BloodGroup","Age","Contact","City","Units","LastDonation","Urgent");
        System.out.println(line);
    }

    private void printFooter() {
        System.out.println("Total Donors: " + size);
    }
}

// ─────────────────────────────────────────────────
// CO1: Sorting Algorithms
// ─────────────────────────────────────────────────
class DonorSorter {

    public static void bubbleSort(DonorNode[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].donorId > arr[j + 1].donorId) {
                    DonorNode tmp = arr[j]; arr[j] = arr[j+1]; arr[j+1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void selectionSort(DonorNode[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++)
                if (arr[j].name.compareToIgnoreCase(arr[minIdx].name) < 0) minIdx = j;
            DonorNode tmp = arr[minIdx]; arr[minIdx] = arr[i]; arr[i] = tmp;
        }
    }

    public static void insertionSort(DonorNode[] arr) {
        for (int i = 1; i < arr.length; i++) {
            DonorNode key = arr[i]; int j = i - 1;
            while (j >= 0 && arr[j].age > key.age) { arr[j + 1] = arr[j]; j--; }
            arr[j + 1] = key;
        }
    }

    public static void mergeSort(DonorNode[] arr, int l, int r) {
        if (l < r) {
            int mid = (l + r) / 2;
            mergeSort(arr, l, mid); mergeSort(arr, mid + 1, r); merge(arr, l, mid, r);
        }
    }
    private static void merge(DonorNode[] arr, int l, int mid, int r) {
        DonorNode[] L = Arrays.copyOfRange(arr, l, mid + 1);
        DonorNode[] R = Arrays.copyOfRange(arr, mid + 1, r + 1);
        int i = 0, j = 0, k = l;
        while (i < L.length && j < R.length)
            arr[k++] = (L[i].unitsDonated <= R[j].unitsDonated) ? L[i++] : R[j++];
        while (i < L.length) arr[k++] = L[i++];
        while (j < R.length) arr[k++] = R[j++];
    }

    public static void quickSort(DonorNode[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1); quickSort(arr, pi + 1, high);
        }
    }
    private static int partition(DonorNode[] arr, int low, int high) {
        String pivot = arr[high].bloodGroup; int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].bloodGroup.compareToIgnoreCase(pivot) <= 0) {
                i++; DonorNode tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
            }
        }
        DonorNode tmp = arr[i+1]; arr[i+1] = arr[high]; arr[high] = tmp;
        return i + 1;
    }
}

// ─────────────────────────────────────────────────
// CO1: Searching Algorithms
// ─────────────────────────────────────────────────
class DonorSearcher {
    public static int linearSearch(DonorNode[] arr, int id) {
        for (int i = 0; i < arr.length; i++) if (arr[i].donorId == id) return i;
        return -1;
    }
    public static int binarySearch(DonorNode[] arr, int id) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if      (arr[mid].donorId == id) return mid;
            else if (arr[mid].donorId <  id) low  = mid + 1;
            else                             high = mid - 1;
        }
        return -1;
    }
}

// ─────────────────────────────────────────────────
// CO3: Stack — Donation History / Undo
// ─────────────────────────────────────────────────
class DonationHistoryStack {
    private static final int MAX = 100;
    private String[] stack = new String[MAX];
    private int top = -1;

    public void push(String action) {
        if (top == MAX - 1) { System.out.println("  [!] Stack Overflow!"); return; }
        stack[++top] = action;
        System.out.println("  [PUSHED] " + action);
    }

    public String pop() {
        if (isEmpty()) { System.out.println("  [!] Stack Underflow!"); return null; }
        String val = stack[top--];
        System.out.println("  [UNDO] " + val);
        return val;
    }

    public String peek() { return isEmpty() ? null : stack[top]; }
    public boolean isEmpty() { return top == -1; }
    public int size()        { return top + 1;   }

    public void displayAll() {
        System.out.println("\n  === Donation History Stack (Latest First) ===");
        if (isEmpty()) { System.out.println("  [Empty]"); return; }
        for (int i = top; i >= 0; i--)
            System.out.println("  [" + (top - i + 1) + "] " + stack[i]);
        System.out.println("  Stack size: " + size());
    }
}

// ─────────────────────────────────────────────────
// CO3: Circular Queue — Blood Request Processing
// ─────────────────────────────────────────────────
class BloodRequestQueue {
    private static final int CAPACITY = 20;
    private String[] queue = new String[CAPACITY];
    private int front = 0, rear = -1, count = 0;

    public boolean enqueue(String request) {
        if (isFull()) { System.out.println("  [!] Request Queue Full!"); return false; }
        rear = (rear + 1) % CAPACITY; queue[rear] = request; count++;
        System.out.println("  [QUEUED] " + request);
        return true;
    }

    public String dequeue() {
        if (isEmpty()) { System.out.println("  [!] No pending requests!"); return null; }
        String req = queue[front]; front = (front + 1) % CAPACITY; count--;
        System.out.println("  [PROCESSED] " + req);
        return req;
    }

    public boolean isFull()  { return count == CAPACITY; }
    public boolean isEmpty() { return count == 0; }
    public int size()        { return count; }

    public void displayQueue() {
        System.out.println("\n  === Pending Blood Requests (" + count + "/" + CAPACITY + ") ===");
        if (isEmpty()) { System.out.println("  [Empty]"); return; }
        for (int i = 0; i < count; i++)
            System.out.println("  [" + (i+1) + "] " + queue[(front + i) % CAPACITY]);
    }
}

// ─────────────────────────────────────────────────
// CO3: Priority Queue — Emergency Donor Dispatch
// ─────────────────────────────────────────────────
class EmergencyDonorPQ {
    private PriorityQueue<DonorNode> pq =
        new PriorityQueue<>((a, b) -> b.unitsDonated - a.unitsDonated);

    public void add(DonorNode d)  { pq.offer(d); }
    public DonorNode poll()       { return pq.poll(); }
    public boolean isEmpty()      { return pq.isEmpty(); }
    public int size()             { return pq.size(); }

    public void displayTop(int n) {
        System.out.println("\n  === Top " + n + " Emergency Donors (Max-Heap by Units) ===");
        List<DonorNode> tmp = new ArrayList<>();
        int count = Math.min(n, pq.size());
        for (int i = 0; i < count; i++) {
            DonorNode d = pq.poll();
            System.out.printf("  Priority #%d → %-20s [%-4s] %-12s Units: %d%s%n",
                i+1, d.name, d.bloodGroup, d.city, d.unitsDonated, d.isUrgent ? " ⚡URGENT" : "");
            tmp.add(d);
        }
        pq.addAll(tmp);
    }
}

// ─────────────────────────────────────────────────
// CO4: Hash Table — Blood Group Index (Chaining)
// ─────────────────────────────────────────────────
class BloodGroupHashTable {
    private static final int TABLE_SIZE = 16;
    private LinkedList<DonorNode>[] table;

    @SuppressWarnings("unchecked")
    public BloodGroupHashTable() {
        table = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) table[i] = new LinkedList<>();
    }

    private int hash(String bloodGroup) {
        int h = 0;
        for (char c : bloodGroup.toCharArray()) h = (h * 31 + c) % TABLE_SIZE;
        return Math.abs(h);
    }

    public void insert(DonorNode donor) { table[hash(donor.bloodGroup)].add(donor); }

    public List<DonorNode> getByBloodGroup(String bg) {
        List<DonorNode> result = new ArrayList<>();
        for (DonorNode d : table[hash(bg)])
            if (d.bloodGroup.equalsIgnoreCase(bg)) result.add(d);
        return result;
    }

    public boolean delete(int donorId) {
        for (LinkedList<DonorNode> chain : table) {
            Iterator<DonorNode> it = chain.iterator();
            while (it.hasNext()) { if (it.next().donorId == donorId) { it.remove(); return true; } }
        }
        return false;
    }

    public void displayTable() {
        System.out.println("\n  === Hash Table — Blood Group Index (Chaining) ===");
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (!table[i].isEmpty()) {
                System.out.print("  Bucket[" + String.format("%2d", i) + "]: ");
                for (DonorNode d : table[i])
                    System.out.print(d.name + "(" + d.bloodGroup + ") → ");
                System.out.println("NULL");
            }
        }
    }
}

// ─────────────────────────────────────────────────
// CO4: Java Collections — City-wise Donor Map
// ─────────────────────────────────────────────────
class CityDonorMap {
    private HashMap<String, List<DonorNode>> map = new HashMap<>();

    public void addDonor(DonorNode d) {
        map.computeIfAbsent(d.city, k -> new ArrayList<>()).add(d);
    }
    public List<DonorNode> getDonorsByCity(String city) {
        return map.getOrDefault(city, Collections.emptyList());
    }
    public void displayCitySummary() {
        System.out.println("\n  === City-wise Donor Summary ===");
        map.entrySet().stream()
            .sorted(Map.Entry.<String, List<DonorNode>>comparingByValue(
                Comparator.comparingInt(List::size)).reversed())
            .forEach(e -> System.out.printf("  %-14s : %d donor(s)%n", e.getKey(), e.getValue().size()));
    }
    public Set<String> getCities() { return map.keySet(); }
}

// ─────────────────────────────────────────────────
// CO2: Circular Singly Linked List — Inventory Ring
// ─────────────────────────────────────────────────
class BloodInventoryCircular {
    class InventoryNode {
        String bloodGroup; int units; InventoryNode next;
        InventoryNode(String bg, int u) { bloodGroup = bg; units = u; }
    }
    private InventoryNode last; int size;

    public void insert(String bg, int units) {
        InventoryNode n = new InventoryNode(bg, units);
        if (last == null) { n.next = n; last = n; }
        else { n.next = last.next; last.next = n; last = n; }
        size++;
    }

    public void updateUnits(String bg, int delta) {
        if (last == null) return;
        InventoryNode cur = last.next;
        do {
            if (cur.bloodGroup.equalsIgnoreCase(bg)) {
                cur.units = Math.max(0, cur.units + delta);
                System.out.printf("  Updated %s → %d units%n", bg, cur.units);
                return;
            }
            cur = cur.next;
        } while (cur != last.next);
        System.out.println("  Blood group not found.");
    }

    public void traverse() {
        System.out.println("\n  === Blood Inventory (Circular Ring) ===");
        if (last == null) { System.out.println("  Empty"); return; }
        InventoryNode cur = last.next;
        int total = 0;
        do {
            String bar = "█".repeat(Math.min(cur.units / 5, 20));
            String warn = cur.units < 20 ? " ⚠ LOW" : "";
            System.out.printf("  %-4s | %3d units | %s%s%n", cur.bloodGroup, cur.units, bar, warn);
            total += cur.units;
            cur = cur.next;
        } while (cur != last.next);
        System.out.println("  Total: " + total + " units across " + size + " blood groups");
    }
}

// ============================================================
//  MAIN — Interactive Menu System
// ============================================================
public class BloodBankSystem {

    static Scanner sc = new Scanner(System.in);

    // ── shared state ──────────────────────────────────
    static DonorLinkedList      donorList  = new DonorLinkedList();
    static DonationHistoryStack history    = new DonationHistoryStack();
    static BloodRequestQueue    reqQueue   = new BloodRequestQueue();
    static EmergencyDonorPQ     emergPQ    = new EmergencyDonorPQ();
    static BloodGroupHashTable  hashTable  = new BloodGroupHashTable();
    static CityDonorMap         cityMap    = new CityDonorMap();
    static BloodInventoryCircular inventory = new BloodInventoryCircular();
    static int nextId = 111;

    // ── utilities ─────────────────────────────────────
    static void banner(String title) {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("  " + title);
        System.out.println("═".repeat(65));
    }

    static void pause() {
        System.out.print("\n  Press ENTER to continue...");
        sc.nextLine();
    }

    static String input(String prompt) {
        System.out.print("  " + prompt);
        return sc.nextLine().trim();
    }

    static int inputInt(String prompt) {
        while (true) {
            try { return Integer.parseInt(input(prompt)); }
            catch (NumberFormatException e) { System.out.println("  [!] Please enter a valid number."); }
        }
    }

    // ── seed data ─────────────────────────────────────
    static void loadSeedData() {
        DonorNode[] seed = {
            new DonorNode(101, "Arjun Sharma",   "O+",  28, "9876543210", "Delhi",     5, "2024-11-10", false),
            new DonorNode(102, "Priya Patel",    "A-",  34, "9123456789", "Mumbai",    3, "2024-09-05", true ),
            new DonorNode(103, "Ravi Kumar",     "B+",  22, "9988776655", "Chennai",   7, "2024-12-01", false),
            new DonorNode(104, "Sneha Iyer",     "AB+", 29, "9871234567", "Bengaluru", 2, "2025-01-15", false),
            new DonorNode(105, "Mohammed Ali",   "O-",  40, "9765432100", "Hyderabad", 9, "2024-10-20", true ),
            new DonorNode(106, "Kavita Singh",   "A+",  25, "9654321098", "Pune",      4, "2024-08-30", false),
            new DonorNode(107, "Deepak Reddy",   "B-",  31, "9543210987", "Delhi",     6, "2024-11-25", false),
            new DonorNode(108, "Anita Nair",     "O+",  27, "9432109876", "Kochi",     8, "2025-02-01", true ),
            new DonorNode(109, "Suresh Menon",   "AB-", 45, "9321098765", "Mumbai",    1, "2024-07-14", false),
            new DonorNode(110, "Lakshmi Devi",   "O-",  33, "9210987654", "Chennai",   5, "2024-12-20", false),
        };
        for (DonorNode d : seed) {
            donorList.insertAtEnd(d);
            hashTable.insert(d);
            cityMap.addDonor(d);
            emergPQ.add(d);
        }
        inventory.insert("O+",  120); inventory.insert("O-",   45);
        inventory.insert("A+",   80); inventory.insert("A-",   30);
        inventory.insert("B+",   65); inventory.insert("B-",   20);
        inventory.insert("AB+",  15); inventory.insert("AB-",   8);

        reqQueue.enqueue("REQ#001 | AIIMS Delhi    | 2 units O+  | URGENT");
        reqQueue.enqueue("REQ#002 | KEM Mumbai     | 1 unit  A-  | NORMAL");
        reqQueue.enqueue("REQ#003 | Apollo Chennai | 3 units B+  | URGENT");
        reqQueue.enqueue("REQ#004 | Fortis Pune    | 2 units AB+ | NORMAL");

        history.push("System initialized with 10 donors");
        history.push("Blood inventory loaded — 8 blood groups");
        history.push("4 blood requests queued");
    }

    // ─────────────────────────────────────────────────────
    // MENU 1: Donor Registry (Doubly Linked List)
    // ─────────────────────────────────────────────────────
    static void menuRegistry() {
        while (true) {
            banner("CO2 │ Donor Registry — Doubly Linked List");
            System.out.println("  [1] View All Donors (Forward Traversal)");
            System.out.println("  [2] View All Donors (Backward Traversal)");
            System.out.println("  [3] Add New Donor (Insert at End)");
            System.out.println("  [4] Add Donor at Beginning");
            System.out.println("  [5] Delete Donor by ID");
            System.out.println("  [6] Reverse the List");
            System.out.println("  [0] Back to Main Menu");
            String ch = input("\n  Choice: ");

            switch (ch) {
                case "1" -> {
                    System.out.println("\n► All Donors (Forward Traversal):");
                    donorList.traverseForward();
                }
                case "2" -> {
                    System.out.println("\n► All Donors (Backward Traversal):");
                    donorList.traverseBackward();
                }
                case "3" -> {
                    System.out.println("\n► Add New Donor");
                    DonorNode d = readDonorInput();
                    donorList.insertAtEnd(d);
                    hashTable.insert(d);
                    cityMap.addDonor(d);
                    emergPQ.add(d);
                    history.push("Added donor: " + d.name + " [" + d.bloodGroup + "] ID=" + d.donorId);
                    System.out.println("  ✓ Donor added successfully.");
                }
                case "4" -> {
                    System.out.println("\n► Add Donor at Beginning");
                    DonorNode d = readDonorInput();
                    donorList.insertAtBeginning(d);
                    hashTable.insert(d);
                    cityMap.addDonor(d);
                    emergPQ.add(d);
                    history.push("Added donor at start: " + d.name + " ID=" + d.donorId);
                    System.out.println("  ✓ Donor added at beginning.");
                }
                case "5" -> {
                    int id = inputInt("Enter Donor ID to delete: ");
                    DonorNode found = donorList.searchById(id);
                    if (found != null) {
                        donorList.deleteById(id);
                        hashTable.delete(id);
                        history.push("Deleted donor: " + found.name + " ID=" + id);
                        System.out.println("  ✓ Donor ID " + id + " deleted.");
                    } else {
                        System.out.println("  [!] Donor ID " + id + " not found.");
                    }
                }
                case "6" -> {
                    donorList.reverse();
                    history.push("List reversed");
                    System.out.println("  ✓ List reversed. Showing result:");
                    donorList.traverseForward();
                }
                case "0" -> { return; }
                default  -> System.out.println("  [!] Invalid option.");
            }
            pause();
        }
    }

    static DonorNode readDonorInput() {
        int id = nextId++;
        String name     = input("Name:              ");
        String blood    = input("Blood Group (e.g. O+, A-, AB+): ");
        int    age      = inputInt("Age:               ");
        String contact  = input("Contact Number:    ");
        String city     = input("City:              ");
        int    units    = inputInt("Units Donated:     ");
        String date     = input("Last Donation Date (YYYY-MM-DD): ");
        String urgentStr= input("Urgent? (yes/no):  ");
        boolean urgent  = urgentStr.equalsIgnoreCase("yes") || urgentStr.equalsIgnoreCase("y");
        System.out.println("  Assigned Donor ID: " + id);
        return new DonorNode(id, name, blood, age, contact, city, units, date, urgent);
    }

    // ─────────────────────────────────────────────────────
    // MENU 2: Search
    // ─────────────────────────────────────────────────────
    static void menuSearch() {
        while (true) {
            banner("CO1 │ Search Algorithms");
            System.out.println("  [1] Linear Search  — by Name         O(n)");
            System.out.println("  [2] Linear Search  — by Blood Group  O(n)");
            System.out.println("  [3] Linear Search  — by City         O(n)");
            System.out.println("  [4] Binary Search  — by Donor ID     O(log n)  [sorted array]");
            System.out.println("  [5] Hash Lookup    — by Blood Group  O(1) avg");
            System.out.println("  [0] Back");
            String ch = input("\n  Choice: ");

            switch (ch) {
                case "1" -> {
                    String name = input("Enter donor name: ");
                    DonorNode found = donorList.searchByName(name);
                    if (found != null) System.out.println("  FOUND → " + found);
                    else               System.out.println("  NOT FOUND.");
                }
                case "2" -> {
                    String bg = input("Enter blood group: ");
                    List<DonorNode> list = donorList.searchByBloodGroup(bg);
                    if (list.isEmpty()) System.out.println("  No donors found with blood group " + bg);
                    else { System.out.println("  Found " + list.size() + " donor(s):"); list.forEach(d -> System.out.println("  → " + d)); }
                }
                case "3" -> {
                    String city = input("Enter city: ");
                    List<DonorNode> list = donorList.searchByCity(city);
                    if (list.isEmpty()) System.out.println("  No donors found in " + city);
                    else { System.out.println("  Found " + list.size() + " donor(s):"); list.forEach(d -> System.out.println("  → " + d)); }
                }
                case "4" -> {
                    int id = inputInt("Enter Donor ID: ");
                    DonorNode[] arr = donorList.toArray();
                    DonorSorter.bubbleSort(arr);   // sort by ID first
                    int idx = DonorSearcher.binarySearch(arr, id);
                    if (idx >= 0) System.out.println("  FOUND at index " + idx + " → " + arr[idx]);
                    else          System.out.println("  NOT FOUND.");
                }
                case "5" -> {
                    String bg = input("Enter blood group: ");
                    List<DonorNode> list = hashTable.getByBloodGroup(bg);
                    if (list.isEmpty()) System.out.println("  No donors found.");
                    else { System.out.println("  Found " + list.size() + " donor(s) [O(1) hash lookup]:"); list.forEach(d -> System.out.println("  → " + d.name + " | " + d.city + " | Units: " + d.unitsDonated)); }
                }
                case "0" -> { return; }
                default  -> System.out.println("  [!] Invalid option.");
            }
            pause();
        }
    }

    // ─────────────────────────────────────────────────────
    // MENU 3: Sorting
    // ─────────────────────────────────────────────────────
    static void menuSorting() {
        while (true) {
            banner("CO1 │ Sorting Algorithms — Asymptotic Analysis");
            System.out.println("  [1] Bubble Sort    by Donor ID      O(n²) avg · O(n) best");
            System.out.println("  [2] Selection Sort by Name          O(n²) always");
            System.out.println("  [3] Insertion Sort by Age           O(n²) worst · O(n) best");
            System.out.println("  [4] Merge Sort     by Units Donated O(n log n) always");
            System.out.println("  [5] Quick Sort     by Blood Group   O(n log n) avg");
            System.out.println("  [6] View Complexity Summary Table");
            System.out.println("  [0] Back");
            String ch = input("\n  Choice: ");

            DonorNode[] arr = donorList.toArray();

            switch (ch) {
                case "1" -> {
                    DonorSorter.bubbleSort(arr);
                    System.out.println("\n► Bubble Sort by Donor ID [O(n²) avg / O(n) best]:");
                    for (DonorNode d : arr) System.out.printf("  %d | %-20s | %s%n", d.donorId, d.name, d.bloodGroup);
                }
                case "2" -> {
                    DonorSorter.selectionSort(arr);
                    System.out.println("\n► Selection Sort by Name [O(n²) always]:");
                    for (DonorNode d : arr) System.out.printf("  %-20s | %s | %s%n", d.name, d.bloodGroup, d.city);
                }
                case "3" -> {
                    DonorSorter.insertionSort(arr);
                    System.out.println("\n► Insertion Sort by Age [O(n²) worst / O(n) best]:");
                    for (DonorNode d : arr) System.out.printf("  %-20s | Age: %-3d | %s%n", d.name, d.age, d.city);
                }
                case "4" -> {
                    DonorSorter.mergeSort(arr, 0, arr.length - 1);
                    System.out.println("\n► Merge Sort by Units Donated [O(n log n) always]:");
                    for (DonorNode d : arr) System.out.printf("  %-20s | Units: %d%n", d.name, d.unitsDonated);
                }
                case "5" -> {
                    DonorSorter.quickSort(arr, 0, arr.length - 1);
                    System.out.println("\n► Quick Sort by Blood Group [O(n log n) avg / O(n²) worst]:");
                    for (DonorNode d : arr) System.out.printf("  %-4s | %-20s | %s%n", d.bloodGroup, d.name, d.city);
                }
                case "6" -> complexityTable();
                case "0" -> { return; }
                default  -> System.out.println("  [!] Invalid option.");
            }
            if (!ch.equals("0")) pause();
        }
    }

    // ─────────────────────────────────────────────────────
    // MENU 4: Stack — Donation History
    // ─────────────────────────────────────────────────────
    static void menuStack() {
        while (true) {
            banner("CO3 │ Stack — Donation History / Undo");
            System.out.println("  [1] View History (Display Stack)");
            System.out.println("  [2] Add Action (Push)");
            System.out.println("  [3] Undo Last Action (Pop)");
            System.out.println("  [4] Peek Top Action");
            System.out.println("  [0] Back");
            String ch = input("\n  Choice: ");

            switch (ch) {
                case "1" -> history.displayAll();
                case "2" -> {
                    String action = input("Enter action description: ");
                    history.push(action);
                }
                case "3" -> {
                    String undone = history.pop();
                    if (undone == null) System.out.println("  Nothing to undo.");
                }
                case "4" -> {
                    String top = history.peek();
                    System.out.println(top != null ? "  PEEK → " + top : "  Stack is empty.");
                }
                case "0" -> { return; }
                default  -> System.out.println("  [!] Invalid option.");
            }
            pause();
        }
    }

    // ─────────────────────────────────────────────────────
    // MENU 5: Queue — Blood Request Processing
    // ─────────────────────────────────────────────────────
    static void menuQueue() {
        while (true) {
            banner("CO3 │ Circular Queue — Blood Request Processing");
            System.out.println("  [1] View Pending Requests");
            System.out.println("  [2] Add New Request (Enqueue)");
            System.out.println("  [3] Process Next Request (Dequeue)");
            System.out.println("  [4] Emergency Priority Queue — Top Donors");
            System.out.println("  [0] Back");
            String ch = input("\n  Choice: ");

            switch (ch) {
                case "1" -> reqQueue.displayQueue();
                case "2" -> {
                    String req = input("Enter request (e.g. REQ#005 | Hospital | 2 units O+ | URGENT): ");
                    reqQueue.enqueue(req);
                    history.push("Queued blood request: " + req);
                }
                case "3" -> {
                    String processed = reqQueue.dequeue();
                    if (processed != null) history.push("Processed request: " + processed);
                }
                case "4" -> {
                    int n = inputInt("Show top N donors (e.g. 3): ");
                    emergPQ.displayTop(n);
                }
                case "0" -> { return; }
                default  -> System.out.println("  [!] Invalid option.");
            }
            pause();
        }
    }

    // ─────────────────────────────────────────────────────
    // MENU 6: Hash Table & City Map
    // ─────────────────────────────────────────────────────
    static void menuHash() {
        while (true) {
            banner("CO4 │ Hash Table & Collections");
            System.out.println("  [1] Display Full Hash Table (Chaining)");
            System.out.println("  [2] Lookup Donors by Blood Group  O(1) avg");
            System.out.println("  [3] City-wise Donor Summary (HashMap)");
            System.out.println("  [4] List Donors in a Specific City");
            System.out.println("  [0] Back");
            String ch = input("\n  Choice: ");

            switch (ch) {
                case "1" -> hashTable.displayTable();
                case "2" -> {
                    String bg = input("Enter blood group: ");
                    List<DonorNode> list = hashTable.getByBloodGroup(bg);
                    if (list.isEmpty()) System.out.println("  No donors found.");
                    else list.forEach(d -> System.out.printf("  → %-20s | %s | Units: %d%n", d.name, d.city, d.unitsDonated));
                }
                case "3" -> cityMap.displayCitySummary();
                case "4" -> {
                    String city = input("Enter city: ");
                    List<DonorNode> list = cityMap.getDonorsByCity(city);
                    if (list.isEmpty()) System.out.println("  No donors in " + city);
                    else list.forEach(d -> System.out.printf("  → %-20s [%s] Units: %d%n", d.name, d.bloodGroup, d.unitsDonated));
                }
                case "0" -> { return; }
                default  -> System.out.println("  [!] Invalid option.");
            }
            pause();
        }
    }

    // ─────────────────────────────────────────────────────
    // MENU 7: Blood Inventory (Circular Linked List)
    // ─────────────────────────────────────────────────────
    static void menuInventory() {
        while (true) {
            banner("CO2 │ Circular Linked List — Blood Inventory");
            System.out.println("  [1] View Inventory Ring");
            System.out.println("  [2] Add Units to Blood Group");
            System.out.println("  [3] Consume Units from Blood Group");
            System.out.println("  [0] Back");
            String ch = input("\n  Choice: ");

            switch (ch) {
                case "1" -> inventory.traverse();
                case "2" -> {
                    String bg = input("Blood group: ");
                    int u = inputInt("Units to add: ");
                    inventory.updateUnits(bg, u);
                    history.push("Added " + u + " units of " + bg);
                }
                case "3" -> {
                    String bg = input("Blood group: ");
                    int u = inputInt("Units to consume: ");
                    inventory.updateUnits(bg, -u);
                    history.push("Consumed " + u + " units of " + bg);
                }
                case "0" -> { return; }
                default  -> System.out.println("  [!] Invalid option.");
            }
            pause();
        }
    }

    // ─────────────────────────────────────────────────────
    // Big-O Complexity Table
    // ─────────────────────────────────────────────────────
    static void complexityTable() {
        System.out.println("""

  ┌─────────────────────────────────┬────────────┬────────────┬───────────┐
  │ Structure / Algorithm           │ Best       │ Average    │ Worst     │
  ├─────────────────────────────────┼────────────┼────────────┼───────────┤
  │ Doubly LL Insert/Delete Head    │ O(1)       │ O(1)       │ O(1)      │
  │ Doubly LL Search (Linear)       │ O(1)       │ O(n)       │ O(n)      │
  │ Circular LL Traverse            │ O(n)       │ O(n)       │ O(n)      │
  │ Stack Push/Pop                  │ O(1)       │ O(1)       │ O(1)      │
  │ Circular Queue Enq/Deq          │ O(1)       │ O(1)       │ O(1)      │
  │ Priority Queue (Heap) Poll      │ O(log n)   │ O(log n)   │ O(log n)  │
  │ Hash Table Insert/Lookup        │ O(1)       │ O(1)       │ O(n)      │
  │ HashMap (Collections) Get       │ O(1)       │ O(1)       │ O(n)      │
  │ Bubble Sort                     │ O(n)       │ O(n²)      │ O(n²)     │
  │ Selection Sort                  │ O(n²)      │ O(n²)      │ O(n²)     │
  │ Insertion Sort                  │ O(n)       │ O(n²)      │ O(n²)     │
  │ Merge Sort                      │ O(n log n) │ O(n log n) │ O(n log n)│
  │ Quick Sort                      │ O(n log n) │ O(n log n) │ O(n²)     │
  │ Linear Search                   │ O(1)       │ O(n)       │ O(n)      │
  │ Binary Search                   │ O(1)       │ O(log n)   │ O(log n)  │
  └─────────────────────────────────┴────────────┴────────────┴───────────┘

  Space Complexity:
  • Doubly LL       : O(n)         — n nodes with prev/next pointers
  • Stack (array)   : O(MAX=100)   — fixed allocation
  • Circular Queue  : O(CAPACITY)  — fixed allocation
  • Heap / PQ       : O(n)         — n elements
  • Hash Table      : O(n + m)     — n donors, m buckets
  • Merge Sort      : O(n)         — auxiliary arrays
  • Quick Sort      : O(log n)     — call stack average
""");
    }

    // ─────────────────────────────────────────────────────
    // MAIN MENU
    // ─────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("\n  Loading Blood Bank System...");
        loadSeedData();
        System.out.println("  ✓ 10 donors loaded | Inventory ready | 4 requests queued\n");

        while (true) {
            banner("BLOOD BANK DONOR INFORMATION SYSTEM");
            System.out.println("  CO2  [1] Donor Registry        — Doubly Linked List");
            System.out.println("  CO1  [2] Search Algorithms      — Linear / Binary / Hash");
            System.out.println("  CO1  [3] Sorting Algorithms     — Bubble/Selection/Insertion/Merge/Quick");
            System.out.println("  CO3  [4] Donation History       — Stack (Undo)");
            System.out.println("  CO3  [5] Blood Request Queue    — Circular Queue + Priority Queue");
            System.out.println("  CO4  [6] Hash Table & City Map  — Chaining + HashMap");
            System.out.println("  CO2  [7] Blood Inventory        — Circular Linked List");
            System.out.println("  CO1  [8] Complexity Summary     — Big-O Table");
            System.out.println("       [0] Exit");
            System.out.printf("%n  Donors: %d | Requests: %d | History: %d%n",
                donorList.getSize(), reqQueue.size(), history.size());

            String ch = input("\n  Choice: ");
            switch (ch) {
                case "1" -> menuRegistry();
                case "2" -> menuSearch();
                case "3" -> menuSorting();
                case "4" -> menuStack();
                case "5" -> menuQueue();
                case "6" -> menuHash();
                case "7" -> menuInventory();
                case "8" -> { complexityTable(); pause(); }
                case "0" -> {
                    System.out.println("\n  ✓ Exiting Blood Bank System. Goodbye!\n");
                    sc.close();
                    System.exit(0);
                }
                default -> System.out.println("  [!] Invalid choice. Try again.");
            }
        }
    }
}
