import groovyx.gpars.dataflow.Dataflow
import groovyx.gpars.dataflow.DataflowVariable
import groovyx.gpars.dataflow.Dataflows
import groovyx.gpars.dataflow.LazyDataflowVariable
import groovyx.gpars.dataflow.Promise
import org.codehaus.groovy.runtime.MethodClosure

/**
 * Created by root on 10/9/19.
 */
class test3 {


   public  static void main(String[] args){

         def  Closure[] defs = [{sleep 1000 ;"" },{sleep 1000 ;"2"},{sleep 1000 ;""}];

            final Dataflows dflow = new Dataflows()
            final DataflowVariable variable = new DataflowVariable()
             final DataflowVariable variable2 = new DataflowVariable()
            final DataflowVariable result = new DataflowVariable()
            final LazyDataflowVariable variable1 = new LazyDataflowVariable({sleep 1000 ;"this is variable1" })
            def ChainedDataflowVariablenew cdata= new ChainedDataflowVariablenew(defs)


            variable >>{sleep 5000 ;println "$it"}
            variable.whenBound {sleep 4000 ;println "3" }
             variable <<2
            //assert 1 == result.val
     /*  def a = new DataflowVariable()
       a >> {println "The variable has just been bound to $it"}

       a.whenBound{println "Just to confirm that the variable has been really set to $it"}*/


           variable2.whenBound(Dataflow.DATA_FLOW_GROUP) {-> result << 1}
           variable2 << 4
           println("$result")

            Promise p =cdata
            Promise p2 =variable1
            /*long t =System.currentTimeMillis()
            System.out.println("start time is"+t)
            System.out.println("result = "+cdata.val)
            System.out.println("consume time is"+(System.currentTimeMillis()-t))*/
           // p.then{println("succeed to get value")}
             def succeed={String prop->println"succeed,the value is $prop"}
             def failed ={String prop->println"failed,the value is$prop"}


             p.then(succeed,failed)
             p2.then(succeed,failed)
             //p2.then(succeed,failed)
             while(1){}

             //succeed.call()

           // System.out.println("state is "+)
    }
/*    private void handle(int i,int j){
        println("j="+j)
        println("i+j="+(i+j))

    }*/
}
