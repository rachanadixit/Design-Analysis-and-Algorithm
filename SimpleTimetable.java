import java.util.*;

public class SimpleTimetable {
    static int N, R;
    static boolean[][] conflict;   
    static int[] courseSize;
    static int[] roomCap;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Number of courses (N): ");
        N = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Number of rooms (R): ");
        R = Integer.parseInt(sc.nextLine().trim());

        courseSize = new int[N];
        System.out.println("Enter course sizes (one per line):");
        for (int i = 0; i < N; ++i) courseSize[i] = Integer.parseInt(sc.nextLine().trim());

        roomCap = new int[R];
        System.out.println("Enter room capacities (one per line):");
        for (int i = 0; i < R; ++i) roomCap[i] = Integer.parseInt(sc.nextLine().trim());

        conflict = new boolean[N][N];
        System.out.print("Number of students S: ");
        int S = Integer.parseInt(sc.nextLine().trim());
        System.out.println("For each student, enter enrolled course ids (space separated, 0-based):");
        for (int s = 0; s < S; ++s) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) { s--; continue; }
            String[] parts = line.split("\\s+");
            List<Integer> list = new ArrayList<>();
            for (String p : parts) list.add(Integer.parseInt(p));
            for (int i = 0; i < list.size(); ++i)
                for (int j = i + 1; j < list.size(); ++j) {
                    int a = list.get(i), b = list.get(j);
                    conflict[a][b] = conflict[b][a] = true;
                }
        }

        // simple check: every course must fit in at least one room
        for (int i = 0; i < N; ++i) {
            boolean ok = false;
            for (int r = 0; r < R; ++r) if (roomCap[r] >= courseSize[i]) ok = true;
            if (!ok) {
                System.out.println("Course " + i + " cannot fit in any room. Exiting.");
                sc.close();
                return;
            }
        }

        // Try k = 1..N slots
        boolean solved = false;
        int[] color = new int[N];
        int[] roomAssigned = new int[N];

        for (int k = 1; k <= N && !solved; ++k) {
            Arrays.fill(color, -1);
            Arrays.fill(roomAssigned, -1);
            boolean[][] usedRoom = new boolean[k][R]; // usedRoom[slot][room]
            if (backtrack(0, k, color, roomAssigned, usedRoom)) {
                System.out.println("Found schedule with " + k + " slots:");
                for (int i = 0; i < N; ++i) {
                    System.out.println("Course " + i + " -> Slot " + (color[i] + 1) + ", Room " + roomAssigned[i]);
                }
                solved = true;
            } else {
                // try next k
            }
        }

        if (!solved) System.out.println("No feasible schedule found up to " + N + " slots.");
        sc.close();
    }

    // backtracking: assign course 'idx'
    static boolean backtrack(int idx, int k, int[] color, int[] roomAssigned, boolean[][] usedRoom) {
        if (idx == N) return true; // all assigned

        for (int c = 0; c < k; ++c) {
            // check neighbor conflict for color c
            boolean conflictColor = false;
            for (int v = 0; v < N; ++v) {
                if (conflict[idx][v] && color[v] == c) { conflictColor = true; break; }
            }
            if (conflictColor) continue;

            // try assign a room in this slot
            for (int r = 0; r < R; ++r) {
                if (usedRoom[c][r]) continue;
                if (roomCap[r] < courseSize[idx]) continue;

                // assign
                color[idx] = c;
                roomAssigned[idx] = r;
                usedRoom[c][r] = true;

                if (backtrack(idx + 1, k, color, roomAssigned, usedRoom)) return true;

                // undo
                color[idx] = -1;
                roomAssigned[idx] = -1;
                usedRoom[c][r] = false;
            }
        }
        return false; // no color-room worked
    }
}