package stack43210199;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;

    public class HowToImplementGenericTree {

    	class TreeNode<T> {
    		T data;
    		List<TreeNode<T>> children;

    		TreeNode(T data) {
    			this.data = data;
    			children = new ArrayList<TreeNode<T>>();
    		}
    		public  TreeNode<T> findNextNode(T n,Comparator<T> comp) {
        		if (comp.compare(data , n) < 0) {
        			return this;
        		}
        		if (children.size() == 0) {
        			return null;
        		}
        		for (int i = 0; i < children.size(); i++) {
        			TreeNode<T> node= children.get(i).findNextNode(n,comp);
        			if(node!=null)return node;
        		}
        	    return null;
        	}
    	}
    	
    	@org.junit.Test
    	public void testForYourCode() {
    		TreeNode<Integer> root = buildNode(0);
    		TreeNode<Integer> firstChild = buildNode(5);
    		TreeNode<Integer> secondChild = buildNode(4);
    		TreeNode<Integer> thirdChild = buildNode(5);
    		root.children.add(firstChild);
    		root.children.add(secondChild);
    		root.children.add(thirdChild);
    		//Arrg - not as expected
    		Assert.assertEquals(secondChild, findNextLargerNode(root, 0));
    	}

    	@org.junit.Test
    	public void testForModifiedCode() {
    		TreeNode<Integer> root = buildNode(2);
    		TreeNode<Integer> firstChild = buildNode(5);
    		TreeNode<Integer> secondChild = buildNode(4);
    		TreeNode<Integer> thirdChild = buildNode(5);
    		TreeNode<Integer> fourthChild = buildNode(1);
    		root.children.add(firstChild);
    		root.children.add(secondChild);
    		root.children.add(thirdChild);
    		thirdChild.children.add(fourthChild);
    		//find next greater
    		Assert.assertEquals(firstChild, root.findNextNode(2,(a,b)->{return b-a;}));
    		//find next lesser
    		Assert.assertEquals(fourthChild, root.findNextNode(2,(a,b)->{return a-b;}));
    	   	}
    	
    	@org.junit.Test
    	public void testForModifiedCodeComplex() {
    		TreeNode<Integer> root = buildNode(2);
    		TreeNode<Integer> firstChild = buildNode(2);
    		TreeNode<Integer> secondChild = buildNode(4);
    		TreeNode<Integer> thirdChild = buildNode(5);
    		TreeNode<Integer> fourthChild = buildNode(1);
    		TreeNode<Integer> sixthChild = buildNode(8);
    		firstChild.children.add(fourthChild);
    		firstChild.children.add(sixthChild);
    		root.children.add(firstChild);
    		root.children.add(secondChild);
    		root.children.add(thirdChild);
    		//find next greater
    		Assert.assertEquals(sixthChild, root.findNextNode(2,(a,b)->{return b-a;}));
    		//find next lesser
    		Assert.assertEquals(fourthChild, root.findNextNode(2,(a,b)->{return a-b;}));
    	}

    	private TreeNode<Integer> buildNode(int i) {
    		return new TreeNode<Integer>(new Integer(i));
    	}

    	public static TreeNode<Integer> findNextLargerNode(TreeNode<Integer> root, int n) {

    		if (root == null)
    			return root;

    		if (root.children.size() == 0) {
    			
    			if (root.data > n) {
    				return root;
    			}

    			else
    				return null;

    		}

    		TreeNode<Integer> count[] = new TreeNode[root.children.size()];

    		for (int i = 0; i < root.children.size(); i++) {
    			count[i] = findNextLargerNode(root.children.get(i), n);
    		}

    		int nextLarger = Integer.MAX_VALUE;
    		TreeNode<Integer> next = null;

    		for (int i = 0; i < count.length; i++) {
    			if (count[i] != null) {
    				if (count[i].data > n && count[i].data < nextLarger) {
    					nextLarger = count[i].data;
    					next = count[i];
    				}
    			}
    		}

    		if (next != null) {
    			if (root.data > n && root.data < next.data)
    				return root;
    			else
    				return next;

    		} else {
    			if (root.data > n)
    				return root;
    			else
    				return null;
    		}
    	}
    	
    
    }