package sample;

//  Sukhamrit Singh
//  BinarySearchTrees
/*
This program fully implements the BST class in Listing 25.4.
I designed and wrote a (main) driver program to completely
test every method in the BST class to ensure the class meets
all its requirements. It tests all the methods to output elements
in the different orders and see how the tree overall works.
 */

//  Imports the necessary libraries
import java.util.Collection;

public class BinarySearchTrees<E extends Comparable<E>> implements Tree<E>  {
    protected TreeNode<E> root;
    protected int size = 0;

    public static void main(String[] args) {
        // Create a Binary search tree and add elements
        BinarySearchTrees<String> tree = new BinarySearchTrees<>();
        tree.insert("Sukhamrit");
        tree.insert("Bob");
        tree.insert("Joe");
        tree.insert("Josh");
        tree.insert("Angela");
        tree.insert("Kate");
        tree.insert("John");
        tree.insert("Felix");
        tree.insert("Messi");
        tree.insert("Virgil");
        tree.insert("Andre");
        tree.insert("Luis");

        printTree(tree);

        // Searching for an element within the tree
        System.out.print("\nIS \"Messi\" IN THE TREE? " +
                tree.search("Messi"));

        // Get a path from the root to Luis
        System.out.print("\nPATH FROM ROOT NODE TO \"Luis\" IS: ");
        java.util.ArrayList<BinarySearchTrees.TreeNode<String>> path
                = tree.path("Luis");
        for (int i = 0; path != null && i < path.size(); i++)
            System.out.print(path.get(i).element + " ");

        Integer[] numbers = {2, 4, 3, 1, 8, 5, 6, 7};
        BinarySearchTrees<Integer> intTree =
                new BinarySearchTrees<>(numbers);
        System.out.print("\nIN-ORDER (sorted): ");
        intTree.inorder();
        System.out.print("\n");

        // printing out the deleted items and the tree after this
        System.out.println("\nAfter deleting Messi:");
        tree.delete("Messi");
        printTree(tree);
        System.out.println("\nAfter deleting Sukhamrit:");
        tree.delete("Sukhamrit");
        printTree(tree);
        System.out.println("\nAfter deleting Bob:");
        tree.delete("Bob");
        printTree(tree);

    }

    //  Method to print out the tree
    public static void printTree(BinarySearchTrees tree) {
        // Traverse tree
        System.out.print("IN-ORDER:\t");
        tree.inorder();
        System.out.print("\nPOST-ORDER:\t");
        tree.postorder();
        System.out.print("\nPRE-ORDER:\t");
        tree.preorder();
        System.out.print("\nNUMBER OF NODES: "
                + tree.getSize() + "\n");
    }

    /** Create a default binary tree */
    public BinarySearchTrees() {
    }

    /** Create a binary tree from an array of objects */
    public BinarySearchTrees(E[] objects) {
        for (int i = 0; i < objects.length; i++)
            add(objects[i]);
    }

    @Override /** Returns true if the element is in the tree */
    public boolean search(E e) {
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else // element matches current.element
                return true; // Element is found
        }

