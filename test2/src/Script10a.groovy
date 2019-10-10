/**
 * Created by root on 10/10/19.
 */

def d=new DDD()
Closure cc={callback call-> d.test(1,call)};
cc.call(new callback() {
    @Override
    public void handle(int c) {
        System.out.print("i ="+c);
    }
});