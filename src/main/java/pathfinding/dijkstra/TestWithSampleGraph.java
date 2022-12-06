package pathfinding.dijkstra;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.List;

/**
 * Tests the implementation of Dijkstra's algorithm using the following sample graph:
 *
 * <pre>
 *       1
 *      / \
 *    2/   \3
 *    /     \
 *   / 3   1 \    5
 *  3-----4---5-------2
 *  |      \  |       |
 *  |      4\ |6      |
 *  |        \|       |
 * 2|         6       |15
 *  |         |       |
 *  |         |7      |
 *  |         |       |
 *  7---------8-------9
 *       4        3
 * </pre>
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
@SuppressWarnings({"squid:S106", "PMD.SystemPrintln"}) // System.out is OK in this test program
public class TestWithSampleGraph {
  public static void main(String[] args) {
    ValueGraph<String, Integer> graph = createSampleGraph();

    System.out.println("graph = " + graph);

    findAndPrintShortestPath(graph, "D", "10");
    findAndPrintShortestPath(graph, "1", "F");
    findAndPrintShortestPath(graph, "E", "10");
    findAndPrintShortestPath(graph, "2", "10");
    findAndPrintShortestPath(graph, "2", "I");
    findAndPrintShortestPath(graph, "E", "10");
  }

  private static void findAndPrintShortestPath(
      ValueGraph<String, Integer> graph, String source, String target) {
    List<String> shortestPath = DijkstraWithPriorityQueue.findShortestPath(graph, source, target);
    System.out.printf("shortestPath from %s to %s = %s%n", source, target, shortestPath);
  }

  private static ValueGraph<String, Integer> createSampleGraph() {
    MutableValueGraph<String, Integer> graph = ValueGraphBuilder.undirected().build();
    graph.putEdgeValue("1", "C", 2);
    graph.putEdgeValue("1", "E", 3);
    graph.putEdgeValue("2", "E", 5);
    graph.putEdgeValue("2", "I", 15);
    graph.putEdgeValue("C", "D", 3);
    graph.putEdgeValue("C", "G", 2);
    graph.putEdgeValue("D", "E", 1);
    graph.putEdgeValue("D", "F", 4);
    graph.putEdgeValue("E", "F", 6);
    graph.putEdgeValue("F", "10", 7);
    graph.putEdgeValue("G", "10", 4);
    graph.putEdgeValue("10", "I", 3);
    return graph;
  }
}
