package ru.compot.pomsrest.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

// граф точка
public class GraphNode {

    private final float x, y; // координаты
    private final Array<Connection<GraphNode>> connections = new Array<>(); // соединения с другими точками

    public GraphNode(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return х точки
     */
    public float getX() {
        return x;
    }

    /**
     * @return у точки
     */
    public float getY() {
        return y;
    }

    /**
     * @return массив соединений
     */
    public Array<Connection<GraphNode>> getConnections() {
        return connections;
    }

    /**
     * соеднияет текущую точку с другой точкой из графа
     * @param node точка, с которой требуется соединить текущую
     */
    public void connect(GraphNode node) {
        connections.add(new ConnectionImpl(this, node));
    }

    /**
     * удаляет соединение между текущей точкой и другой точкой из графа
     * @param node точка, с которой требуется разорвать соединение
     */
    public void removeConnection(GraphNode node) {
        for (int i = 0; i < connections.size; i++) {
            Connection<GraphNode> connection = connections.get(i);
            if (connection.getToNode() == node) connections.removeValue(connection, true);
        }
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
