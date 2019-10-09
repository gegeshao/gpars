/**
 * Created by root on 10/8/19.
 */

import groovyx.gpars.MessagingRunnable;
import groovyx.gpars.dataflow.DataflowVariable;
import groovyx.gpars.dataflow.LazyDataflowVariable;
import groovyx.gpars.dataflow.Promise;
import groovyx.gpars.group.DefaultPGroup;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @ClassName DataflowTaskDemo
 * @description TODO
 * @Author root
 * @Date 10/8/19 5:32 PM
 * @Version 1.0
 **/
public class DataflowTaskDemo {
     public static void main(final String[] args){
         final DefaultPGroup group = new DefaultPGroup(10);
         final DataflowVariable dataflow = new DataflowVariable();

      /*   group.task(new Runnable() {
             @Override
             public void run() {
                 dataflow.bind(33);
             }
         });*/
         group.getThreadPool().execute(new Runnable() {
             @Override
             public void run() {
                 dataflow.bind(33);
             }
         });
         final Promise result = group.task(new Callable() {
             public Object call() throws Exception{
                 return (Integer)dataflow.getVal()+2;
             }
         });

         result.whenBound(new MessagingRunnable<Integer>() {
             @Override
             protected void doRun(final Integer integer) {
                 System.out.println("arguments = "+integer);
             }

         });

         System.out.println("result = "+result.toString());
         //LazyDataflowVariable
     }

}
