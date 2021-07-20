
// A union by rank and path compression
// based program to detect cycle in a graph
// class to represent edge



class UnionFind
{
    Subset[] subsets;
    public Subset[] getSubsets() {
        return subsets;
    }

    UnionFind(int V)
    {
        subsets = new Subset[V];
        for (int v = 0; v < V; v++) {

            subsets[v] = new Subset();
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
    }


    // A utility function to find
    // set of an element i (uses
    // path compression technique)
    int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i)
            subsets[i].parent
                    = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    // A function that does union
    // of two sets of x and y
    // (uses union by rank)
    void Union(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[yroot].rank < subsets[xroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[xroot].parent = yroot;
            subsets[yroot].rank++;
        }
    }
}
