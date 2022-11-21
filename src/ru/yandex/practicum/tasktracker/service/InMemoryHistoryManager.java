package ru.yandex.practicum.tasktracker.service;

import ru.yandex.practicum.tasktracker.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
    private final Map<Integer, Node<Task>> viewedTasks = new HashMap<>();
    private Node<Task> head; // Указатель на первый элемент списка.
    private Node<Task> tail; // Указатель на последний элемент списка.

    private Node<Task> linkLast(Task task) { // Создаем новый узел и добавляем задачу в tail.
        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;

        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }

        return newNode;
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();

        Node<Task> node = head;
        while (node != null) {
            tasks.add(node.data);
            node = node.next;
        }

        return tasks;
    }

    private void removeNode(Node<Task> node) {  // "Вырезаем" узел.
        final Task task = node.data; // Данные внутри элемента.
        final Node<Task> nextNode = node.next; // Ссылка на следующий узел.
        final Node<Task> prevNode = node.prev; // Ссылка на предыдущий узел.

        /* Если узел первого элемента в списке или единственного элемента в списке -
        ссылка на предыдущий узел будет null.
         */
        if (prevNode == null) {
            head = nextNode; // присваиваем ссылку следующего элемента удаляемого узла head.
        } else { // Если узел не первого элемента списка и имеет ссылки next и prev.
            prevNode.next = nextNode;
            node.prev = null; // Обнуляем ссылку у удаляемого узла.
        }

        /* Если узел последнего элемента в списке или единственного элемента в списке -
        ссылка на следующий узел будет null.
        */
        if (nextNode == null) {
            tail = prevNode;
        } else { // Если узел не первого элемента списка и имеет ссылки next и prev.
            nextNode.prev = prevNode;
            node.next = null;
        }

        node.data = null;
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (viewedTasks.containsKey(task.getUin())) {
                remove(task.getUin());
            }
            Node<Task> newNode = linkLast(task);
            viewedTasks.put(task.getUin(), newNode);
        }
    }

    @Override
    public void remove(int id) { // удаление задач из списка просмотренных задач.
        if (viewedTasks.containsKey(id)) {
            Node<Task> node = viewedTasks.get(id); // Находим узел по id Task.
            removeNode(node); // Вырезаем узел из списка.
            viewedTasks.remove(id); // удаляем запись из мапы.
        }
    }

    @Override
    public List<Task> getHistory() { //надо изменить
        return getTasks();
    }
}