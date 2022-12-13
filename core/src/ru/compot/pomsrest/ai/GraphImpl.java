package ru.compot.pomsrest.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

// граф-таблица всех соединений
public class GraphImpl implements IndexedGraph<GraphNode> {

    private final GraphNode[] nodes; // точки
    private final Rectangle[] obstacles; // препятствия
    private GraphNode start, end; // точка начала и конца пути, который нужно найти данным алгоритмом

    public GraphImpl(float corner, float playerWidth, Rectangle[] obstacles) {
        GraphNode[] nodes = new GraphNode[obstacles.length * 4]; //у каждого преп 4 вершины
        for (int i = 0; i < obstacles.length; i++) { // получаем все прямоугольные препятствия и массив точек заполняем вершинами данных прямоугольников. вершины немного смещены на corner пикселей. все левые вершины смещены на playerWidth пикселей, чтобы не казалось, что герой летает над препятствиями
            Rectangle rect = obstacles[i];
            rect.x -= playerWidth;
            rect.width += playerWidth;
            nodes[4 * i] = new GraphNode(rect.x - corner, rect.y - corner);
            nodes[4 * i + 1] = new GraphNode(rect.x - corner, rect.y + rect.height + corner);
            nodes[4 * i + 2] = new GraphNode(rect.x + rect.width + corner, rect.y + rect.height + corner);
            nodes[4 * i + 3] = new GraphNode(rect.x + rect.width + corner, rect.y - corner);
        }
        for (GraphNode node : nodes)
            checkConnections(node, nodes, obstacles); // процесс поиска возможных соединений для точек
        this.nodes = nodes;
        this.obstacles = obstacles;
    }

    /**
     * метод поиска всевозможных соединений точек для одной точки. между этими соединениями не должно быть препятствий
     * @param node необходимая точка
     * @param nodes остальные точки
     * @param obstacles препятствия
     * @return точку первый аргумент
     */
    private static GraphNode checkConnections(GraphNode node, GraphNode[] nodes, Rectangle[] obstacles) {
        label:
        for (GraphNode n2 : nodes) {
            for (Rectangle rect : obstacles) {
                if (Intersector.intersectSegmentRectangle(node.getX(), node.getY(), n2.getX(), n2.getY(), rect))
                    continue label;
            }
            node.connect(n2);
            n2.connect(node);
        }
        return node;
    }

    /**
     * метод проверки прямого соединения между точкой начала и конца. если между таким соединением нет препятствий, точки соединяются
     * @param obstacles препятствия
     * @param start точка начало
     * @param end точка конец
     */
    private static void checkDirectConnection(Rectangle[] obstacles, GraphNode start, GraphNode end) {
        for (Rectangle rect : obstacles) { // проверяет каждую прямоугольное препятствие
            if (Intersector.intersectSegmentRectangle(start.getX(), start.getY(), end.getX(), end.getY(), rect)) // вернет true если линия пересекает переданный прямоугольник
                return;
        }
        start.connect(end);
        end.connect(start);
    }

    /**
     * возвращает позиуию точки в массиве всех точек
     * @param node the node whose index will be returned
     * @return позицию точки
     */
    @Override
    public int getIndex(GraphNode node) {
        if (node == start) return 0;
        if (node == end) return nodes.length + 1;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == node) return i + 1;
        }
        throw new IllegalArgumentException("node not found in this graph");
    }

    /**
     * @return общее количество точек в графе
     */
    @Override
    public int getNodeCount() {
        return nodes.length + 2; // размер массива точек вершин препятствий + точка начало + точка конец
    }

    /**
     * возвращает массив всех существующих соединений для определенной точки
     * @param fromNode the node whose outgoing connections will be returned
     * @return массив соединений
     */
    @Override
    public Array<Connection<GraphNode>> getConnections(GraphNode fromNode) {
        return fromNode.getConnections();
    }

    /**
     * @return точку начала поиска пути
     */
    public GraphNode getStart() {
        return start;
    }

    /**
     * @return точку конца поиска пути
     */
    public GraphNode getEnd() {
        return end;
    }

    /**
     * устанавливает начало и конец для поиска пути между даннымы точками
     * @param startX х начала
     * @param startY у начала
     * @param endX х конца
     * @param endY у конца
     */
    public void setPathNodes(float startX, float startY, float endX, float endY) {
        this.start = checkConnections(new GraphNode(startX, startY), nodes, obstacles);
        this.end = checkConnections(new GraphNode(endX, endY), nodes, obstacles);
        checkDirectConnection(obstacles, start, end);
    }

    /**
     * удаляет все существующие соеднениния точек между начальными и конечными точками, между которыми требовалось найти путь
     */
    public void removePathNodes() {
        if (start != null && end != null) {
            for (GraphNode node : nodes) {
                node.removeConnection(start);
                node.removeConnection(end);
            }
        }
    }
}
