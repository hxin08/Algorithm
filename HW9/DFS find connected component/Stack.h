#ifndef STACK_H
#define STACK_H

#include "SList.h"
template <class T> class Stack {
private:
	SList<T> container;
public:
	const T& Top() { return container.ShowFirst(); }
	void Pop() { container.RemoveFirst(); }
	void Push(const T& newItem) { container.AddFirst(newItem); }
	bool IsEmpty() { return container.isEmpty(); }
};

#endif
