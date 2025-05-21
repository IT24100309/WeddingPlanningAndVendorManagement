package com.weddingplanner.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A custom implementation of a LinkedList 
 * @param <T> The type of elements stored in the list
 */
public class CustomLinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Node class for the linked list
     * @param <T> Type of data stored in the node
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Constructs an empty linked list
     */
    public CustomLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds an element to the end of the list
     * @param data The element to add
     */
    public void add(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    /**
     * Gets the element at the specified position
     * @param index The index of the element to return
     * @return The element at the specified position
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    /**
     * Removes the element at the specified position
     * @param index The index of the element to remove
     * @return The removed element
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        T removedData;

        if (index == 0) {
            // Removing the head
            removedData = head.data;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            // Find the predecessor of the node to be removed
            Node<T> pred = head;
            for (int i = 0; i < index - 1; i++) {
                pred = pred.next;
            }

            // Remove the node
            Node<T> removedNode = pred.next;
            removedData = removedNode.data;
            pred.next = removedNode.next;

            // If removing the tail, update the tail reference
            if (removedNode == tail) {
                tail = pred;
            }
        }

        size--;
        return removedData;
    }

    /**
     * Returns the number of elements in the list
     * @return The number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns if the list is empty
     * @return true if the list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears all elements from the list
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns an iterator over the elements in this list
     * @return an Iterator over the elements
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    /**
     * Returns an Iterable for the elements in this list
     * @return an Iterable for the elements
     */
    public Iterable<T> elements() {
        return this::iterator;
    }
} 