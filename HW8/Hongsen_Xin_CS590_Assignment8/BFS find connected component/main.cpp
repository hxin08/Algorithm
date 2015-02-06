#include <fstream>
#include "SList.h"
#include "Queue.h"
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
	int *nodesNumber; // record whether a node has been visited

	input >> nodes >> edges;
	nodesNumber = new int[nodes];

	for (int i = 0; i < nodes; i++)
		nodesNumber[i] = 1; // initialize all nodes are not visisted

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
			Queue<int> q;
			Queue<int> qForExplore;
			q.EnQueue(nodeToStartVisit); // push the node into s
			qForExplore.EnQueue(nodeToStartVisit); // push the node into s'
			nodesNumber[nodeToStartVisit] = 0; // set the node to be visited
			while (!qForExplore.IsEmpty()) { // still node edges are not completely visited
				int v = qForExplore.Front();
				if (vecIter[v] == graph[v].end())
					qForExplore.DeQueue();
				else {
					int w = *(vecIter[v]);
					++(vecIter[v]);
					if (nodesNumber[w] != 0) {// node is not visited
						q.EnQueue(w);
						qForExplore.EnQueue(w);
						nodesNumber[w] = 0;
					}
				}
			}

			/* print the result to the output file */
			output << "compnent " << componentsNumber << " = ( ";
			while (!q.IsEmpty()) {
				output << q.Front() << " ";
				q.DeQueue();
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
