import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

	private int length; 
	Lock lock = new ReentrantLock();
	Condition putCondition = lock.newCondition();
	Condition takeCondition = lock.newCondition();
	
	public static void main(String args[]) throws InterruptedException{
		ConditionDemo condDemo = new ConditionDemo(5);
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 0; i < 20; i++){
					condDemo.put("num"+i);
				}
			}
		});
		thread.start();
		Thread.sleep(3000);
		for(int i = 0; i < 20; i++){
			Object result = condDemo.take();
			System.out.println("取出"+result);
			Thread.sleep(3000);
		}
		
	}
	public ConditionDemo(int length){
		this.length = length;
	}
	List<Object> quene = new ArrayList<>(length);
	public void put(Object obj){
		lock.lock();
			try{
				
				if(quene.size() < length){
					quene.add(obj);
					System.out.println(obj);
					takeCondition.signal();
				}else{
					putCondition.await();
				}
			}catch (Exception e) {
				// TODO: handle exception
			}finally{
				lock.unlock();
			}
	}
	public Object take(){

		Object a = null;
		lock.lock();
			try{
				if(quene.size() > 0){
					a = quene.get(0);
					quene.remove(0);
					putCondition.signal();
				}else{
					takeCondition.await();
				}
			}catch (Exception e) {
				// TODO: handle exception
			}finally{
				lock.unlock();
			}
		return a;
	}
	
}
