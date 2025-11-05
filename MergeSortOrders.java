/* 123B5F138 RACHANA DIXIT
    Design and implement a sorting algorithm using Merge Sort to efficiently arrange customer
orders based on their timestamps. The solution should handle a large dataset (up to 1 million
orders) with minimal computational overhead. Additionally, analyze the time complexity and
compare it with traditional sorting techniques.*/



import java.util.Random;

class Order {
    long timestamp;
    String orderId;

    public Order(long timestamp, String orderId) {
        this.timestamp = timestamp;
        this.orderId = orderId;
    }
}

public class MergeSortOrders {

    public static void mergeSort(Order[] orders, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(orders, left, mid);
            mergeSort(orders, mid + 1, right);
            merge(orders, left, mid, right);
        }
    }

    private static void merge(Order[] orders, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Order[] leftArray = new Order[n1];
        Order[] rightArray = new Order[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = orders[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = orders[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i].timestamp <= rightArray[j].timestamp) {
                orders[k] = leftArray[i];
                i++;
            } else {
                orders[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            orders[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            orders[k] = rightArray[j];
            j++;
            k++;
        }
    }
    public static void printOrders(Order[] orders, int limit) {
        for (int i = 0; i < limit && i < orders.length; i++) {
            System.out.println("OrderID: " + orders[i].orderId + " | Timestamp: " + orders[i].timestamp);
        }
    }

    public static void main(String[] args) {
        int size = 1_000_000; 
        Order[] orders = new Order[size];
        Random random = new Random();

        
        for (int i = 0; i < size; i++) {
            long time = random.nextLong() & Long.MAX_VALUE; 
            orders[i] = new Order(time, "ORD" + i);
        }

        long start = System.currentTimeMillis();
        mergeSort(orders, 0, orders.length - 1);
        long end = System.currentTimeMillis();

    
        System.out.println("Sorted Orders (First 10): ");
        printOrders(orders, 10);

        System.out.println("\nTotal Orders Sorted: " + size);
        System.out.println("Sorting Time: " + (end - start) + " ms");
    }

}
