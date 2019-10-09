import groovyx.gpars.dataflow.DataflowVariable
import groovyx.gpars.dataflow.Dataflows
import groovyx.gpars.dataflow.Dataflows

/*def a = new DataflowVariable()

  a>>{println"$it"}
  a>>{println"second"}

   a.whenBound {sleep 20;println"3"}
   println("start bound value")
   a<<2
   println("has been bounded value")
  while(1){}*/

 def b = new Dataflows()
 task{
  b.f=b.c+b.x
  println("${b.f}")
 }
task{
 b.c=1
 b.x=2
}


