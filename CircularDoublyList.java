/*
	Apolline Weinstein
	Mr. Cruté
	AT1
	Date created: wed, jan 25, 2023
	Date last edited: thurs, feb 2, 2023
	Description: CircularDoublyList class that has global variables dummy(ListNode E) and size(integer) and has methods size (takes in nothing), 
	front(takes nothing), rear(takes nothing), add(takes in an object), start(takes in an object), moveDummy(takes in nothing), isEmpty(takes in nothing), remove(takes in an object), toString(takes in nothing), 
	contains(takes in an object), append(takes in an object), indexOf(takes in an object), valueAt(takes in an integer), 
	pop(takes in nothing), popRear(takes nothing), pop(takes in an integer), insert(takes in an integer and an object), and private class ListNode
*/

import java.util.*;
public class CircularDoublyList<E extends Comparable<? super E>> implements MyList<E>{

	private ListNode<E> dummy;
	private int size;

	//constructor
	public CircularDoublyList(){
		dummy = new ListNode<E>();
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
		return dummy.next.info;
	}

	//inputs nothing, returns info at the last node of the list of it isn't empty
	public E rear(){
		if(size == 0){
			return null;
		}
		return dummy.prev.info;
	}

	//inputs an object, add the object to the front of the list if it does not already exist in the list
	public void add(E obj){ 
		if(isEmpty()){
			dummy.next = new ListNode<E>(obj, null, null);
			dummy.prev = dummy.next;
		}else{
			ListNode<E> pointNext = dummy.next;
			dummy.next = new ListNode<E>(obj, pointNext, dummy.prev);
			dummy.prev.next = dummy.next;
			dummy.next.next.prev = dummy.next;
		}
		size++;
	}

	//set the dummy node to an inputed object, return null
	public void start(E obj){
		int count = 0;
		while(count != size){
			if(dummy.next.info.equals(obj)){		
				return;
			}
			dummy = dummy.next;
			count++;
		}
	}

	//move the dummy node over one, return null
	public void moveDummy(){
		dummy = dummy.next;
	}

	//inputs nothing, if the size is 0 or list is empty, return true, else return false
	public boolean isEmpty(){
		if(size == 0){
			return true;
		}
		return false;
	}

	//inputs an object, if the object is at the first index or the last index, process changes since one pointer must 
	//be null and must not point back to the dummy node, uses a while loop to loop through the function, if one of 
	//them equals the inputed object, it is removed, size is decreased and the object i returned, if the list doesn't 
	//contain the object, return null
	public E remove(E obj){
		if(isEmpty()){
			return null;
		}else{
			if(size == 1 && contains(obj)){
				dummy.next = null;
				dummy.prev = null;
				size--;
				return obj;
			}else if(dummy.next.info == obj){
				dummy.next = dummy.next.next;
				dummy.next.prev.next = null;
				dummy.next.prev = null;
				size--;
				return obj;
			}else if(dummy.prev.info == obj){
				dummy.prev = dummy.prev.prev;
				dummy.prev.next.prev = null;
				dummy.prev.next = null;
				size--;
				return obj;
			}else{
				ListNode<E> curr = dummy.next;
				int count = 0;
				while(count != size){
					if(curr.info.equals(obj)){
						curr.prev.next = curr.next;
						curr.next.prev = curr.prev;
						curr.next = null;
						curr.prev = null;
						size--;
						return obj;
					}
					curr = curr.next;
					count++;
				}
			}
		}
		return null;
	}

	//inputs nothing, returns a string of every value, separated by commas and surrounded by brackets
	public String toString(){
		String list = "[";
		ListNode<E> curr = dummy.next;
		ListNode<E> prev = dummy;
		int count = 0;
		while(count != size){
			if(count != size-1){
				list +=  curr.info + ", ";
			}else{
				list += curr.info;
			}
			prev = curr;
			curr = curr.next;
			count++;
		}
		list += "]";
		return list;
	}

	//inputs an object, goes through the list using a while loop and returns true if the inputed object equals any
	//object in the list
	public boolean contains(E obj){	
		ListNode<E> curr = dummy.next;
		int count = 0;
		while(count != size){
			if(curr.info.equals(obj)){
				return true;
			}
			curr = curr.next;
			count++;
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
		ListNode<E> curr = dummy.next;
		int count = 0;
		if(contains(obj)){
			while(count != size){
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
		ListNode<E> curr = dummy.next;
		int count = 0;
		if(pos != -1 && pos <= size){
			while(count != size){
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
		ListNode<E> curr = dummy.next;
		if(size == 1){
			dummy.next = null;
			dummy.prev = null;
			size--;
			return hold;
		}else if(pos == 0){
			pop();
		}else if(pos == size){
			popRear();
		}else{
			while(count != size){
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
			ListNode<E> curr = dummy.next;
			for(int i = 0; i < pos; i++){
				if(i == pos-1){
					curr.next = new ListNode<E>(obj, curr.next, curr);
					//curr.next.prev = curr;
					curr.next.next.prev = curr.next;
					size++;
				}
				curr = curr.next;
			}
		}else if(pos == size){
			ListNode<E> pointPrev = dummy.prev;
			dummy.prev = new ListNode<E>(obj, dummy.next, pointPrev);
			pointPrev.next = dummy.prev;
			dummy.next.prev = dummy.prev;
			size++;
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