package p;
abstract class B {
  void foo(A<?> a)
  {
    a.get().<error descr="Cannot resolve method 'reversed' in 'Comparator'">reversed</error>();
  }
}