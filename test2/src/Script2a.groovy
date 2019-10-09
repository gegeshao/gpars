/**
 * Created by root on 9/14/19.
 */
/*
println "In Script2a"


Binding propertyBinding = new Binding()

//propertyBinding.setProperty("newproperty","ddd")

//def person = new person()
propertyBinding.property={
    id, Closure c ->
        //c.call()
        //println "cc"
        person p = new person()
       // Closure p=c.clone()
        c.delegate=p
        c.call()
        //p.call()
}
//Binding propertyBinding = new Binding()
shell = new GroovyShell(propertyBinding)
shell.evaluate(new File('Script1a.groovy'))
*/

//System.out.print(toString(resoult))
println "In Script2"
class person{
    /* storageVolume1(){
         println"storageVolume1"
     }
     storageVolume2(){
         println"storageVolume2"
     }*/
    // def name = "shaoChen"
    // def age = 20
    //def storage[]
    def propertyMissing(String name, value) { println("the missingProperty name is $name \r\n value is $value\r\n") }
    def propertyMissing(String name) {  println("the missingProperty name is $name \r\n") }
    def methodMissing(String name, def args) {
        return "this methodMissing is $name"
    }
    def int cc(){
        println "invoke cc"
        return 1
    }
    def int ccc(int a){
        println "invoke ccc"
        return a+1
    }
}
def person =new person()
  //person.ddcd = "dd"
Binding propertyBinding = new Binding()
propertyBinding.property = {
    id,Closure c ->
        //def person =new person()
        c.delegate=person
        c.call()
        //  System.out.println()
}
shell = new GroovyShell(propertyBinding)
shell.evaluate(new File('Script1a.groovy'))