import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hinotohui on 18/3/15.
 */

//跳表
public class SkipList<K extends Comparable, V> {
    private int level;

    private static class Node<K extends Comparable, V> {
        private K key;
        private V value;

        private ArrayList<Node<K, V>> nexts;

        private Node() {

        }

        public Node(Node<K, V> node) {
            this.nexts = new ArrayList<Node<K, V>>();
            this.nexts.add(node);
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;

            this.nexts = new ArrayList<Node<K, V>>();
        }

        public int comPareKey(Node<K, V> node) {
            //一个trick，哨兵作为终点默认key最大
            if (this.key == null)
                return 1;
            return this.key.compareTo(node.key);
        }

        public int comPareKey(K key) {
            //一个trick，哨兵作为终点默认key最大
            if (this.key == null)
                return 1;
            return this.key.compareTo(key);
        }

        public void setNext(int index, Node<K, V> node) {
            this.nexts.set(index, node);
        }

        public Node<K, V> getNext(int index) {
            return this.nexts.get(index);
        }

        public void addNext(Node<K, V> node) {
            this.nexts.add(node);
        }

        public V getValue() {
            return value;
        }

        public static <K extends Comparable, V> Node getSentryNode() {
            return new Node<K, V>();
        }
    }

    private Node<K, V> sentry;
    private Node<K, V> head;

    public SkipList() {
        //哨兵
        this.sentry = Node.<K, V>getSentryNode();
        this.head = new Node<K, V>(this.sentry);

        this.level = 1;
    }

    private int level() {
        if (this.level <= 3)
            return this.level;

        int level = new Random().nextInt(2 * this.level);

        return level > this.level ? this.level : level;
    }

    public V search(K key) {
        Node<K, V> node=_searchKey(key);
        if (node!=null)
            return node.getValue();
        return null;
    }

    private Node<K, V> _searchKey(K key){
        Node<K, V> node = this.head;
        Node<K, V> pre = node;

        for (int level = this.level - 1; level >= 0; level--) {
            node = pre;

            while (true) {
                pre = node;
                node = node.getNext(level);

                int result = node.comPareKey(key);

                if (result == 0)
                    return node;
                else if (result > 0) {
                    break;
                }
            }

        }

        return null;
    }

    public void remove(K key) throws KeyNotExistException {
        Node<K, V> node = this.head;
        Node<K, V> pre = node;

        for (int level = this.level - 1; level >= 0; level--) {
            node = pre;

            while (true) {
                pre = node;
                node = node.getNext(level);

                int result = node.comPareKey(key);

                if (result == 0){
                    pre.setNext(level,node.getNext(level));
                    break;
                    
                }else if (result > 0) {
                    break;
                }
            }
        }
    }

    public void insert(K key, V value) throws Exception {
        Node<K, V> kvNode = new Node<K, V>(key, value);

        ArrayList<Node<K, V>> histories = new ArrayList<Node<K, V>>();

        Node<K, V> node = this.head;
        Node<K, V> pre = node;

        for (int i = level - 1; i >= 0; i--) {
            node = pre;

            while (node != sentry) {
                pre = node;
                node = node.getNext(i);

                int result = node.comPareKey(kvNode);

                if (result == 0) {
                    throw new KeyExistException();
                } else if (result > 0) {
                    histories.add(pre);
                    break;
                }
            }

        }

        int level = level();

        for (int i = level - 1; i >= 0; i--) {
            Node<K, V> history = histories.get(i);

            kvNode.addNext(history.getNext(level - 1 - i));
            history.setNext(level - 1 - i, kvNode);
        }

        //最后一个
        if (level >= this.level) {
            this.level++;

            kvNode.addNext(sentry);
            this.head.addNext(kvNode);
        }
    }


    public static void main(String[] args) throws Exception {
        /*SkipList<Integer, String> skipList = new SkipList<Integer, String>();

        for (int i = 0; i < 100; i++) {
            skipList.insert(i, i + "\tjob");
        }

        System.out.println(skipList.search(101));*/
        System.out.println(new String(new char[]{'\ue40a', '\ue40b'}));
    }
}
