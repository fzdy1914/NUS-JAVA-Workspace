import java.util.concurrent.*;
import java.time.Instant;
import java.time.Duration;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    // Create a pool of threads
    ForkJoinPool pool = new ForkJoinPool();
    Instant start;
    Instant stop;
    int[] array = createArray(10_000_000);
    MergeSort mergeSort = new MergeSort(array, 0, array.length - 1);
    start = Instant.now();

    pool.invoke(mergeSort); 

    stop = Instant.now();
    System.out.println(Duration
        .between(start,stop)
        .toMillis() + " ms");
  }

  private static int[] createArray(final int size) {
    int[] array = new int[size];
    for (int i = size-1; i >= 0; i--) {
      array[i] = size;
    }
    return array;
  }
}

/**
 * Extends RecursiveAction.
 * Notice that the compute method does not return anything.
 */
class MergeSort extends RecursiveAction {
  final int FORK_THRESHOLD = 5000;
  private int array[];
  private int left;
  private int right;

  public MergeSort(int[] array, int left, int right) {
    this.array = array;
    this.left = left;
    this.right = right;
  }

  @Override
  protected void compute() {
    if (right - left <= FORK_THRESHOLD) {
      bubbleSort(array, left, right);
    } else if (left < right) {
      int mid = (left + right) / 2;
      MergeSort leftSort = 
          new MergeSort(array, left, mid);
      MergeSort rightSort = 
          new MergeSort(array, mid + 1, right);
      leftSort.fork();
      rightSort.compute();
      leftSort.join();
      merge(left, mid, right);
    }
  }
    
  private void bubbleSort(int[] arr, int left, int right) {
    int n = arr.length;
    for (int i = left; i <= right; i++) {
      for (int j = left; j <= right-i-1; j++) {
        if (arr[j] > arr[j+1]) {
          // swap temp and arr[i]
          int temp = arr[j];
          arr[j] = arr[j+1];
          arr[j+1] = temp;
        }
      }
    }
  }

  /**
   * Merge two parts of an array in sorted manner.
   * @param left  Left side of left array.
   * @param mid   Middle of separation.
   * @param right Right side of right array.
   */
  private void merge(int left, int mid, int right) {
    int temp [] = new int[right - left + 1];
    int x = left;
    int y = mid + 1;
    int z = 0;
    while (x <= mid && y <= right) {
      if (array[x] <= array[y]) {
        temp[z] = array[x];
          z++;
          x++;
      } else {
          temp[z] = array[y];
          z++;
          y++;
      }
    }
    while (y <= right) {
      temp[z++] = array[y++];
    }
    while (x <= mid) {
      temp[z++] = array[x++];
    }

    for (z = 0; z < temp.length; z++) {
      array[left + z] = temp[z];
    }
  }
}