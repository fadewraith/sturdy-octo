// #include <bits/stdc++.h>
#include<iostream>
#include<vector>
#include<algorithm>
#include<set>
#include<map>
#include<climits>

using namespace std;

bool f(int x, int y) { // global comparator function to sort in the order we want
    return x > y;
}

void vectorDemo() {
    
    vector<int> A = { 11, 2, 3, 14 };
    cout << A[1] << endl;
    
//  sort(A.begin(), A.end()); // O(NlogN)
    
//  for(auto i: A)  cout << i << ", ";
    bool present = binary_search(A.begin(), A.end(), 3);
    present = binary_search(A.begin(), A.end(), 4);
    // cout << 3 << " is present: " << present;
    // cout << 4 << " is not present: " << present;
    
    A.push_back(100);
    A.push_back(100);
    A.push_back(100);
    A.push_back(100);
    A.push_back(123);
    
    // lower_bound & upper_bound on sorted vectors is done in O(logN) time using binary search
    
    vector<int>::iterator it = lower_bound(A.begin(), A.end(), 100); // returns iterator of vector<int>, first element >= i.e. 100
    auto it2 = upper_bound(A.begin(), A.end(), 100); // returns iterator of vector<int>, first element > i.e. 123
    
    // iterators in vectors are random, we can simply perform arithematic operations on them in constant time
    
    cout << *it << " " << *it2 << endl;
    cout << it2 - it << endl; // O(1) 8 - 4, index of first 100 is 4 and index of 123 is 8, so diff is 8 - 4 = 4
    
    // sorting the vector in reverse order, overloading the sort function by passing the comparator function to control the order
    // where f is comparator function declared globally
    sort(A.begin(), A.end(), f);
    vector<int>::iterator it3; // using iteraotr to print the vector
    
    // for(it3 = A.begin(); it3 != A.end(); it3++) cout << *it3 << ", ";
    for(int &x: A) { // if we want to update the value of A, we can u se reference operator and any changes here will reflect in A
        if(x == 100) x = 101;
        cout << x << ", ";
    }
    // draw back is inserting, sorting and performing lower_bound & upper_bound on elements 
    cout << endl;
}

void setDemo() {
    // in vector, we can simply add at the end, if vector was sorted and if we insert some number, it will disturb the ordering of the vector unless and until, the last number we are inserting is the max or greater than the max of the present vector.
    // so this is where set is useful and insert time is O(logN) time, so at any time our complete collection is sorted and we dont have to perform a sort operation explicitly whenever we're inserting any new number which is the case in vector and sorting is in LogN
    set<int> S; // set internally maintains the ascending order of the elements and each operation is in logN time
    S.insert(10);
    S.insert(2);
    S.insert(3);
    S.insert(1);
    S.insert(-1);
    S.insert(-1);
    for(int x: S) cout << x << " ";
    cout << endl;
    // auto it = S.find(-1); // if something is not present, this will return the iterator S.end();
    auto it = S.find(20);
    if(it == S.end()) cout << "element not found"<< endl;
    else cout << *it;
    // to find out first number in the set, which is > -1 or which is > 0 
    // for strictly greater than, we can use upper bound
    auto it2 = S.lower_bound(-1);
    auto it3 = S.upper_bound(0);
    cout << *it2 << ", " << *it3 << endl;
    auto it4 = S.upper_bound(10);
    if(it4 == S.end()) cout << "cant find element";
    else cout << *it4 << endl;
    
}

void mapDemo() {
    map<int, int> A;
    A[1] = 100; // takes logN time 
    A[2] = -1;
    A[3] = 200;
    A[100000232] = 1;
    
    map<char, int> count;
    string x = "hello world";
    
    for(char c: x) count[c]++; // logN time
    cout << count['l'] << " " << count['o'] << endl; // finding how many times a and o occurs in the string, here we are retrieving integer value mapped to character
    
}

void PowerOfStlDemo() {
    // [x, y]
    // pair is x, y, ex - {1, 2}, {-1, -1}
    // pair {a, b} is smaller than pair {c, d}
    // iff (a < b) or (a == b and c < d)
    // values in pair X = { a, b } can be accessed using X.first and X.second
    set<pair<int, int>> S;
    // rachit jain stl container video
    
    S.insert({ 401, 450 });
    S.insert({ 2, 3 });
    S.insert({ 10, 20 });
    S.insert({ 30, 400 });
    // 2, 3
    // 10, 20
    // 30, 400
    // 401, 450
    /*
        performing upper_bound on {31, any_num}, so it will give iterator to {401, 450} in set.
        we will go one step back and will come to {30, 400} and if that pair contains 31, then we can that
        this is the interval we want otherwise no pair in a set which contains given point
        corner case - 
        doing upper bound on {10, 0}, iterator will point to {10, 20} and then we will go one step back and based on the algorithm, we will go to one step back, i.e. {2, 3} and then algorithm will fail telling the given pair doesnt contains in any interval 
        thats why we are performing upper_bound on {point, INT_MAX}
        if point = 1, we get upper_bound to {2, 3} and 1 step back doesnt exists
        point = 401, point will return S.end() and we go 1 step back and will give {401, 450}
    */
    
    int point = 401;
    // int point = 1;
    // int point = 13;
    auto it = S.upper_bound({ point, INT_MAX }); // performing upper bound on pairs
    if(it == S.begin()) {
        cout << "the given point is not lying in any interval\n";
        return;
    }
    it--;
    pair<int, int> current = *it;
    if(current.first <= point && point <= current.second) {
        cout << "yes its present: " << "{ " << current.first << ", " << current.second << " }" << endl;
    } else {
        cout << "the given point is not lying in any interval\n";
    }
    
    
    
}

int main() {
    // vectorDemo();
    // setDemo();
    // mapDemo();
    PowerOfStlDemo();
    return 0;
}