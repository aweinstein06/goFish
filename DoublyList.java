/*
	Apolline Weinstein
	Mr. Cruté
	AT1
	Date created: fri, jan 13, 2023
	Date last edited: sun, feb 6, 2023
	Description: DoublyList class that has global variables head(ListNode E), tail(ListNode E), and size(integer) and has methods size (takes in nothing), 
	front(takes nothing), rear(takes nothing), add(takes in an object), isEmpty(takes in nothing), remove(takes in an object), toString(takes in nothing), 
	reverseToString(takes nothing), contains(takes in an object), append(takes in an object), indexOf(takes in an object), valueAt(takes in an integer), 
	pop(takes in nothing), popRear(takes nothing), pop(takes in an integer), insert(takes in an integer and an object), set(takes in an integer and an object),
	indicesOf(takes in an object), removeAll(takes in an object), and private class ListNode
*/

import java.util.*;
public class DoublyList<E extends Comparable<? super E>> implements MyList<E>{

	private ListNode<E> head;
	private ListNode<E> tail;
	private int size;

	//constructor
	public DoublyList(){
		head = new ListNode<E>();
		tail = new ListNode<E>();
		size = 0;
	}

	//inputs nothing, return size of list
	public int size(){
		return size;
	}

	//inputs nothing, returns info at the front of the list if the list isn't empty
	public E front(){
		if(size == 0){
			return null;
		}
		return head.next.info;
	}

	//inputs nothing, returns info at the last node of the list of it isn't empty
	public E rear(){
		if(size == 0){
			return null;
		}
		return tail.prev.info;
	}

	//inputs an object, add the object to the front of the list if it does not already exist in the list
	public void add(E obj){ 
		if(isEmpty()){
			head.next = new ListNode<E>(obj, null, null);
			tail.prev = head.next;
		}else{
			ListNode<E> pointNext = head.next;
			head.next = new ListNode<E>(obj, pointNext, null);
			pointNext.prev = head.next;
		}
		size++;
	}

	//inputs nothing, if the size is 0 or list is empty, return true, else return false
	public boolean isEmpty(){
		if(size == 0){
			return true;
		}
		return false;
	}

	//inputs an object, if the object is at the first index or the last index, process changes since one pointer must 
	//be null and must not point back to the head or tail, uses a while loop to loop through the function, if one of 
	//them equals the inputed object, it is removed, size is decreased and the object i returned, if the list doesn't 
	//contain the object, return null
	public E remove(E obj){
		if(isEmpty()){
			return null;
		}else{
			if(size == 1 && contains(obj)){
				head.next = null;
				tail.prev = null;
				size--;
				return obj;
			}else if(head.next.info == obj){
				head.next = head.next.next;
				head.next.prev.next = null;
				head.next.prev = null;
				size--;
				return obj;
			}else if(tail.prev.info == obj){
				tail.prev = tail.prev.prev;
				tail.prev.next.prev = null;
				tail.prev.next = null;
				size--;
				return obj;
			}else{
				ListNode<E> curr = head.next;
				while(curr != null){
					if(curr.info.equals(obj)){
						curr.prev.next = curr.next;
						curr.next.prev = curr.prev;
						curr.next = null;
						curr.prev = null;
						size--;
						return obj;
					}
					curr = curr.next;
				}
			}
		}
		return null;
	}

	//inputs nothing, returns a string of every value, separated by commas and surrounded by brackets
	public String toString(){
		String list = "[";
		ListNode<E> curr = head.next;
		ListNode<E> prev = head;
		while(curr != null){
			if(curr.next!=null){
				list +=  curr.info + ", ";
			}else{
				list += curr.info;
			}
			prev = curr;
			curr = curr.next;
		}
		list += "]";
		return list;
	}


	//inputs nothing, loops through the list and adds each element to a string from the last to the first, 
	//separated by commas and surrounded by brackets
	public String reverseToString(){
		String list = "[";
		ListNode<E> curr = tail.prev;
		while(curr != null){
			if(curr == head.next){
				list += curr.info;
			}else{
				list +=  curr.info + ", ";
			}
			curr = curr.prev;
		}
		list += "]";
		return list;
	}

	//inputs an object, goes through the list using a while loop and returns true if the inputed object equals any
	//object in the list
	public boolean contains(E obj){	
		ListNode<E> curr = head.next;
		while(curr != null){
			if(curr.info.equals(obj)){
				return true;
			}
			curr = curr.next;
		}
		return false;
	}

	//inputs an object and uses the insert function to add/insert the object to the end of the list, returns nothing
	public void append(E obj){ 
		insert(size, obj);
	}

