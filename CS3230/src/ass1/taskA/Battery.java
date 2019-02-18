package ass1.taskA;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Battery {
    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        int N = sc.nextInt();
        int E = sc.nextInt();
        int T = sc.nextInt();

        ArrayList<ArrayList<Edge>> adjacencyList = new ArrayList<>();

        for(int i = 0; i < N; i++){
        	adjacencyList.add(new ArrayList<>());
        }

        for(int i = 0; i < E; i++){
        	int first = sc.nextInt() - 1;
        	int second = sc.nextInt() - 1;
        	int distance = sc.nextInt();
        	adjacencyList.get(first).add(new Edge(second, distance));
        	adjacencyList.get(second).add(new Edge(first, distance));
        }

        int low = 0;
        int high = T;
        while(low < high) {
            int mid = (low + high) / 2;
            if(verifyDistanceAlt(filterList(adjacencyList,mid), N, T)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        System.out.println(low);
    }

    private static ArrayList<ArrayList<Edge>> filterList(ArrayList<ArrayList<Edge>> adjacencyList, int length) {
        ArrayList<ArrayList<Edge>> newAdjacencyList = new ArrayList<>();
        for(ArrayList<Edge> list : adjacencyList) {
            newAdjacencyList.add(new ArrayList<>(list.stream().filter(e -> e.distance <= length).collect(Collectors.toList())));
        }
        return newAdjacencyList;
    }

    private static boolean verifyDistanceAlt(ArrayList<ArrayList<Edge>> adjacencyList, int N, int T) {
        int[] distances = new int[N];

        for(int i = 0; i < N; i++){
        	distances[i] = Integer.MAX_VALUE;
        }
        PriorityQueue<DijkstraNode> heap = new PriorityQueue<>((n1, n2) -> n1.distance - n2.distance);

        distances[0] = 0;
        heap.add(new DijkstraNode(0, 0));
        while(heap.size() != 0){
        	DijkstraNode node = heap.poll();
        	for(int i = 0; i < adjacencyList.get(node.number).size(); i++){
    			Edge edge = adjacencyList.get(node.number).get(i);
    			int newDistance = node.distance + edge.distance;
    			if(newDistance < distances[edge.neighbor]){
    				distances[edge.neighbor] = newDistance;
    				heap.add(new DijkstraNode(edge.neighbor, newDistance));
    			}
    		}
        }

        return distances[N - 1] <= T;
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

class Edge{
    public int neighbor;
    public int distance;
    Edge(int neighbor, int distance){
        this.neighbor = neighbor;
        this.distance = distance;
    }
}

class DijkstraNode {
	public int distance;
	public int number;

	DijkstraNode(int number, int distance){
	    this.distance = distance;
	    this.number = number;
	}
}
