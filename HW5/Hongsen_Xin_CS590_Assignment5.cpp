#include <iostream>
#include <queue>
#include <stack>
#include <fstream>

using namespace std;

char score[256];

void removeOther(string& in){
	for (unsigned int i = 0; i< in.size(); ++i){
		if (in[i] == ' ' || in[i] < 32)
			in.erase(i--, 1);
	}
}

void pushIntoQue(const string& str, queue<char>& que,
	stack<char>& stk){
	for (unsigned int i = 0; i<str.size(); ++i){
		if (isdigit(str[i]))
			que.push(str[i]);
		else{
			if (str[i] == ')'){
				while (stk.top() != '('){
					que.push(stk.top());
					stk.pop();
				}
				stk.pop();
			}
			else{
				if (stk.size()>0 && score[int(str[i])] <
					score[int(stk.top())] && stk.top() != '('){
					que.push(stk.top());
					stk.pop();
					queue<char> temp;
					while (stk.size()>0 && score[int(str[i])] <
						score[int(stk.top())] && stk.top() != '('){
						temp.push(stk.top());
						stk.pop();
					}
					stk.push(str[i]);
					while (temp.size() > 0){
						stk.push(temp.front());
						temp.pop();
					}

				}
				else
					stk.push(str[i]);
			}

		}
	}
	while (stk.size()>0){
		que.push(stk.top());
		stk.pop();
	}
}

int solvepost(queue<char>& que){
	stack<int> stk;
	while (que.size() > 0){
		if (stk.size() > 0 && !isdigit(que.front())){
			int x1 = stk.top();
			stk.pop();
			int x2 = stk.top();
			stk.pop();
			int out = 0;
			switch (que.front()){
			case '+': out = x2 + x1;
				break;
			case '-': out = x2 - x1;
				break;
			case '*': out = x2 * x1;
				break;
			case '/': out = x2 / x1;
				break;
			default: break;
			}
			stk.push(out);
		}
		else{
			stk.push(que.front() - '0');
		}
		que.pop();
	}
	return stk.top();
}

int main(){
	queue<char> que, que2;
	stack<char> stk;
	score['-'] = 0;
	score['+'] = 0;
	score['*'] = 1;
	score['/'] = 1;
	score['('] = 2;
	score['('] = 2;


	ifstream file("infile.dat");


	string input((std::istreambuf_iterator<char>(file)),
		std::istreambuf_iterator<char>());

	removeOther(input);
	cout << "Infix:" << input << endl;;
	pushIntoQue(input, que, stk);

	cout << "Postfix:";
	while (que.size() > 0){
		cout << char(que.front());
		que2.push(que.front());
		que.pop();
	}
	cout << endl;

	cout << "Result:" << solvepost(que2) << endl;

	file.close();

	return 0;
}