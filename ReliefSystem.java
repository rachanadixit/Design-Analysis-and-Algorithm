import java.util.*;

class Supply {
    String name;
    double weight;
    double utility;
    boolean divisible;

    Supply(String name, double weight, double utility, boolean divisible) {
        this.name = name;
        this.weight = weight;
        this.utility = utility;
        this.divisible = divisible;
    }

    double utilityPerKg() {
        return utility / weight;
    }
}

public class ReliefSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Total Items : ");
        int n = Integer.parseInt(sc.nextLine().trim());

        List<Supply> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            System.out.println("\n--- Item " + i + " ---");
            System.out.print("Name: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) name = "item" + i;

            System.out.print("Weight (kg): ");
            double w = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Utility (importance): ");
            double u = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Divisible? (y/n or 1/0): ");
            String flag = sc.nextLine().trim();
            boolean div = flag.equalsIgnoreCase("y") || flag.equals("1");

            list.add(new Supply(name, w, u, div));
        }

        System.out.print("\nBoat capacity (kg): ");
        double capacity = Double.parseDouble(sc.nextLine().trim());

        // sort by utility per kg, highest first
        list.sort((a, b) -> Double.compare(b.utilityPerKg(), a.utilityPerKg()));

        double remaining = capacity;
        double totalUtility = 0;
        double used = 0;

        System.out.println("\nOutput:");
        for (int i = 0; i < list.size(); i++) {
            if (remaining <= 0) break;
            Supply s = list.get(i);
            if (s.divisible) {
                double take = Math.min(s.weight, remaining);
                double util = s.utilityPerKg() * take;
                totalUtility += util;
                remaining -= take;
                used += take;
                System.out.println(" - " + s.name + ": took " + two(take) + " kg (utility +" + two(util) + ")");
            } else {
                if (s.weight <= remaining) {
                    totalUtility += s.utility;
                    remaining -= s.weight;
                    used += s.weight;
                    System.out.println(" - " + s.name + ": took whole item (" + two(s.weight) + " kg, utility +" + two(s.utility) + ")");
                } else {
                    System.out.println(" - " + s.name + ": skipped (too heavy to fit as whole)");
                }
            }
        }

        System.out.println("\nTotal weight loaded: " + two(used) + " / " + two(capacity) + " kg");
        System.out.println("Total utility on board: " + two(totalUtility));
        System.out.println("Free space left: " + two(remaining) + " kg");

        sc.close();
    }

    private static String two(double x) {
        double r = Math.round(x * 100.0) / 100.0;
        if (r == (long) r) return String.valueOf((long) r) + ".0";
        return String.valueOf(r);
    }

}
