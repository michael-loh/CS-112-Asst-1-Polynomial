package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		Node sum = null;
		
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		
		int size = 0;
		
		if(poly1 == null && poly2 == null)
			return null;
		else if(poly1 == null)
			return poly2;
		else if(poly2 == null)
			return poly1;
		
		for(Node t1 = ptr1; t1.next != null; t1 = t1.next){		//checking size
			size ++;
			
		}
		
		int p1d;
		int p2d;
		
		for(int i = 0; i <= size; i++){				//building the linked list
			if(ptr1 == null && ptr2 == null)
				break;
			
			if(ptr1 == null)
				p1d = ptr2.term.degree + 1;
			else
				p1d = ptr1.term.degree;
			
			if(ptr2 == null)
				p2d = ptr1.term.degree + 1;
			else
				p2d = ptr2.term.degree;
			
			
			if(p1d == p2d){
				if(ptr1.term.coeff + ptr2.term.coeff != 0){
					sum = new Node(ptr1.term.coeff + ptr2.term.coeff, ptr1.term.degree, sum);
				}
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
			}
			else if(p1d < p2d){
				sum = new Node(ptr1.term.coeff, ptr1.term.degree, sum);
				ptr1 = ptr1.next;
				i--;
				
			}
			else if(p2d < p1d){
				sum = new Node(ptr2.term.coeff, ptr2.term.degree, sum);
				ptr2 = ptr2.next;
				i--;
			}
			
		}
		
		Node reverseSum = null;							//reversing linked list to correct order
		for(Node n1 = sum; n1 != null; n1 = n1.next){
			reverseSum = new Node(n1.term.coeff, n1.term.degree, reverseSum);
		}
		
		return reverseSum;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node product = null;
		Node productSimplified = null;
		
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		
		if(poly1 == null || poly2 == null)
			return null;
		
		while(ptr1 != null){
			
			ptr2 = poly2;
			while(ptr2 != null){
				product = new Node(ptr1.term.coeff*ptr2.term.coeff, ptr1.term.degree + ptr2.term.degree, product);
				ptr2 = ptr2.next;
			}
			ptr1 = ptr1.next;
		}
		
		Node front = product;
		
		
		while(front != null){
			Node front2 = front.next;
			Node front3 = front;
			float coeff = front.term.coeff;
			while(front2!= null){
				
				if(front.term.degree == front2.term.degree){
					coeff += front2.term.coeff;
					front3.next = front2.next; 
					front2 = front2.next;
				}
				else{
					front2 = front2.next;
					front3 = front3.next;
				}
			}
			productSimplified = new Node(coeff, front.term.degree, productSimplified);
			front = front.next;
		}
		
		boolean notSorted = true;
		
		
		
		while(notSorted){
			for(Node pt = productSimplified; pt != null; pt = pt.next){
				if(pt.next == null){
					notSorted = false;
				}
				else if(pt.term.degree > pt.next.term.degree){
					float coeffTemp = pt.term.coeff;
					int degreeTemp = pt.term.degree;
					pt.term.coeff = pt.next.term.coeff;
					pt.term.degree = pt.next.term.degree;
					pt.next.term.coeff = coeffTemp;
					pt.next.term.degree = degreeTemp;
					break;
				}
			}
			
		}
		
		
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		
		
		return productSimplified;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		float eval = 0;
		if(poly == null)
			return 0;
		
		Node ptr = poly;
		
		while(ptr != null){
			eval += Math.pow(x, ptr.term.degree) * ptr.term.coeff;
			ptr = ptr.next;
		}
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		return eval;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
