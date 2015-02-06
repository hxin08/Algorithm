#ifndef SLIST_H
#define SLIST_H

#include <assert.h>
template <class T> class SList
{
private:
	struct Node
	{
		T data;
		Node *next;

		Node()
		{
			next = 0;
		}

		Node(const T& a, Node *p = 0)
		{
			data = a;
			next = p;
		}
	};

public:
	class Iterator;
	Node *head;
	Node *tail;

	SList()
	{
		head = new Node();
		tail = new Node();
		head->next = tail;
	}

	SList(const SList<T> & cObj)
	{
		head = new Node();
		tail = new Node();
		head->next = tail;

		Node *current = cObj.head->next;
		while (current->next != 0)
		{
			T newData = current->data;
			AddLast(newData);
			current = current->next;
		}
	}

	SList<T>& operator=(const SList<T> &cObj)
	{
		setToEmpty();
		Node *current = cObj.head->next;
		while (current->next != 0)
		{
			T currentData = current->data;
			AddLast(currentData);
			current = current->next;
		}
		return *this;

	}

	~SList()
	{
		setToEmpty();
		delete head;
		delete tail;
	}

	Iterator begin()
	{
		return Iterator(head->next);
	}

	Iterator end()
	{
		return Iterator(tail);
	}

	void AddLast(const T &newItem)
	{
		Node *newLast = new Node(newItem);
		if (head->next == tail)
		{
			head->next = newLast;
			newLast->next = tail;
		}
		else {
			tail->data = newLast->data;
			delete newLast;
			tail->next = new Node();
			tail = tail->next;
		}
	}

	void setToEmpty()
	{
		while (head->next != tail)
		{
			Node *temp = head->next;
			head->next = head->next->next;
			delete temp;
		}
	}

	bool isEmpty() const
	{
		return (head->next == tail);
	}

	const T& ShowFirst() const
	{
		return head->next->data;
	}

	void AddFirst(const T &newItem)
	{
		Node *newNode = new Node(newItem);
		newNode->next = head->next;
		head->next = newNode;
	}

	void AddAfter(Iterator i, const T &newItem)
	{
		assert(i.it != tail);
		Node *newNode = new Node(newItem);
		newNode->next = i.it->next;
		i.it->next = newNode;
	}

	void RemoveFirst()
	{
		assert(head->next != tail);
		Node *temp = head->next;
		head->next = head->next->next;
		delete temp;
	}

	void RemoveAfter(Iterator i)
	{
		assert(i.it != tail && i.it->next != tail);
		Node *temp = i.it->next;
		i.it->next = i.it->next->next;
		delete temp;
	}

	class Iterator
	{
		friend class SList<T>;

	private:
		Node *it;
		Iterator(Node *pt)
		{
			it = pt;
		}

	public:
		Iterator()
		{
			this->it = 0;
		}
		Iterator & operator++()
		{
			it = it->next;
			return *this;
		}

		Iterator operator++(int)
		{
			Iterator old(*this);
			it = it->next;
			return old;
		}

		T & operator*()
		{
			return it->data;
		}

		bool operator==(const Iterator& other)
		{
			return it == other.it;
		}

		bool operator!=(const Iterator& other)
		{
			return !(it == other.it);
		}
	};

};

#endif