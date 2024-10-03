package com.chandru.basic_crud_app.ds;

import com.chandru.basic_crud_app.interfaces.Interleavable;

import java.util.*;

public class IntervalAVLTree<T> {

    private Node<T> root;
    private Comparator<T> comparator;
    private boolean insertFlag;
    private boolean deleteFlag;

    private static class Node<T> {
        Node<T> left;
        Node<T> right;
        int height;
        T value;

        @Override
        public String toString() {
            return "Node{" +
                    "height=" + height +
                    ", value=" + value +
                    '}';
        }

        public Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    public IntervalAVLTree() {
    }

    public IntervalAVLTree(T initialNode) {
        this(initialNode, null);
    }

    public IntervalAVLTree(T initialNode, Comparator<T> comparator) {
        isInterleaveIns(initialNode);
        this.comparator = comparator;
        this.root = createNode(initialNode);
    }

    // ADDING WORKS GREATLY -- (02-10-2024)(12:30AM)
    public boolean add(T value) {
        if (this.root == null) {
            this.root = createNode(value);
            return true;
        }
        if (!preAddCheck(comparator, this.root, value))
            return false;

        this.root = add(this.root, value);
//        if (!insertFlag)
//            return false;
//
//        insertFlag = false;
        return true;
    }

    private Node<T> add(Node<T> root, T value) {

        if (root == null) {
            insertFlag = true;
            return createNode(value);
        }
        int comparedValue = compareInterleave(comparator, value, root.value);

        // move left
        if (comparedValue < 0)
            root.left = add(root.left, value);
            // move right
        else if (comparedValue > 0)
            root.right = add(root.right, value);
            // it is not a valid interval to insert
        else
            return root;

        // update the height of the current root
        root.height = 1 + getMaxHeight(root);
        int balanceLevel = balanceLevel(root);

        // We concern only if the insertion is happened

        // left
        if (balanceLevel > 1) {
            int interLeave = compareInterleave(comparator, value, root.left.value);
            // left
            if (interLeave < 0)
                return rightRotate(root);
            // right
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // right
        else if (balanceLevel < -1) {
            int interLeave = compareInterleave(comparator, value, root.right.value);
            // right
            if (interLeave > 0)
                return leftRotate(root);
            // left
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        // left left
//            if (balanceLevel > 1 && compareInterleave(value, root.left.value) < 0) {
////            System.out.println(" 1 -- > " + value + " -- " + root.value); --> DEBUG
//                return rightRotate(root);
//            }
//
//            // right right
//            if (balanceLevel < -1 && compareInterleave(value, root.right.value) > 0) {
////            System.out.println(" 2 --> " + value + " -- " + root.value); --> DEBUG
//                return leftRotate(root);
//            }
//
//            // left right
//            if (balanceLevel > 1 && compareInterleave(value, root.left.value) > 0) {
////            System.out.println(" 3 --> " + value + " -- " + root.value); --> DEBUG
//                root.left = leftRotate(root.left);
//                return rightRotate(root);
//            }
//
//            // right left
//            if (balanceLevel < -1 && compareInterleave(value, root.right.value) < 0) {
////            System.out.println(" 4 --> " + value + " -- " + root.value); --> DEBUG
//                root.right = rightRotate(root.right);
//                return leftRotate(root);
//            }

        return root;
    }

    public boolean contains(T searchObject) {
        Objects.requireNonNull(searchObject, "Search object cannot be empty");
        return contains(this.root, searchObject);
    }

    private boolean contains(Node<T> root, T searchObject) {
        Interleavable interleavable = (Interleavable) searchObject;
        while (root != null) {
            // USE OF "isEqual"
            // Because we need to find a way to equalize the two object
            // We know all the object inside the IntervalAVLTree is the child of Interleavable
            // So I use the interleavable to compare the two objects to give the positive result to caller
            if (isEqual(interleavable, root.value))
                return true;
            int comparedValue = compareInterleave(comparator, searchObject, root.value);
            // go to left
            if (comparedValue < 0)
                root = root.left;
                // go right
            else if (comparedValue > 0)
                root = root.right;
            else
                break;
        }

        return false;
    }

    public boolean remove(T obj) {
        if (this.root == null)
            return false;
        // pre-check of the deletion
        if (!contains(obj))
            return false;

        this.root = remove(this.root, obj);
        return true;
    }

    private Node<T> remove(Node<T> root, T keyObj) {

        if (root == null)
            return null;

        int compareValue = compareInterleave(comparator, keyObj, root.value);

        // go left
        if (compareValue < 0)
            root.left = remove(root.left, keyObj);
            // go right
        else if (compareValue > 0)
            root.right = remove(root.right, keyObj);
            // the compareValue = 0 cause in two scenario
            // 1 -> the "keyObj" is "overlap" the current root
            // 2 -> the "keyObj" "match" the current root
        else {
            // we found the node to be deleted
            if (isEqual((Interleavable) keyObj, root.value)) {

                if (root.left == null)
                    return root.right;

                if (root.right == null)
                    return root.left;

                Node<T> tempNode = root.right;
                // find the left most to replace the node that we want to delete
                while (tempNode.left != null)
                    tempNode = tempNode.left;

                root.value = tempNode.value;
                // from root.right -> we need to delete the leaf node that we replaced with the current root
                root.right = remove(root.right, tempNode.value);
            }
        }

        // from this point we need do update the height the current root
        root.height = 1 + getMaxHeight(root);
        // after the update of the height we need to check the balance of the current root
        int balanceLevel = balanceLevel(root);

        // left
        if (balanceLevel > 1) {
            int leftBalanceLevel = balanceLevel(root.left);
            // left
            if (leftBalanceLevel >= 0)
                return rightRotate(root);
            // right
            root.left = leftRotate(root.left);
            return rightRotate(root.right);
        }
        // right
        else if (balanceLevel < -1) {
            int rightBalanceLevel = balanceLevel(root.right);
            // right
            if (rightBalanceLevel <= 0)
                return leftRotate(root);
            // left
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
        // ---
//        if (root == null)
//            return null;
//
//        // go to left
//        if (compareInterleave(comparator, keyObj, root.value) < 0)
//            root.left = remove (root.left, keyObj);
//
//        // go to right
//        else if (compareInterleave(comparator, keyObj, root.value) > 0)
//            root.right = remove (root.right, keyObj);
//
//        // we found the node to be deleted
//        else {
//            deleteFlag = true;
//            if (root.left == null)
//                return root.right;
//
//            if (root.right == null)
//                return root.left;
//
//            Node<T> current = root.right;
//
//            while (current.left != null)
//                current = current.left;
//
//            root.value = current.value;
//            root.right = remove(root.right, current.value);
//        }
//        // Till this point it is a normal BST deletion
//        // NULL NEEDED, BUT I THINK IT DOESN'T
//
//        root.height = 1 + getMaxHeight(root);
//
//        int balance = balanceLevel(root);
//
//        if (balance > 1 && balanceLevel(root.left) >= 0)
//            return rightRotate(root);
//
//        if (balance < -1 && balanceLevel(root.right) <= 0)
//            return leftRotate(root);
//
//        if (balance > 1 && balanceLevel(root.left) < 0) {
//            root.left = leftRotate(root.left);
//            return rightRotate(root);
//        }
//
//        if (balance < -1 && balanceLevel(root.right) > 0) {
//            root.right = rightRotate(root.right);
//            return leftRotate(root);
//        }
//
//        return root;
    }

    // --- UTILITIES ---
    // interleave node check
    private static <T> void isInterleaveIns(T node) {
        try {
            Interleavable dummy = (Interleavable) node;
        } catch (ClassCastException exception) {
            throw new ClassCastException("it is not a interleave node");
        }
    }

    private static <T> Node<T> createNode(T value) {
        return new Node<>(value);
    }

    private static <T> int compareInterleave(Comparator<T> comparator, T toBeInsert, T current) {
        if (comparator != null)
            return comparator.compare(toBeInsert, current);

        @SuppressWarnings("unchecked")
        Comparable<T> comparable = (Comparable<T>) toBeInsert;
        return comparable.compareTo(current);
    }

    // AVL-UTILS
    private static <T> int height(Node<T> node) {
        return node == null ? 0 : node.height;
    }

    private static <T> int balanceLevel(Node<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private static <T> int getMaxHeight(Node<T> node) {
        return Math.max(height(node.left), height(node.right));
    }

    private static <T> Node<T> rightRotate(Node<T> node) {
        Node<T> tempRoot = node.left;
        Node<T> tempRootRight = tempRoot.right;

        tempRoot.right = node;
        node.left = tempRootRight;

        node.height = 1 + getMaxHeight(node);
        tempRoot.height = 1 + getMaxHeight(tempRoot);

        return tempRoot;
    }

    private static <T> Node<T> leftRotate(Node<T> node) {
        Node<T> tempRoot = node.right;
        Node<T> tempRootLeft = tempRoot.left;

        tempRoot.left = node;
        node.right = tempRootLeft;

        node.height = 1 + getMaxHeight(node);
        tempRoot.height = 1 + getMaxHeight(tempRoot);

        return tempRoot;
    }

    private static <T> boolean isEqual(Interleavable target, T current) {
        Interleavable interleavable = (Interleavable) current;
        return target.start() == interleavable.start() && target.end() == interleavable.end();
    }

    // Used to check if there is room for inserting the element
    // because we want pre-check the element has a space to insert into the tree
    private static <T> boolean preAddCheck(Comparator<T> comparator, Node<T> root, T object) {
        // avoid the original root gets modified
        Node<T> node = root;
        while (node != null) {
            int compareValue = compareInterleave(comparator, object, node.value);
            if (compareValue < 0)
                node = node.left;
            else if (compareValue > 0)
                node = node.right;
            else
                return false;
        }

        return true;
    }

    // --- TEST ---
    public void levelOrder() {

        for (List<Node<T>> ele : levelOrder(this.root))
            System.out.println(ele);

        System.out.println();
    }

    private List<List<Node<T>>> levelOrder(Node<T> root) {

        List<List<Node<T>>> ds = new ArrayList<>();

        if (root == null)
            return ds;

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {

            int size = queue.size();
            LinkedList<Node<T>> list = new LinkedList<>();

            while (size-- > 0) {
                Node<T> temp = queue.poll();
                list.addLast(temp);

                if (temp.left != null)
                    queue.add(temp.left);

                if (temp.right != null)
                    queue.add(temp.right);
            }

            ds.add(list);
        }

        return ds;
    }

    public void inorder() {
        inorder(this.root);
        System.out.println();
    }

    private void inorder(Node<T> root) {
        if (root == null)
            return;

        inorder(root.left);
        System.out.println(root.value);
        inorder(root.right);
    }
}
