import groovyx.gpars.dataflow.Dataflow
import groovyx.gpars.dataflow.DataflowVariable
import groovyx.gpars.dataflow.LazyDataflowVariable
import groovyx.gpars.dataflow.Promise
import org.codehaus.groovy.runtime.MethodClosure

/**
 * Created by root on 10/9/19.
 */
class test3 {


   public  static void main(String[] args){

         def  Closure[] defs = [{sleep 1000 ;"" },{sleep 1000 ;"2"},{sleep 1000 ;""}];

            final DataflowVariable variable = new DataflowVariable()
            final DataflowVariable result = new DataflowVariable()
            final LazyDataflowVariable variable1 = new LazyDataflowVariable({sleep 1000 ;"this is variable1" })
            def ChainedDataflowVariablenew cdata= new ChainedDataflowVariablenew(defs)

            /*variable.whenBound {-> result << 1}
            variable << 4*/
            //assert 1 == result.val

       /* variable.whenBound(Dataflow.DATA_FLOW_GROUP) {-> result << 1}
        variable << 4*/
            Promise p =cdata
            /*long t =System.currentTimeMillis()
            System.out.println("start time is"+t)
            System.out.println("result = "+cdata.val)
            System.out.println("consume time is"+(System.currentTimeMillis()-t))*/
           // p.then{println("succeed to get value")}
             def succeed={String prop->println"succeed,the value is $prop"}
             def failed ={String prop->println"failed,the value is$prop"}

             p.then(succeed,failed)
             while(1){}

             //succeed.call()

           // System.out.println("state is "+)
    }
/*    private void handle(int i,int j){
        println("j="+j)
        println("i+j="+(i+j))

    }*/
}
