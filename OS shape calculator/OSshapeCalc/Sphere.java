public class Sphere {
    public static int rad = 0;
    public static double ssurface = 0;
    public static double svolume = 0;


    public static void wr(int input, int num) {
        switch(num) {
            case 1:
            rad = input;
            break;
        }
    }

    public static double rd(int num) {
        switch(num) {
            case 1:
            return ssurface;
            case 2:
            return svolume;
        }
        return 0.0;
    }

    public static void calculate() {
        svolume = (4/3)*Math.PI*Math.pow(rad,2);
        ssurface = 4*Math.PI*Math.pow(rad,2);
    }
}
