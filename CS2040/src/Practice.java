import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.*;
public class Practice {
  public static void a(){
	  System.out.println(1);  
  }
  public static void b(){
	  double sum = 0;
	  for(int i=0; i<100000000; i++){
		  sum += i;
	  }
	  System.out.println(sum); 
  }
  public static void c(){
	  System.out.println(3); 
  }
  public static int f(int x){
	  return 1;
  }
  public static int g(int x){
	  return 1 + x;
  }
  public static int h(int x){
	  return 2 + x;
  }
  public static int i(int x,int y){
	  return x - y;
  }
  static class A {
	 static int g(){
		 return 1;
	 }
  }
  public static void main(String[] args) {
	  Practice.A a = new A();
	  A b = new A();
	  Function<Integer, Number> c = s -> s;  

  }
  
  static void a(Func functor){
	  System.out.println(functor.func(1, 2, 3));
  }
  static int sum(int x, int y, int z){
	  return x + y + z;
  }
}

@FunctionalInterface
interface Func {
  int func(int a, int b, int c);
}
class A implements I,J{
  private void a(){
	  
  }
  public void b(){
	  
  }
  public void g(){
	  
  }
}
class B extends A{
  public void b(){
	  
  }
}
class C extends B{
  int a = 0;
  public void a(){
	  
  }
}
class D<T>{

}
interface I{
	default void g(){
		
	}
}
interface J{
	void b();
}
class E<T extends E<T>>{
	
}