	//inputs an object, if the object exists in the list, loop through the list while keeping a counter which will
	//keep track of the index, once object id found, return the counter
	public int indexOf(E obj){ 
		ListNode<E> curr = head.next;
		int count = 0;
		if(contains(obj)){
			while(curr != null){
				if(curr.info.equals(obj)){
					return count;
				}
				curr = curr.next;
				count++;
			}
		}
		return -1;
	}

	//inputs an integer of the position, makes sure it isn't -1 or greater than the size, loops through the list 
	//and keeps a counter for the position, when the position equals the position requested, return the node info at 
	//that position
	public E valueAt(int pos){
		ListNode<E> curr = head.next;
		int count = 0;
		if(pos != -1 && pos <= size){
			 while(curr != null){
				if(count == pos){
					return curr.info;
				}
				curr = curr.next;
				count++;
			}
		}
		return null;
	}

	//inputs nothing, uses the remove function to remove the first value (found using valueAt) of the function, returns the obj removed
	public E pop(){
		return remove(valueAt(0));
	}

	//inputs nothing, uses the remove function to remove the last value (found using valueAt) of the function, returns the obj removed
	public E popRear(){
		return remove(valueAt(size-1));
	}

	//inputs nothing, uses algorithm of remove function, however specifies which occurence of an object should be removed (remove() 
	//removes first instance of an object), returns the obj removed
	public E pop(int pos){
		E hold = valueAt(pos);
		int count = 0;
		ListNode<E> curr = head.next;
		if(size == 1){
			head.next = null;
			tail.prev = null;
			size--;
			return hold;
		}else if(pos == 0){
			pop();
		}else if(pos+1 == size){
 			popRear();
		}else{
			while(curr != null){
				if(count == pos){
					curr.prev.next = curr.next;
					curr.next.prev = curr.prev;
					curr.next = null;
					curr.prev = null;			
					size--;
					return hold;
				}
				curr = curr.next;
				count++;
			}	
		}
		return null;
	}

	// inputs an integer position and an object, if the list doesn't already contain the object and the position is
	// not greater than the size, loop through the list and once the value before where the object should be placed 
	// is reached, point its pointer to the new inserted list node which pointer will point to the node its previous
	// node was pointing at, returns nothing
	public void insert(int pos, E obj){
		if(pos == 0){
			add(obj);
		}else if(pos < size){
			ListNode<E> curr = head.next;
			for(int i = 0; i < pos; i++){
				if(i == pos-1){
					curr.next = new ListNode<E>(obj, curr.next, curr);
					curr.next.prev = curr;
					curr.next.next.prev = curr.next;
					size++;
				}
				curr = curr.next;
			}
		}else if(pos == size){
			ListNode<E> pointPrev = tail.prev;
			tail.prev = new ListNode<E>(obj, null, pointPrev);
			pointPrev.next = tail.prev;
			size++;
		}
	}

	//inputs an object and a position, inserts the object at that position and removes the following object
	//therefore replacing the object at that position with the object inputed, returns nothing
	public void set(int pos, E obj){
		insert(pos, obj);
		pop(pos+1);
	}

	//inputs an object, loops through the list to find instances of the object, keeps a counter to keep track of the indices
	//if an object is found, the index is added to the array of indiceds, which is returned once the loop finished looping through
	//the list
	public ArrayList<Integer> indicesOf(E obj){
		ArrayList<Integer> indices = new ArrayList<Integer>();
		ListNode<E> curr = head.next;
		ListNode<E> prev = head;
		int count = 0;
		if(contains(obj)){
			while(curr != null){
				if(curr.info.equals(obj)){
					indices.add(count);
				}
				prev = curr;
				curr = curr.next;
				count++;
			}
		}
		return indices;
	}

	//inputs an object, gets all the indices of this object, loops through the list, and removes the objects at each index
	//returns arraylist of objects removed
	public void removeAll(E obj){
		ListNode<E> curr = head.next;
		ListNode<E> prev = head;
		ArrayList<Integer> indices = indicesOf(obj);
		for(int i = 0; i < indices.size(); i++){
			remove(valueAt(indices.get(i)-i));
		}
	}	

	private static class ListNode<E>{
		private E info; 
		ListNode<E> next; //pointer
		ListNode<E> prev; //pointer

		public ListNode(){
			info = null;
			next = null;
			prev = null;
		}
		public ListNode(E obj, ListNode<E> nextNode, ListNode<E> prevNode){
			info = obj;
			next = nextNode;
			prev = prevNode;
		}
	}

}

