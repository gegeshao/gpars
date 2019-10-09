import org.codehaus.groovy.runtime.MethodClosure

/**
 * Created by root on 10/4/19.
 */
def Mutiplite = {x,y->return x*y}
def cc = Mutiplite.curry(2,7)
def handle(int i ,int j){
    println("j="+j)
    println("3+j="+(3+j))
}

   MethodClosure c = new MethodClosure(this , "handle")
   Closure cd = c.curry(3)
   cd.call()


println"$d"