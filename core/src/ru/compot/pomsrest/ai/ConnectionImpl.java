package ru.compot.pomsrest.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;

// класс, представляющий соединение между точками при поиске наикратчайшего пути
public class ConnectionImpl implements Connection<GraphNode> {

    private final GraphNode start, end; // точка начала и конца соеднения
    private final float cost; // растояние между точками (стоимость)

    public ConnectionImpl(GraphNode start, GraphNode end) {
        this.start = start;
        this.end = end;
        this.cost = Vector2.dst(start.getX(), start.getY(), end.getX(), end.getY()); // растояние находим по теореме пифагора
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public GraphNode getFromNode() {
        return start;
    }

    @Override
    public GraphNode getToNode() {
        return end;
    }
}
