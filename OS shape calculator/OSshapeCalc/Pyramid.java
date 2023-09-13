public class Pyramid {
    public static double px = 0;
    public static double py = 0;
    public static double pz = 0;
    public static double psurface = 0;
    public static double pvolume = 0;
    
    public static void wr(int input, int num) {
        switch(num) {
            case 1:
            px = (double)input;
            break;
            case 2:
            py = (double)input;
            break;
            case 3:
            pz = (double)input;
            break;
        }
    }

    public static double rd(int num) {
        switch(num) {
            case 1:
            return psurface;
            case 2:
            return pvolume;
        }
        return 0;
    }

    public static void calculate() {
        pvolume =  (px*py*pz)/3;
        psurface = (pz * px) + (pz * Math.sqrt(Math.pow(px / 2, 2) + Math.pow(py, 2))) + (px * Math.sqrt(Math.pow(pz / 2, 2) + Math.pow(py, 2)));
    }
}
