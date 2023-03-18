/*
	Apolline Weinstein
	Mr. Cruté
	AT1
	Date created: tues, jan 10, 2023
	Date last edited: wed, jan 18, 2023
	Description: UnorderedListDuplicate class that has global variables head(ListNode E) and size(integer) and has methods size (takes in nothing), 
	front(takes nothing), add(takes in an object), isEmpty(takes in nothing), remove(takes in an object), toString(takes in nothing), 
	contains(takes in an object), append(takes in an object), indexOf(takes in an object), valueAt(takes in an integer), pop(takes in nothing)
	insert(takes in an integer and an object), indicesOf(takes in an object), removeAll(takes in an object), and private class ListNode
*/

import java.util.*;
public class UnorderedListDuplicate<E extends Comparable<? super E>> implements MyList<E>{

	private ListNode<E> head;
	private int size;

	public UnorderedListDuplicate(){
		head = new ListNode<E>();
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

	//inputs an object, add the object to the front of the list if it does not already exist in the list
	public void add(E obj){ 
		if(isEmpty()){
			head.next = new ListNode<E>(obj, null);
		}else{
			ListNode<E> prev = head.next;
			head.next = new ListNode<E>(obj, prev);
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

	//inputs an object, uses a while loop to loop through the function, if one of them equals the inputed object, 
	//it is removed, size is decreased and the object i returned, if the list doesn't contain the object, return null
	public E remove(E obj){
		if(isEmpty()){
			return null;
		}else{
			ListNode<E> curr = head.next;
			ListNode<E> prev = head;
			while(curr != null){
				if(curr.info.equals(obj)){
					prev.next = curr.next;
					curr.next = null;
					size--;
					return obj;
				}
				prev = curr;
				curr = curr.next;
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

	//inputs an object, goes through the list using a while loop and returns true if the inputed object equals any
	//object in the list
	public boolean contains(E obj){	
		ListNode<E> curr = head.next;
		ListNode<E> prev = head;
		while(curr != null){
			if(curr.info.equals(obj)){
				return true;
			}
			prev = curr;
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
		ListNode<E> prev = head;
		int count = 0;
		if(contains(obj)){
			while(curr != null){
				if(curr.info.equals(obj)){
					return count;
				}
				prev = curr;
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
		ListNode<E> prev = head;
		int count = 0;
		if(pos != -1 && pos <= size){
			 while(curr != null){
				if(count == pos){
					return curr.info;
				}
				prev = curr;
				curr = curr.next;
				count++;
			}
		}
		return null;
	}

	//inputs nothing, uses the remove function to remove the first value (found using valueAt) of the function, returns object removed
	public E pop(){
		return remove(valueAt(0));
	}

	//inputs an integer position and an object, if the list doesn't already contain the object and the position is
	//not greater than the size, loop through the list and once the value before where the object should be placed 
	//is reached, point its pointer to the new inserted list node which pointer will point to the node its previous
	//node was pointing at, returns nothing
	public void insert(int pos, E obj){
		if(pos == 0){
			add(obj);
		}else if(pos <= size){
			ListNode<E> curr = head.next;
			for(int i = 0; i < pos; i++){
				if(i == pos-1){
					curr.next = new ListNode<E>(obj, curr.next);
					size++;
				}
				curr = curr.next;
			}

		}
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
	//returns nothing
	public void removeAll(E obj){
		ListNode<E> curr = head.next;
		ListNode<E> prev = head;
		ArrayList<Integer> indices = indicesOf(obj);
		for(int i = 0; i < indices.size(); i++){
			remove(valueAt(indices.get(i)-i));
		}
	}	

	//class list node, global variables info and next, two constructors
	private static class ListNode<E>{
		private E info; 
		ListNode<E> next; //pointer

		//contructor, takes in nothing
		public ListNode(){
			info = null;
			next = null;
		}
		//constructor, takes in an object and a ListNode
		public ListNode(E obj, ListNode<E> nextNode){
			info = obj;
			next = nextNode;
		}
	}
}