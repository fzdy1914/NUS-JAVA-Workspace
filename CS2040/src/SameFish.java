/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class SameFish {
    private void run() {
        Scanner in = new Scanner(System.in);
        int fishNum = in.nextInt();
        String[] fishs = new String[fishNum];
        int[] differ = new int[fishNum];
        //differ[0] = 0;
        fishs[0] = in.next();
        TreeSet<Integer> set = new TreeSet<>();

        for (int i = 1; i < fishNum; i++) {
            fishs[i] = in.next();
            if(!fishs[i].equals(fishs[i - 1])) {
                set.add(i);
            }
        }
        int opNum = in.nextInt();
        for (int i = 0; i < opNum; i++) {
            String op = in.next();
            switch (op) {
                case "REPLACE":
                    int num = in.nextInt() - 1;
                    String name = in.next();
                    if(name.equals(fishs[num])) {
                        continue;
                    } else {
                        fishs[num] = name;
                        if(num > 0 && !name.equals(fishs[num - 1])) {
                            set.add(num);
                        } else {
                            set.remove(num);
                        }
                        if(num < fishNum - 1 && name.equals(fishs[num + 1])) {
                            set.remove(num + 1);
                        } else {
                            set.add(num + 1);
                        }
                    }
                    break;
                case "SAME":
                    int first = in.nextInt() - 1;
                    int second = in.nextInt() - 1;
                    Integer temp = set.higher(first);
                    if(temp == null || temp > second) {
                        System.out.println(fishs[first]);
                    } else {
                        System.out.println("DIFFERENT");
                    }
                    break;

            }

        }


    }
    public static void main(String[] args) {
        SameFish newSameFish = new SameFish();
        newSameFish.run();
    }
}
