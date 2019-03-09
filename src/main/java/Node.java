import java.util.ArrayList;

/**
 * Created by hinotohui on 18/3/15.
 */
public class Node<K extends Comparable,V>  {
    private K key;
    private V value;

    private ArrayList<Node<K,V>> nexts;

    private Node(){

    }

    public Node(Node<K,V> node){
        this.nexts=new ArrayList<Node<K, V>>();
        this.nexts.add(node);
    }

    public Node(K key,V value){
        this.key=key;
        this.value=value;

        this.nexts=new ArrayList<Node<K, V>>();
    }

    public int level(){
        return nexts.size();
    }

    public int comPareKey(Node<K,V> node){
        //一个trick，哨兵作为终点默认key最大
        if (this.key==null)
            return 1;
        return this.key.compareTo(node.key);
    }

    public int comPareKey(K key){
        //一个trick，哨兵作为终点默认key最大
        if(this.key==null)
            return 1;
        return this.key.compareTo(key);
    }

    public void setNext(int index,Node<K,V> node) {
        this.nexts.set(index,node);
    }

    public Node<K,V> getNext(int index) {
        return this.nexts.get(index);
    }

    public void addNext(Node<K,V> node){
        this.nexts.add(node);
    }

    public V getValue() {
        return value;
    }

    public static <K extends Comparable,V> Node getSentryNode(){
        return new Node<K,V>();
    }
}
