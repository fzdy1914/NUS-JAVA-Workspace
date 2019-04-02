package ass2.taskB;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class BoxStack {

    private static ArrayList<Integer> stackBoxes(int N, int S, int[] W) {
        // find the biggest item index.
        ArrayList<Item> items = new ArrayList<>();
        F[] f = new F[S + 1];
        for (int i = 1; i <= N; ++i) {
            items.add(new Item(W[i - 1], i));
        }
        Collections.sort(items, (s1, s2) -> s1.weight - s2.weight);
        Item max = items.get(N - 1);
        int maxIndex = max.index;

        for (int i = 0; i <= S; ++i) {
            f[i] = new F(0, 0);
        }

        for (int i = 1; i <= N; i++) {
            if(i == maxIndex) {
                continue; // ignore the largest item
            }
            for (int j = S; j >= 0; j--) {
                if (j < W[i - 1]) {
                    continue;
                } else {
                    int chooseI = f[j - W[i - 1]].sumW + W[i - 1];
                    int notChooseI = f[j].sumW;
                    if (chooseI > notChooseI) {
                        f[j] = new F(chooseI, i);
                    }
                }
            }
        }
        F result = f[S];
        ArrayList<Integer> visited = new ArrayList<>();
        while(result.lastID != 0) {
            visited.add(result.lastID);
            result = f[result.sumW - W[result.lastID - 1]];
        }

        visited.add(maxIndex);
        return visited;
    }

    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        int N = sc.nextInt();
        int S = sc.nextInt();
        int[] W = new int[N];
        for (int i = 0; i < N; ++i) {
            W[i] = sc.nextInt();
        }
        System.out.println(stackBoxes(N, S, W).stream().map(i->i.toString()).collect(Collectors.joining(" ")));
    }

    private static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1) buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer++];
        }
    }
}
class Item {
    int weight;
    int index;

    public Item(int weight, int index) {
        this.weight = weight;
        this.index = index;
    }
}
class F {
    int sumW;
    int lastID;

    public F(int sumW, int lastID) {
        this.sumW = sumW;
        this.lastID = lastID;
    }
}