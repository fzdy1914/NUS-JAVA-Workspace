import java.util.*;

class Energy{
    public static void main(String arg[]){
        Energy a = new Energy();
        a.run();
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        int opNum = sc.nextInt();
        TreeSet<Quest> pq = new TreeSet<>(new QuestComp());
        for(int i = 0; i <opNum ; i++){
            String command = sc.next();
            //add
            if (command.equals("add")){
                pq.add(new Quest(sc.nextInt(),sc.nextInt()));
            }
            //query
            else if(command.equals("query")){
                int currx = sc.nextInt();
                boolean find = true;
                long reward = 0;
                while(find){
                    Quest q = new Quest(currx, Integer.MAX_VALUE);
                    Quest maxq = pq.floor(q);
                    if(maxq == null){
                        System.out.println(reward);
                        find = false;
                    }else{
                        currx -= maxq.gete();
                        reward += (long)maxq.getg();
                        pq.remove(maxq);
                    }
                }
            }
        }
    }
}

class Quest{
    static int id = 0;
    private int e,g,currentId;

    public Quest(int e, int g){
        this.e = e;
        this.g = g;
        currentId = id++;
    }

    //getter
    public int gete(){
        return e;
    }
    public int getg(){
        return g;
    }
    public int getId() {
        return currentId;
    }
}

class QuestComp implements Comparator<Quest>{
    @Override
    public int compare(Quest q1, Quest q2){
        if(q1.gete() != q2.gete()) {
            return q1.gete() - q2.gete();
        } else if (q1.getg() != q2.getg()){
            return q1.getg() - q2.getg();
        } else {
            return q1.getId() - q2.getId();
        }
    }
}
