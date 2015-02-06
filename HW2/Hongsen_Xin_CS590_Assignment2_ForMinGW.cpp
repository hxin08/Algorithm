#include <fstream.h>
#include <iostream>
#include <vector>
#include <assert.h>

using namespace std;

template<class T> class SList;

template<class T>
class SList{

	public: class Iterator;
	friend class Iterator;

    protected:

        struct Node {
            T data;
            Node *next;

            Node() {next = 0;}
            Node(const T& a, Node *p = 0){
                data = a; next = p;
            }
        };

        Node *head;
        Node *tail;

    public:
        SList(){
            head = tail = NULL;
        }

        SList(const SList &cObj);

        const SList& operator=(const SList<T> &cObj);

        ~SList() { SetToEmpty(); }

        bool IsEmpty() const;
        const T& ShowFirst() const;

        void AddFirst(const T &newItem);
        void AddLast(const T &newItem);
        void AddAfter(Iterator i, const T &newItem);
        void RemoveFirst();
        void RemoveAfter(Iterator i);
        void SetToEmpty();




//also should include the position
        class Iterator{

        friend class SList;

        private:
        	Node *position;
        	Iterator(const Node *setPosi){
        		position = const_cast<Node *> (setPosi);
        	}
        public:
        	Iterator():position(0){}

        	T& operator*() const{
        		assert(position!=0);
        		return(*position).data;
        	}

        	Iterator& operator++() {
        		assert( position != 0 );
        		position = position->next;
        		return *this;
        	}

        	// point to the next position (postfix)
        	Iterator operator++(int) {
        		assert( position != 0 );
        		Iterator retval(*this);
        		position = position->next;
        		return retval;
        	}

        	bool operator==(const Iterator &i) const {
        		return position == i.position;
        	}

        	bool operator!=(const Iterator &i) const {
        		return position != i.position;
        	}
        };


    	Iterator Begin() {
    		Iterator ret_Iter(head);
    		return ret_Iter;
    	}

    	Iterator End() {
    		Iterator ret_Iter(0);
    		return ret_Iter;
    	}
};
template<class T>
SList<T>::SList(const SList<T> &cObj) {

	head = NULL;
	tail = NULL;

	Node *cur = cObj.head;
	while(cur) {
		AddLast(cur->data);
		cur = cur->next;
	}
}

template<class T>
const SList<T>& SList<T>::operator=(const SList<T> &cObj){
	if(this!=&cObj){
		SetToEmpty();
		Node *cur = cObj.head;
		while(cur){
			AddLast(cur->data);
			cur=cur->next;
		}
	}
	return *this;
}

template<class T>
void SList<T>::RemoveFirst(){
    assert(!IsEmpty());
    Node *tmp = head;
    head = head->next;
    delete tmp;
    if(head == 0){
        tail = 0;
    }
}

template<class T>
void SList<T>::AddLast(const T &newItem){
    if(head == 0){
        head = tail = new Node(newItem);
    }
    else{
        if(tail == 0){
        	cout<<"error!!!"<<endl;
        }else{
    	tail->next = new Node(newItem);
        tail = tail->next;
        }
    }
}

template<class T>
void SList<T>::AddFirst(const T &newItem){
	if (head==0){
		head = tail = new Node(newItem);
	}else{
		head = new Node(newItem, head);
	}
}

template<class T>
void SList<T>::AddAfter(SList<T>::Iterator i, const T &newItem){
    assert(i.position != 0);
    i.position->next = new Node(newItem, i.position->next);
}

template<class T>
const T& SList<T>::ShowFirst() const{
    assert(!IsEmpty());

    return (head->data);
}

template<class T>
bool SList<T>::IsEmpty() const{
    return (head == NULL);
}

template<class T>
void SList<T>::SetToEmpty(){
    while(!IsEmpty()){
        RemoveFirst();
    }
}

template <class T>
void SList<T>::RemoveAfter(SList<T>::Iterator i){
	assert(i.position != 0 && i.position->next != 0);
	Node *save = i.position->next;
	i.position->next = i.position->next->next;
	delete save;
}

int main(){

	SList<int> list1;

	list1.AddFirst(1);
	list1.AddFirst(2);

    list1.AddLast(3);
    list1.AddLast(4);

    SList<int>::Iterator i1;

    cout<<"First Operation!!!"<<endl;
    for(i1 = list1.Begin(); i1 != list1.End(); i1++){
        cout<<*i1<<" "<<endl;
    }

    i1=list1.Begin();

    list1.AddAfter(i1,5);

    i1++;

    list1.AddAfter(i1,6);

    cout<<"2nd Operation!!!"<<endl;
    for(i1 = list1.Begin(); i1 != list1.End(); i1++){
        cout<<*i1<<" "<<endl;
    }

    i1=list1.Begin();
    i1++;
    i1++;

    list1.RemoveAfter(i1);

    cout<<"3rd Operation!!!"<<endl;
    for(i1 = list1.Begin(); i1 != list1.End(); i1++){
        cout<<*i1<<" "<<endl;
    }

    i1=list1.Begin();
    cout<<"The first item (Before)!!!"<<endl;
    cout<<*i1<<endl;

    list1.RemoveFirst();
    int show1 =0;
    show1 = list1.ShowFirst();
    cout<<"The first item (After)!!!"<<endl;
    cout<<show1<<endl;


    cout<<"4th Operation!!!"<<endl;
    for(i1 = list1.Begin(); i1 != list1.End(); i1++){
        cout<<*i1<<" "<<endl;
    }

    SList<int> list2;
    //cout<<"1. "<<endl;

   // SList<int>::Iterator i2;
    //cout<<"2. "<<endl;

    list2 = list1;
    //cout<<"3. "<<endl;

SList<int>::Iterator i2;

	cout<<"5th Operation!!!"<<endl;
    for(i2 = list2.Begin(); i2 != list2.End(); i2++){
        cout<<*i2<<" "<<endl;
    }

    return 0;
}








