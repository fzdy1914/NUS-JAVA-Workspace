package exercise7and8;

import java.math.BigInteger;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Main {
	
	static Predicate<Integer> p1 = x -> x > 1;
	static Predicate<Integer> p2 = x -> x > 3;
	static <T> Predicate<T> and(Predicate<T> p1, Predicate<T> p2) {
		  return x -> p1.test(x) && p2.test(x);
	}
	static int hhh(int i) {
		  return new Random(1).nextInt() + i;
	}
	
	static LongStream factors(long y){
		return LongStream.range(1, y + 1)
			      .filter(x -> y % x == 0);
	}
	
	static boolean isPrime(long x) {
	  if(x == 1) {
        return false;
	  }
	  return LongStream.range(2, x)
	    .noneMatch(i -> x % i == 0);
	}
	static LongStream primefactors(long y){
		return factors(y).filter(x -> isPrime(x));	
	}
	
	public static <T,U,R> Stream<R> product(List<T> list1, List<U> list2, 
			BiFunction<? super T, ? super U, R> f){
		
		return list1.stream()
				    .flatMap(x -> list2.stream().map(y -> f.apply(x, y)));
	}
	static LongStream omega(int n){
		return LongStream.range(1, n + 1).map(x -> primefactors(x).count());
	}

	public static Stream<BigInteger> fibonacci(int n){
		class Generator implements Supplier<BigInteger>{
			Pair<BigInteger, BigInteger> pair;
			Generator(Pair<BigInteger, BigInteger> pair){
				this.pair = pair;
			}
			public BigInteger get(){
				BigInteger head = pair.head();
				BigInteger tail = pair.tail();
				BigInteger result = head.add(tail);
				pair = new Pair<BigInteger, BigInteger>(tail, result);
				return tail;
			}
		}
		Supplier<BigInteger> g = new Generator(new Pair<BigInteger, BigInteger>(new BigInteger("0"), new BigInteger("1")));
		return Stream.generate(g)
				     .limit(n);
	}
	public static void main(String[] args){
		//factors(6).forEach(System.out::println);
		//primefactors(6).forEach(System.out::println);
		//omega(6).forEach(System.out::println);
		
		/*
		ArrayList<Integer> list1 = new ArrayList<>();
		ArrayList<Integer> list2 = new ArrayList<>();
		Collections.addAll(list1, 1, 2, 3, 4);
		Collections.addAll(list2, 10, 20);
		product(list1, list2, (str1, str2) -> str1 + str2)
		    .forEach(System.out::println);
		*/
		//fibonacci(20).forEach(System.out::println);;
	}
}

class Pair<U, V>{
	U u;
	V v;
	Pair(U u, V v){
		this.u = u;
		this.v = v;				
	}
	U head(){
		return this.u;
	}
	V tail(){
		return this.v;
	}
}

