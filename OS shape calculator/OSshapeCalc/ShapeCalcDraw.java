import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

class Main {
    public static JFrame myJFrame = new JFrame();
    public static boolean mode = false;
    public static String[] avail = {"Rectangular Prism","Sphere","Pyramid","Instructions"};
    public static final String[] c = {"\u001B[0m","\u001B[30m","\u001B[31m","\u001B[32m","\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[37m","\u001B[40m","\u001B[41m","\u001B[42m","\u001B[43m","\u001B[44m","\u001B[45m","\u001B[46m","\u001B[47m"};
    public static int termHeight = 54, termWidth = 180, curs = 1, sl = 0, cColor = 13, hColor = 3, PrimaryWindow = 0, oldPW = 0, setWindow = 0, numVal = -11, oldSW = 0;
    public static int[][] upos = {{0,1,20,40,60},{0,1,20,10,30}}, usz = {{0,30,30,30,63},{0,15,13,15,24}}; //x,y,cx,cy
    public static String[][] text = {{"Rectangular Prism","Width:","Height:","Length:","Surface Area Is","Volume Is"},{"Sphere","Radius:","Surface Area Is","Volume Is"},{"Pyramid","Width:","Height:","Length:","Surface Area Is", "Area Is"}, {"Instructions","Moving the cursor:","The cursor can traverse a text box with the up and down","arrow keys while the java 'teacup' program is selected.","The left and right keys will select a different window."," ","Adjustment of Values:","Press enter to adjust a value, or highlight a line of text.", "With the '\u2592\u2592' selected, you can move the box with arrow keys."," ","Number Keys and other Specials:","The number keys can be used to change a value in", "an editable text line, or can select a window.", "The Minus(-) key will invert the data in a text line,","and the backspace(delete) key will delete the last digit."}};
    public static int[][] textType= {{0,                 01,      11,       21,          71,            81          },{0           ,31       ,91               ,101    },{0        ,41      ,51       ,61       ,111              ,121  }, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}}; // text type inside text boxes (0= text, <variable location>1= Value, <variable location>2=Toggle, <box to open>3 =open file, <box to close>4 =close file)
    public static int[] boxRenderOrder = {4,3,2,1}, boxData = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; // adjustable data inside text boxes
    public static boolean[] opened = {true, true, true,true}, active = {true,false,false,false}, boxToggle = {false,false,false,false,false}; //toggle data inside text boxes
    public static void main(String args[]) {
        //intro();
        cls();
        Jsetup();
    }

    public static void update() {
        cls();
        windowList(avail);
        RenderOrderUpd();
        Render(4);
    }
    
    public static void CalculateThings() {
        Box.wr(boxData[0],1);
        Box.wr(boxData[1],2);
        Box.wr(boxData[2],3);
        Sphere.wr(boxData[3],1);
        Pyramid.wr(boxData[4],1);
        Pyramid.wr(boxData[5],2);
        Pyramid.wr(boxData[6],3);
        Box.calculate(); Sphere.calculate(); Pyramid.calculate();
        boxData[7] = (int)Box.rd(1);
        boxData[8] = (int)Box.rd(2);
        boxData[9] = (int)Sphere.rd(1);
        boxData[10] = (int)Sphere.rd(2);
        boxData[11] = (int)Pyramid.rd(1);
        boxData[12] = (int)Pyramid.rd(2);
    }
    
    public static void Jsetup() {
        RenderOrderUpd();
        update();
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJFrame.setVisible(true);
        myJFrame.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            numVal = -11;
            if (keyCode == 49) {
            setWindow = 0-sl;
            numVal = 1;
            } if (keyCode == 50) {
            setWindow = 1-sl;
            numVal = 2;
            } if (keyCode == 51) {
            setWindow = 2-sl;
            numVal = 3;
            } if (keyCode == 52) {
            setWindow = 3-sl;
            numVal = 4;
            } if (keyCode == 53) {
            setWindow = 4-sl;
            numVal = 5;
            } if (keyCode == 54) {
            setWindow = 5-sl;
            numVal = 6;
            } if (keyCode == 55) {
            setWindow = 6-sl;
            numVal = 7;
            } if (keyCode == 56) {
            setWindow = 7-sl;
            numVal = 8;
            } if (keyCode == 57) {
            setWindow = 8-sl;
            numVal = 9;
            } if (keyCode == 48) {
            setWindow = 9-sl;
            numVal = 0;
            }
            if (mode == true) {
                setWindow = oldSW;
            } else {
                oldSW = setWindow;
            }
            if (mode == true && textType[PrimaryWindow][curs-1]%10 == 1) {
                int j = boxData[textType[PrimaryWindow][curs-1]/10];
                if (keyCode == KeyEvent.VK_MINUS) {
                boxData[textType[PrimaryWindow][curs-1]/10] *= -1;
            }
                if (keyCode == KeyEvent.VK_BACK_SPACE) {
                boxData[textType[PrimaryWindow][curs-1]/10] = j/10;
            }
                if(numVal != -11) {
                    boolean neg = false;
                    if (j != Math.abs(j)) {
                        neg = true;
                    }
                j = Math.abs(j*10)+numVal;
                boxData[textType[PrimaryWindow][curs-1]/10] = j*(neg ? -1:1);
                }
            }
            if (keyCode == KeyEvent.VK_UP) {
            if(mode == false) {
            curs--;
            } else if (curs == 1) {
            upos[1][PrimaryWindow+1]--;
            } else if (textType[PrimaryWindow][curs-1]%10 == 1) {boxData[textType[PrimaryWindow][curs-1]/10]++;}
        }
            else if (keyCode == KeyEvent.VK_DOWN) {
            if(mode == false) {
            curs++;
            } else if (curs == 1) {
            upos[1][PrimaryWindow+1]++;
            } else if (textType[PrimaryWindow][curs-1]%10 == 1) {boxData[textType[PrimaryWindow][curs-1]/10]--;}
        }
            if (keyCode == KeyEvent.VK_LEFT) {
            if(mode == false) {
            sl--;
            } else if (curs == 1) {
            upos[0][PrimaryWindow+1]--;
            }
        }
            else if (keyCode == KeyEvent.VK_RIGHT) {
            if(mode == false) {
            sl++;
            } else if (curs == 1) {
            upos[0][PrimaryWindow+1]++;
            } 
        }
            if (keyCode == KeyEvent.VK_ENTER) {
            if(mode == false) {
            mode = true;
            } else {
            mode = false;
            }
        }
        if(curs < 1) {
            curs = 1;
        }
        if(curs > text[PrimaryWindow].length) {
            curs = text[PrimaryWindow].length;
        }
        CalculateThings();
        update();
        }
        });
    }

    public static void intro() {
        cls();
        crs(0,0,"");
        Random rand = new Random();
        String garb;
        String value;
        for(int i = 0; i < termHeight; i++) {
            garb = "";
            value = "";
            for (int j = 0; j < termWidth; j++) {
                switch(rand.nextInt(5)) {
                case 0:
                value = "\u2588";
                break;
                case 1:
                value = "\u2591";
                break;
                case 2:
                value = "\u2592";
                break;
                case 3:
                value = "\u2593";
                break;
                case 4:
                value = " ";
                break;
                }
                garb = garb + value;
            }
            System.out.println(garb);
        }
        delay(1000);
        cls();
        booting();
        delay(800);
        cls();
        delay(100);
    }
    
    public static void cls() {
        System.out.print("\033[2J");
    }
    
    public static void crs(int row, int column, String txt) {
    System.out.print(String.format("%c[%d;%df",033,row,column));
    System.out.println(txt);
    }

    public static void delay(int ms) {
        try { 
        Thread.sleep(ms);
        } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        }
    }

    public static void booting() {
        int mem = 4194304;
        for(int i = 0; i < 4; i++) {
        crs(0,0,"Initializing   ");
        delay(100);
        crs(0,0,"Initializing.  ");
        delay(100);
        crs(0,0,"Initializing.. ");
        delay(100);
        crs(0,0,"Initializing...");
        delay(100);
        }
        delay(100);
        crs(2,0,"Asgard Computers OS 2");
        delay(100);
        crs(3,0,"All Rights Reserved");
        delay(400);
        for(int i=0; i<=mem;i+=128) {
        crs(4,0,i + " Bytes OK");
        }
        delay(100);
        for(int i = mem; i >= mem-72144; i-=4) {
        crs(5,0,i + " Bytes Free");
        }
        delay(300);
        crs(6,0,"OS 2 Launching, Press 8 and * key together to enter Safe Mode");
        delay(2000);
    }
    
    public static void box(int x, int y, int width, int height, String[][] text, int cursor, int boxNum) {
        if(opened[boxNum] == true) {
        if(PrimaryWindow != boxNum) {
        cursor = 0;
        }
        String hbar = "", wid = "", drawText = "";
        for(int i = 0; i < width-2; i++) {hbar = hbar + "\u2500"; wid = wid + " ";} //create a horizontal bar
        crs((Math.max(y+1,0)),x,"\u2553" + hbar + "\u2556"); //set top bar
        crs(y+2,x,Character.toString('\u2551')); crs(y+2,Math.max(x- 1,0)+width,Character.toString('\u2551'));
        crs((Math.max(y+3,0)),x,"\u255F" + hbar + "\u2563"); //add description zone
        crs(y+2,x+1,wid); //clear description zone
        try {
        crs(y+2,x+1,text[boxNum][0]); //add description text
        } catch(ArrayIndexOutOfBoundsException e) { //ONLY FOR TESTING, WHEN FINISHED DELETE TRY{}CATCH(){} FOR SPEED SAVINGS
            crs(20,1,c[10] + "add text to box " + boxNum + " loser" + c[0]); //catch idiots
            crs(0,1,"");
            System.exit(0);
            }
        if(cursor == 1) {
        System.out.print(c[cColor + (mode ? 1:0)*hColor]);
        }
            crs(y+2,width+x-4,"\u2592\u2592"+ c[0]); //add draggy
            for(int i = 4; i < height-6; i++) { //create body
            crs(y+i,x,Character.toString('\u2551') + wid + Character.toString('\u2551'));
            try {
            drawText = text[boxNum][(i-3)];
            } catch(ArrayIndexOutOfBoundsException e) { //ONLY FOR TESTING, WHEN FINISHED DELETE TRY{}CATCH(){} FOR SPEED SAVINGS
            crs(20,1,c[10] + "missing " + (height-text[boxNum].length-3) + " lines of text in box " + boxNum + ", exited bounds at row " + (i-4) + c[0]);
            crs(21,1,c[13] + "you are stupid and should be sad for letting this happen" + c[0]);
            crs(0,0,"");
            System.exit(0);
            //throw new ArrayIndexOutOfBoundsException("Too Little Text Box Data!");
            }
            if(cursor == i-2) {
            System.out.print(c[cColor + (mode ? 1:0)*hColor]);
            }
            crs(y+i,x+1, drawText + c[0]);
            if(textType[boxNum][i-3]%10 == 1) {
            crs(y+i,x+1+drawText.length()+1,"" + boxData[textType[boxNum][i-3]/10]);
            }
        }
        crs(y+height-6,x,"\u2559" + hbar + "\u255C"); //set bottom bar
    }
    }

    public static void RenderOrderUpd() {
        if(oldPW != PrimaryWindow) {
            int f = index(boxRenderOrder,PrimaryWindow+1);
            int j = boxRenderOrder[3];
            int k = 0;
            boxRenderOrder[3] = boxRenderOrder[f];
            for(int i = 2; i >= f; i--) {
                if(i%2 == 0) {
                    k = boxRenderOrder[i];
                    boxRenderOrder[i] = j;
                } else {
                    j = boxRenderOrder[i];
                    boxRenderOrder[i] = k;
                }
            }
            oldPW = PrimaryWindow;
            curs = 1;
        }
    }

    public static void Render(int boxcount) {
        for(int i = 0; i < boxcount; i++) {
            int j = boxRenderOrder[i];
            box(upos[0][j],upos[1][j],usz[0][j],usz[1][j],text,curs,j-1);
        }
        crs(0,0,"Fullscreen this Terminal by pressing the '^' symbol (far right) and hiding the sidebar, then press the green reload button. Make Sure VScode is resized to fill the screen but not fullscreened.");
    }
    
    public static int index(int[] array, int val) {
        int len = array.length;
        for(int i = 0; i<len;i++) {
            if(array[i] == val) {
            return i;
            }
        }
        return -1;
    }

    public static void windowList(String[] availiable) {
        String hbar = "", wid = "";
        int num = 0;
        for(int i = 0; i < termWidth-2; i++) {hbar = hbar + "\u2500"; wid = wid + " ";} //create a horizontal bar
        crs(termHeight-5,0,"\u2553"+hbar+"\u2556");
        for(int i = 1; i < 5; i++) {
        crs(termHeight-i,0,"\u2551"+wid+"\u2551");
        }
        crs(termHeight,0,"\u2559"+hbar+"\u255C");
        for(int i = 20; i < termWidth-1 && (i/20) <= avail.length; i+=20) {
            num = (i/20)-1;
            crs(termHeight-4,i,"\u2502");
            crs(termHeight-3,i,"\u2502");
            crs(termHeight-2,i,"\u2502");
            crs(termHeight-1,i,"\u2502");
            crs(termHeight-4,i-18,avail[num]);
            if(sl+setWindow == num) {
            PrimaryWindow = num;
            crs(termHeight-1,i-15,c[10] + "Primary Window" + c[0]);
            }
        }
    }
}