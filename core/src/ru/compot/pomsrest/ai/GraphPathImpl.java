package ru.compot.pomsrest.ai;

import com.badlogic.gdx.ai.pfa.GraphPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// результат алгоритма поиска наикратчайшего пути
public class GraphPathImpl implements GraphPath<GraphNode> {

    private final List<GraphNode> nodes = new ArrayList<>(); // лист точек наикратчашего пути

    @Override
    public int getCount() {
        return nodes.size();
    }

    @Override
    public GraphNode get(int index) {
        return nodes.get(index);
    }

    @Override
    public void add(GraphNode node) {
        nodes.add(node);
    }

    @Override
    public void clear() {
        nodes.clear();
    }

    @Override
    public void reverse() {
        Collections.reverse(nodes);
    }

    @Override
    public Iterator<GraphNode> iterator() {
        return nodes.iterator();
    }
}
