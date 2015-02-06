#include <fstream>
#include "SList.h"
#include "Stack.h"
#include <iostream>
#include <vector>

using namespace std;

ifstream input;
ofstream output;

int main()
{
	input.open("infile.dat");
	output.open("outfile.dat");
	if (input.fail()) {
		cout << "Cannot open infile.dat file, please try again." << endl;
		return -1;
	}

	vector<SList<int> > graph;
	vector<SList<int>::Iterator > vecIter;
	int nodes, edges;
	int *nodesNumber, *printedCutPointNodeNumber;

	input >> nodes >> edges;
	nodesNumber = new int[nodes];
	printedCutPointNodeNumber = new int[nodes];

	for (int i = 0; i < nodes; i++) {
		nodesNumber[i] = 1; // initialize all nodes are not visisted
		printedCutPointNodeNumber[i] = 0;
	}
		

	for (int i = 0; i < nodes; i++) {
		graph.push_back(SList<int>()); // construct ajacency linked list
		vecIter.push_back(SList<int>::Iterator());
	}

	int edge_end_node1, edge_end_node2;

	for (int i = 0; i < edges; i++) {
		input >> edge_end_node1 >> edge_end_node2;
		graph[edge_end_node1].AddLast(edge_end_node2); // undirected graph, hence add twice
		graph[edge_end_node2].AddLast(edge_end_node1); // undirected graph, hence add twice
	}

	for (int i = 0; i < nodes; i++) {
		output << "Adjacency List for node " << i << " : ";
		for (vecIter[i] = graph[i].begin(); vecIter[i] != graph[i].end(); vecIter[i]++) {
			output << *vecIter[i] << " ";
		}
		output << endl;
	}

	for (int i = 0; i < nodes; i++) {
		vecIter[i] = graph[i].begin();
	}
		

	input.close(); // close the input file

	int componentsNumber = 0; // indicate the components number of a components

	while (true) {
		bool isNodesAllVisited = true;
		int nodeToStartVisit;

		for (int i = 0; i < nodes; i++) { // find out the next node to visit
			if (nodesNumber[i] == 1) {
				isNodesAllVisited = false;
				nodeToStartVisit = i;
				break;
			}
		}

		if (!isNodesAllVisited) { // start visit from the 'nodeToStartVisit'
			componentsNumber++;
			output << "==============================" << endl;
			Stack<int> s;
			Stack<int> sForExplore;
			vector<int> dfn, low;

			for (int i = 0; i < nodes; i++) { // initialize depth first number and low point number
				dfn.push_back(0);
				low.push_back(0);
			}

			s.Push(nodeToStartVisit); // push the node into s
			sForExplore.Push(nodeToStartVisit); // push the node into s'
			nodesNumber[nodeToStartVisit] = 0; // set the node to be visited
			int nodeToStartVisitTreeEdgeAmount = 0;
			int dfnNumber = 0;
			dfnNumber++;
			dfn[nodeToStartVisit] = dfnNumber;
			low[nodeToStartVisit] = dfnNumber;

			while (!sForExplore.IsEmpty()) { // still node edges are not completely visited
				int v = sForExplore.Top();

				if (vecIter[v] == graph[v].end()) {
					if (v == nodeToStartVisit) {
						if (nodeToStartVisitTreeEdgeAmount >= 2) { // more than two out edge of the start node
							output << "The start node " << nodeToStartVisit << " is a cut point" << endl;
						}
					}

					sForExplore.Pop();

					if (!sForExplore.IsEmpty()) {
						int p = sForExplore.Top();
						if (p != nodeToStartVisit) {
							if (low[p] > low[v]) {
								low[p] = low[v];
							}
							if (low[v] >= dfn[p] && printedCutPointNodeNumber[p] == 0) {
								output << "Node " << p << " is a cut point" << endl;
								printedCutPointNodeNumber[p] = 1;
							}
						}
					}
				} else {
					int w = *(vecIter[v]);
					++(vecIter[v]);
					if (nodesNumber[w] != 0) {// node is not visited
						if (v == nodeToStartVisit)
							nodeToStartVisitTreeEdgeAmount++;
						s.Push(w);
						sForExplore.Push(w);
						nodesNumber[w] = 0;
						dfnNumber++;
						dfn[w] = dfnNumber;
						low[w] = dfnNumber;
					} else {// back edge
						if (low[v] > dfn[w])
							low[v] = dfn[w];
					}
				}
			}

			/* print the result to the output file */
			output << "compnent " << componentsNumber << " = ( ";
			while (!s.IsEmpty()) {
				output << s.Top() << " ";
				s.Pop();
			}
			output << ")" << endl;

		}
		else { // all the nodes are visited
			output.close();
			break;
		}
	}
	return 0;
}
