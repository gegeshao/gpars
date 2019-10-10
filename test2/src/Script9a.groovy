import groovyx.gpars.dataflow.DataflowVariable
import groovyx.gpars.dataflow.Dataflows
import groovyx.gpars.dataflow.Dataflows

def a = new DataflowVariable()
def b = new DataflowVariable()

   a>>{println"$it"}
   a>>{println"second"}

   a.whenBound {String i ->sleep 20;println"3$i"}
   println("start bound value")
   a<<b   //<< reload operator leftshift
   b<<"cc"
   b.whenBound {println"b is $b.val"}
   println("has been bounded value")
  while(1){}

/* def b = new Dataflows()
 task{
  b.f=b.c+b.x
  println("${b.f}")
 }
task{
 b.c=1
 b.x=2
}*/


