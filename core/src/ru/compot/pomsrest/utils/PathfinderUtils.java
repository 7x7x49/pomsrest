package ru.compot.pomsrest.utils;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import ru.compot.pomsrest.ai.GraphImpl;
import ru.compot.pomsrest.ai.GraphNode;
import ru.compot.pomsrest.ai.GraphPathImpl;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.utils.constants.OtherConstants;

import java.util.Iterator;

public class PathfinderUtils { //поиск наикротчайшего пути

    private static final Heuristic<GraphNode> HEURISTIC =
            (node, endNode) -> Vector2.dst(node.getX(), node.getY(), endNode.getX(), endNode.getY());

    private PathfinderUtils() { //находит путь
    }

    public static Iterator<GraphNode> findPath(float x1, float y1, float x2, float y2, GraphImpl graph) {
        IndexedAStarPathFinder<GraphNode> pathfinder = new IndexedAStarPathFinder<>(graph);
        GraphPathImpl path = new GraphPathImpl();
        graph.setPathNodes(x1, y1, x2, y2);
        try {
            if (pathfinder.searchNodePath(graph.getStart(), graph.getEnd(), HEURISTIC, path))
                return path.iterator();
        } catch (Exception ignored) {
        } finally {
            graph.removePathNodes();
        }
        return null;
    }

    public static void animatePath(Iterator<GraphNode> nodes, TransformComponent transform, TransformAnimationComponent transformAnimation, PathFinishConsumer onPathFinish, Runnable onFinish) {
        if (nodes == null || !nodes.hasNext()) {
            onFinish.run();
            return; //анимирует
        }
        GraphNode node = nodes.next();
        if (onPathFinish != null) onPathFinish.accept(node);
        transformAnimation.animate(
                TransformAnimationType.POSITION,
                OtherConstants.PLAYER_SPEED,
                transform.x,
                transform.y,
                node.getX(),
                node.getY(),
                () -> {
                    if (nodes.hasNext()) {
                        animatePath(nodes, transform, transformAnimation, onPathFinish, onFinish);
                        return;
                    }
                    if (onFinish != null) onFinish.run();
                    transformAnimation.stopAnimations(TransformAnimationType.POSITION);
                }
        );
    }

    @FunctionalInterface
    public interface PathFinishConsumer {
        void accept(GraphNode node);
    }
}
