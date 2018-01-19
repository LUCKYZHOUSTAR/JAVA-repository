package concurrency;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:11 2018/1/19
 */
public class MutilThread1 {
    Person person = new Person(1, "张三");


    public static void main(String[] args) throws Exception {
        MutilThread1 mainThread = new MutilThread1();
        Thread threadA = new Thread(() -> {
            Person person = mainThread.modify(2, "李四");
            System.out.println(Thread.currentThread().getName() + person.getName());

        }, "ThreadA");

        Thread threadB = new Thread(() -> {
            Person person = mainThread.modify(3, "王五");
            System.out.println(Thread.currentThread().getName() + person.getName());

        }, "threadB");

        threadA.start();
        threadB.start();


        Thread.sleep(3000L);
    }

    public Person modify(int id, String name) {
        person.setId(id);
        person.setName(name);
        return person;
    }


    class Person {
        private int id;
        private String name;


        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
