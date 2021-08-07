# Friends

**This was the fourth and final project assigned in Rutgers 01:198:112 (Data Structures).

In this assignment, I implemented several useful algorithms that apply to friendship graphs (similar to those used by Facebook).

A friendship graph is an undirected graph without any weights on the edges. It is a simple graph because there are no self loops, or multiple edges.

The vertices in the graphs for this assignment represent two kinds of people: students and non-students. Each vertex stores the name of the person. If the person is a student, the name of the school is also stored. Note that the graph may not be connected, as seen in this example in which there are two "islands" or cliques that are not connected to each other by any edge. 

I implemented the following methods in the Friends class:
- shortestChain: Finds the shortest chain of people from p1 to p2. The chain is returned as a sequence of names starting with p1, and ending with p2. Each pair (n1,n2) of consecutive names in the returned chain is an edge in the graph.
- cliques: Finds all cliques (or islands) of students in a given school.
- connectors: Finds and returns all connectors in a graph.
