package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Node;
import java.util.ArrayList;

public class SortNodes {

    public static void sort(IGraph g, ArrayList<INode> nodes) {
        int n = nodes.size();
        
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            
            for (int j = i + 1; j < n; j++) {
                INode nodeA = nodes.get(j);
                INode nodeB = nodes.get(maxIdx);
                
                int degA = g.degree(nodeA);
                int degB = g.degree(nodeB);
                
                if (degA > degB) {
                    maxIdx = j;
                } else if (degA == degB) {
                    int idA = ((Node) nodeA).getId();
                    int idB = ((Node) nodeB).getId();
                    if (idA < idB) {
                        maxIdx = j;
                    }
                }
            }
            
            INode temp = nodes.get(maxIdx);
            nodes.set(maxIdx, nodes.get(i));
            nodes.set(i, temp);
        }
    }
}