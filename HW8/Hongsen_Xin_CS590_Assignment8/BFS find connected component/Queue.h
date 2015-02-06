#ifndef QUEUE_H
#define QUEUE_H
#include "SList.h"

template <class T> class Queue {
private:
	SList<T> container;
public:
	const T& Front() { return container.ShowFirst(); }
	void DeQueue() { container.RemoveFirst(); }
	void EnQueue(const T& newItem) { container.AddLast(newItem); }
	bool IsEmpty() { return container.isEmpty(); }
};

#endif