        return false;
    }

    @Override /** Insert element e into the binary tree
     * Return true if the element is inserted successfully */
    public boolean insert(E e) {
        if (root == null)
            root = createNewNode(e); // Create a new root
        else {
            // Locate the parent node
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while (current != null)
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                }
                else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                }
                else
                    return false; // Duplicate node not inserted

            // Create the new node and attach it to the parent node
            if (e.compareTo(parent.element) < 0)
                parent.left = createNewNode(e);
            else
                parent.right = createNewNode(e);
        }

        size++;
        return true; // Element inserted successfully
    }

    protected TreeNode<E> createNewNode(E e) {
        return new TreeNode<>(e);
    }

    @Override /** Inorder traversal from the root */
    public void inorder() {
        inorder(root);
    }

    /** Inorder traversal from a subtree */
    protected void inorder(TreeNode<E> root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.element + " ");
        inorder(root.right);
    }

    @Override /** Postorder traversal from the root */
    public void postorder() {
        postorder(root);
    }

    /** Postorder traversal from a subtree */
    protected void postorder(TreeNode<E> root) {
        if (root == null) return;
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.element + " ");
    }

    @Override /** Preorder traversal from the root */
    public void preorder() {
        preorder(root);
    }

    /** Preorder traversal from a subtree */
    protected void preorder(TreeNode<E> root) {
        if (root == null) return;
        System.out.print(root.element + " ");
        preorder(root.left);
        preorder(root.right);
    }

    /** This inner class is static, because it does not access
     any instance members defined in its outer class */
    public static class TreeNode<E> {
        public E element;
        public TreeNode<E> left;
        public TreeNode<E> right;

        public TreeNode(E e) {
            element = e;
        }
    }

    @Override /** Get the number of nodes in the tree */
    public int getSize() {
        return size;
    }

    /** Returns the root of the tree */
    public TreeNode<E> getRoot() {
        return root;
    }

    /** Returns a path from the root leading to the specified element */
    public java.util.ArrayList<TreeNode<E>> path(E e) {
        java.util.ArrayList<TreeNode<E>> list =
                new java.util.ArrayList<>();
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            list.add(current); // Add the node to the list
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else
                break;
        }

        return list; // Return an array list of nodes
    }

    @Override /** Delete an element from the binary tree.
     * Return true if the element is deleted successfully
     * Return false if the element is not in the tree */
    public boolean delete(E e) {
        // Locate the node to be deleted and also locate its parent node
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            }
            else
                break; // Element is in the tree pointed at by current
        }

        if (current == null)
            return false; // Element is not in the tree

        // Case 1: current has no left child
        if (current.left == null) {
            // Connect the parent with the right child of the current node
            if (parent == null) {
                root = current.right;
            }
            else {
                if (e.compareTo(parent.element) < 0)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            }
        }
        else {
            // Case 2: The current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost)
                parentOfRightMost.right = rightMost.left;
            else
                // Special case: parentOfRightMost == current
                parentOfRightMost.left = rightMost.left;
        }

        size--;
        return true; // Element deleted successfully
    }

    @Override /** Obtain an iterator. Use inorder. */
    public java.util.Iterator<E> iterator() {
        return new InorderIterator();
    }

    // Inner class InorderIterator
    private class InorderIterator implements java.util.Iterator<E> {
        // Store the elements in a list
        private java.util.ArrayList<E> list =
                new java.util.ArrayList<>();
        private int current = 0; // Point to the current element in list


        public InorderIterator() {
            inorder(); // Traverse binary tree and store elements in list
        }

        /** Inorder traversal from the root*/
        private void inorder() {
            inorder(root);
        }

        /** Inorder traversal from a subtree */
        private void inorder(TreeNode<E> root) {
            if (root == null)return;
            inorder(root.left);
            list.add(root.element);
            inorder(root.right);
        }

        @Override /** More elements for traversing? */
        public boolean hasNext() {
            if (current < list.size())
                return true;

            return false;
        }

        @Override /** Get the current element and move to the next */
        public E next() {
            return list.get(current++);
        }

        @Override // Remove the element returned by the last next()
        public void remove() {
            if (current == 0) // next() has not been called yet
                throw new IllegalStateException();

            delete(list.get(--current));
            list.clear(); // Clear the list
            inorder(); // Rebuild the list
        }
    }

    @Override /** Remove all elements from the tree */
    public void clear() {
        root = null;
        size = 0;
    }
}

interface Tree<E> extends Collection<E> {
    /** Return true if the element is in the tree */
    public boolean search(E e);

    /** Insert element e into the binary tree
     * Return true if the element is inserted successfully */
    public boolean insert(E e);

    /** Delete the specified element from the tree
     * Return true if the element is deleted successfully */
    public boolean delete(E e);

    /** Get the number of elements in the tree */
    public int getSize();

    /** Inorder traversal from the root*/
    public default void inorder() {
    }

    /** Postorder traversal from the root */
    public default void postorder() {
    }

    /** Preorder traversal from the root */
    public default void preorder() {
    }

    @Override /** Return true if the tree is empty */
    public default boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public default boolean contains(Object e) {
        return search((E)e);
    }

    @Override
    public default boolean add(E e) {
        return insert(e);
    }

    @Override
    public default boolean remove(Object e) {
        return delete((E)e);
    }

    @Override
    public default int size() {
        return getSize();
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
        // Left as an exercise
        return false;
    }

    @Override
    public default boolean addAll(Collection<? extends E> c) {
        // Left as an exercise
        return false;
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
        // Left as an exercise
        return false;
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
        // Left as an exercise
        return false;
    }

    @Override
    public default Object[] toArray() {
        // Left as an exercise
        return null;
    }

    @Override
    public default <T> T[] toArray(T[] array) {
        // Left as an exercise
        return null;
    }
}