/**
 * Created by root on 9/26/19.
 */
class Foo {
    def storage = [:]
    def propertyMissing(String name, value) { storage[name] = value }// like a setter
    def propertyMissing(String name) { storage[name] }  //like a getter
}

def f = new Foo()
f.foo = "bar" // set property

//assert f.foo == "bar"
System.out.println(f.foo) //get property