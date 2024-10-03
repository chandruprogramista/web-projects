package com.chandru.basic_crud_app.ds;

import static com.chandru.basic_crud_app.applogic.Constants.ifTrueOrElse;
import com.chandru.basic_crud_app.interfaces.Interleavable;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

// supports two functionalities
// 1 -> insert a interval
// 2 -> delete an interval
// additional
// -> balance the tree
public class IntervalTree<T> {

    private Node<T> root;
    private Comparator<T> comparator;

    private static class Node<T> {
        Node<T> left;
        Node<T> right;
        T value;

        public Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    // interleave node check
    private void isInterleaveIns(T node) {
        try {
            Interleavable dummy = (Interleavable) node;
        } catch (ClassCastException exception) {
            throw new ClassCastException("it is not a interleave node");
        }
    }

    public IntervalTree() {
    }

    public IntervalTree(T initialNode) {
        this(initialNode, null);
    }

    public IntervalTree(T initialNode, Comparator<T> comparator) {
        isInterleaveIns(initialNode);
        this.comparator = comparator;
        this.root = createNode(initialNode);
    }

    public boolean insert(T node) {

        if (root == null) {
            isInterleaveIns(node);
            root = createNode(node);
            return true;
        }

        Node<T> tempNode = this.root;
        Node<T> lastNode = null;
        int compareResult = 0;

        while (tempNode != null) {
            compareResult = compareInterleave(tempNode.value, node);
            lastNode = tempNode;

            if (compareResult == 0)
                break;
            tempNode = compareResult > 0 ? tempNode.right : tempNode.left;
        }

        if (tempNode != null)
            return false;

        ifTrueOrElse(
                (Predicate<Integer>) e -> e > 0,
                (Consumer<Node<T>>) e -> e.right = createNode(node),
                e -> e.left = createNode(node)
        ).accept(compareResult, lastNode);

        return true;
    }

    public boolean delete() {

        // If we can find the interval
        return false;
    }

    public void preorder () {
        preorder(this.root);
        System.out.println();
    }

    private void preorder (Node<T> root) {
        if (root == null)
            return;

        System.out.println(root.value);
        preorder(root.left);
        preorder(root.right);
    }

    private static <T> Node<T> createNode(T node) {
        return new Node<>(node);
    }

    private int compareInterleave(T current, T toBeInsert) {
        if (comparator != null)
            return comparator.compare(current, toBeInsert);

        @SuppressWarnings("unchecked")
        Comparable<T> comparable = (Comparable<T>) current;
        return comparable.compareTo(toBeInsert);
    }
}
