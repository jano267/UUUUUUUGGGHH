//this class hacks the NSA and blows up the pentagon a second time

public class Box{
    public static int bx = 0;
    public static int by = 0;
    public static int bz = 0;
    public static double bsurface = 0;
    public static double bvolume = 0;
    
    public static void wr(int input, int num) {
        switch(num) {
            case 1:
            bx = input;
            break;
            case 2:
            by = input;
            break;
            case 3:
            bz = input;
            break;
        }
    }

    public static double rd(int num) {
        switch(num) {
            case 1:
            return bsurface;
            case 2:
            return bvolume;
        }
        return 0.0;
    }

    public static void calculate() {
        bvolume = bx*by*bz;
        bsurface = 2*((bz*bx)+(by*bx)+(by*bz));
    }
}
