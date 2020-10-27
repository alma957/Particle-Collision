package utils;

import java.util.ArrayList;

public class MinPriorityQueue<T extends Comparable<T>> {
    private ArrayList<T> queue;

    /**
     * Creates an empty queue.
     */
    public MinPriorityQueue() {
        this.queue = new ArrayList<T>();
        queue.add(null);
    }

    /**
     * Returns the number of elements currently in the queue.
     */
    public int size() {
        return this.queue.size() - 1;

    }

    /**
     * Adds elem to the queue.
     */
    public void add(T elem) {
        this.queue.add(elem);

        if (this.size() <= 1)
            return;

        int elem_index = this.size();
        int parent_index = elem_index / 2;
        int compare = queue.get(elem_index).compareTo(queue.get(parent_index));

        while (elem_index > 1 && compare < 0) {

            T parent = queue.get(parent_index);
            queue.set(parent_index, queue.get(elem_index));
            queue.set(elem_index, parent);
            elem_index = parent_index;
            parent_index = elem_index / 2;

            if (elem_index > 1) {
                compare = queue.get(elem_index).compareTo(queue.get(parent_index));
            }
        }
    }

    /**
     * Returns -1 if either there are no children to this node or both children are
     * larger than the node. Otherwise,returns the index of the smallest child which
     * is smaller than the node.
     */
    public int compare_to_children(int node_index) {

        T node_elem = queue.get(node_index);
        boolean has_left_child = false;
        boolean has_right_child = false;
        int left_child_index = 2 * node_index;
        int right_child_index = 2 * node_index + 1;
        int smallest_child_index = 0;
        T smallest_child = node_elem; // This value changes below. Initialized to node_elem only so that code would
                                      // compile.

        if (left_child_index <= this.size()) {
            has_left_child = true;
        }
        if (right_child_index <= this.size()) {
            has_right_child = true;
        }

        // case when node has no children
        if (!has_left_child && !has_right_child) {
            return -1;
        }

        // case when node has both children. set the smallest_child accordingly.
        if (has_left_child && has_right_child) {
            T left_child = queue.get(left_child_index);
            T right_child = queue.get(right_child_index);

            if (left_child.compareTo(right_child) >= 0) {
                smallest_child_index = right_child_index;
                smallest_child = right_child;
            } else {
                smallest_child_index = left_child_index;
                smallest_child = left_child;
            }
            // case when node has only left child, thus left child is also the smallest
            // child
        } else if (has_left_child && !has_right_child) {
            T left_child = queue.get(left_child_index);
            smallest_child = left_child;
            smallest_child_index = left_child_index;
            // case when node has only right child, thus right child is also the smallest
            // child
        } else if (has_right_child && !has_left_child) {
            T right_child = queue.get(right_child_index);
            smallest_child = right_child;
            smallest_child_index = right_child_index;
        }

        // if a node is smaller than its smallest child, it is smaller than both its
        // children and thus return -1
        if (node_elem.compareTo(smallest_child) <= 0) {
            return -1;
        }

        return smallest_child_index;

    }

    /**
     * Removes, and returns, the element at the front of the queue.
     */
    public T remove() {
        int cur_root_index = 1;
        T removed_elem = queue.get(cur_root_index); // min element in the queue to be returned

        if (this.size() <= 1) { // case when there is only one element in the queue
            queue.remove(cur_root_index);
            return removed_elem;
        }

        int last_index = this.size();// put the last elem in the queue to the root. delete the last element.
        queue.set(cur_root_index, queue.get(last_index));
        queue.remove(last_index);

        int smallest_child_index = this.compare_to_children(cur_root_index);

        while (smallest_child_index != -1) {

            T cur_root = queue.get(cur_root_index);
            queue.set(cur_root_index, queue.get(smallest_child_index));
            queue.set(smallest_child_index, cur_root);

            cur_root_index = smallest_child_index;
            smallest_child_index = this.compare_to_children(cur_root_index);

        }

        return removed_elem;

    }

    /**
     * Returns true if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {

        return queue.size() < 1;

    }

    public void print_queue() {
        for (int i = 0; i <= this.queue.size(); i++)
            System.out.println(this.queue.get(i));
    }

}
