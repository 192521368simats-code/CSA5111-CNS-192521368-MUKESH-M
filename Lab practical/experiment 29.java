
public class SHA3Lanes {
    public static void main(String[] args) {
        int stateBits = 1600;
        int rateBits = 1024;
        int capacityBits = stateBits - rateBits;

        int laneSize = 64;
        int rateLanes = rateBits / laneSize;
        int capacityLanes = capacityBits / laneSize;

        boolean[] lanes = new boolean[stateBits / laneSize];

        // First 16 lanes belong to the rate portion.
        // Each is assumed to have at least one nonzero bit.
        for (int i = 0; i < rateLanes; i++) {
            lanes[i] = true;
        }

        // Capacity lanes are initially zero.
        for (int i = rateLanes; i < lanes.length; i++) {
            lanes[i] = false;
        }

        System.out.println("SHA-3 State Analysis");
        System.out.println("State size = " + stateBits + " bits");
        System.out.println("Rate = " + rateBits + " bits");
        System.out.println("Capacity = " + capacityBits + " bits");
        System.out.println("Rate lanes = " + rateLanes);
        System.out.println("Capacity lanes = " + capacityLanes);
        System.out.println("Nonzero capacity lanes initially = 0");

        System.out.println(
            "With permutation ignored, capacity lanes remain zero.");
        System.out.println(
            "Time until all capacity lanes become nonzero = Never");
    }
}
