
public class Wifi {

    private int sta;
    private final int SIFS = 10;
    private final int DIFS = 50;
    private final int SIGMA = 20;
    private final int DELTA = 0;
    private final int PHYSYCAL_HEADER = 72 + 24;
    private final double MAC_HEADER = 272 / 11;
    private final double ACK = (112 / 11) + PHYSYCAL_HEADER;
    private final double TOTAL_HEADER = PHYSYCAL_HEADER + MAC_HEADER;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Format: java Wifi <number of stations> <Payload in bits>");
        } else {
            int sta = Integer.parseInt(args[0]);
            int payload = Integer.parseInt(args[1]);
            new Wifi().start(sta, payload);
        }
    }

    public void start(int sta, int payload) {
        this.sta = sta;
        System.out.println("\n\n--- Eficiencia Wifi IEEE 802.11b ---\n");
        System.out.println("\tEstaciones = " + sta);
        System.out.println("\tPayload = " + payload + " bits");
        System.out.println("\n\tEficiencia: " + s(payload));
		System.out.println("\n-------------------------------------\n");
    }

    private double ts(int payload) {
        return TOTAL_HEADER + (payload / 11) + SIFS + DELTA + ACK + DIFS + DELTA;
    }

    private double tc(int payload) {
        return TOTAL_HEADER + (payload / 11) + DIFS + DELTA;
    }

    private double thau(int payload) {
        return 1 / (sta * Math.sqrt(tc(payload) / (2 * SIGMA)));
    }

    private double ptr(int payload) {
        return 1 - Math.pow((1 - thau(payload)), sta);
    }

    private double ps(int payload) {
        return (sta * thau(payload) * Math.pow(1 - thau(payload), sta - 1)) / ptr(payload);
    }

    public double s(int payload) {
        return (payload/11)/(ts(payload)-tc(payload)+(SIGMA*(1 - ptr(payload))/ptr(payload) + tc(payload))/ps(payload));
    }
}
