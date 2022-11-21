package ru.yandex.practicum.tasktracker.service;

public class Node<Task> {
    public Task data; // Данные внутри элемента.
    public Node<Task> next;
    public Node<Task> prev; // Ссылка на предыдущий узел.

    public Node(Node<Task> prev, Task data, Node<Task> next) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}