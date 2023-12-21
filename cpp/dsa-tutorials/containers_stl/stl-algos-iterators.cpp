#include<bits/stdc++.h>
using namespace std;

// Pairs
void pairsExplained() {
    pair<int, int> p = { 1, 3 };
    cout << p.first << " " << p.second << endl;
    
    pair<int, pair<int, int>> p2 = { 1, { 3,  4 } };
    cout << p2.first << " " << p2.second.second << " " << p2.second.first << endl;
    
    pair<int, int> arr[] = { { 1, 2 }, { 2, 5 }, { 5, 1 } };
    cout << arr[0].first << endl;
    cout << arr[1].second << endl;
}

// Vector - dynamic array
void vectorsExplained() {
    
    // { 10, 20, 30, 40, 50 }
    // vector size can be increased dynamically, even after pre defined size
    vector<int> v; // creates an empty container
    v.push_back(1);
    v.emplace_back(2); // similar to push back, it dynamically increases the size and pushed to the back and emplace_back is faster than the push_back, rason is on quora
    
    vector<pair<int, int>> vec;
    
    vec.push_back({1, 2});
    vec.emplace_back(3, 4); // here we dont use curly braces, coz it automatically assumes to be a pair and inserts into vector defined
    
    vector<int> v1(5, 100); // 5 = size, will contain 5 instances of 100 - {100, 100, 100, 100, 100}
    
    vector<int> v2(5); // will create 5 instances of a value and value will different for compilers and systems, can be 0 or grabage value
    
    vector<int> v3(5, 20); // here if we push the value using v3.push_back(9), it will increase the size by 1 and inserts 9
    
    vector<int> v4(v3); // copied v3 to v4, v4 will be another container with 5 instances
    
    vector<int>::iterator it = v.begin(); // pointing @ beginning of the memory address of the vector, i.e. the addr of the index 0 stored in memory
    it++;
    cout << *(it) << " ";
    
    it = it + 2;
    cout << *(it) << " ";
    
    vector<int>::iterator it1 = v.end(); // this will point @place after the last element
    // vector<int>::iterator it2 = v.rend(); // never used. reverse end, will point before the beginning of the first element
    // vector<int>::iterator it3 = v.rbegin(); // never used, will point at 50, it++ will point to 40 and so on
    
    cout << v[0] << " " << v.at(0); 
    cout << v.back() << " "; // the element @end of the array, i.e. 50
    
    for(vector<int>::iterator it4 = v.begin(); it4 != v.end(); it++) {
        cout << *(it) << " ";
    }
    
    for(auto it5 = v.begin(); it5 != v.end(); it5++) {
        cout << *(it5) << " ";
    }
    
    for(auto it: v) {
        cout << it << " ";
    }
    
    // erase
    v.erase(v.begin() + 1); // give the exact location to delete
    v.erase(v.begin() + 2, v.begin() + 4); // give the start and end point, end point is the address after the last element which is to be deleted(means start is included, end is exlcuded)
    
    
    // insert function
    vector<int>v(2, 100); // {100, 100}
    v.insert(v.begin(), 300); // {300, 100, 100}
    v.insert(v.begin() + 1, 2, 10); // {300, 10, 10, 100, 100}

    vector<int> copy(2, 50); // {50, 50}
    // @beginning of the vector v, insert everything from start to end which is in vector 'copy'
    v.insert(v.begin(), copy.begin(), copy.end()); // {50, 50, 300, 10, 10, 100, 100}

    // {10, 20}
    cout << v.size(); // 2
    
    // {10, 20}
    v.pop_back(); // {10}

    // v1 -> {10, 20}
    // v2 -> {30, 40}
    v1.swap(v2); // v1 -> {30, 40}, v2 -> {10, 20}

    v.clear(); // erases the entire vector

    cout << v.empty();
    
}

// List
void listExplained() {
    list<int> ls;
    // insert fn in vector is costly, here it is singly linked list
    // in list internal operatn is doubly link list

    ls.push_back(2); // {2}
    ls.emplace_back(4); // {2, 4}

    ls.push_front(5); // {5, 2, 4}, its very very cheap

    ls.emplace_front(); // {2, 4}
    // rest functions are same as vector
    // begin, end, rbegin, rend, clear, insert, size, swap
}

// dequeue
void dequeueExplained() {
    dequeue<int> dq;
    dq.push_back(1);
    dq.emplace_back(2);
    dq.push_front(4);
    dq.emplace_front(3);

    dq.pop_back();
    dq.pop_front();

    dq.back();

    dq.front();

    // rest functions are same as vector
    // begin, end, rbegin, rend, clear, insert, size, swap
}

// stack
void stackExplained() {
    stack<int> st;
    st.push(1);
    st.push(2);
    st.push(3); // {3, 2, 1}
    st.push(3);
    st.emplace(5); // {5, 3, 3, 2, 1}

    cout << st.top(); // 5

    st.pop(); // {3, 3, 2, 1}

    cout << st.top();
    cout << st.size(); // 4
    cout << st.empty();

    stack<int> st1, st2;
    st1.swap(st2);
    // push, pop, top - O(1)

}

// queue
void queueExplained() {
    queue<int> q;
    q.push(1);
    q.push(2);
    q.emplace(4); // {1, 2, 4}

    q.back() += 5; // 4 + 5 = 9, in place of 4 it will be 9

    cout << q.back(); // 9

    cout << q.front(); // prints 1

    q.pop(); // {2, 9}

    cout << q.front(); // 2

    // size swap empty same as stack
}

void pqExplained() {
    // push - log n, top - 1, pop - log n
    priority_queue<int> pq;
    pq.push(5);
    pq.push(2);
    pq.push(8); // {8, 5, 2}
    pq.emplace(10); // {10, 8, 5, 2}

    cout << pq.top();

    pq.pop();

    cout << pq.top();

    // size swap empty fn same as others

    // min heap
    priority_queue<int, vector<int>, greater<int>> pq;
    pq.push(5);
    pq.push(2); // {2, 5}
    pq.push(8); // 2, 5, 8
    pq.emplace(10); // {2, 5, 8, 10}

    cout << pq.top(); // 2
}

void setExplained() {
    // in set everything happens in the logarithmic time
    set<int> st;
    st.insert(1);
    st.emplace(2); // {1, 2}
    st.insert(2); // {1, 2}
    st.insert(4); // {1, 2, 4}
    st.insert(3); // {1, 2, 3, 4} , a tree is maintained

    // functionality of insert in vector
    // can be used also, that only increases
    // efficiency

    // begin(), end(), rbegin(), rend(), size(),
    // empty() and swap() are same as those of above

    auto it = st.find(3); // returns an iterator 
    auto it = st.find(6); // if element not present, returns st.end()
    st.erase(5); // takes log time, del 5 and maintains the sorted order

    int cnt = st.count(1); // if present, will return 1 else 0
    auto it = st.find(3); // element to be erased or iterator
    st.erase(it); // takes constant time

    auto it1 = st.find(2);
    auto it2 = st.find(4);

    st.erase(it1, it2); // after erase {1, 4, 5} [first, last)

    // lower_bound() and upper_bound() function works in the same way
    // as in vector it does.

    // syntax
    auto it = st.lower_bound(2);
    auto it = st.upper_bound(3);

}

void multisetExplained() {
    // everything is same as set
    // only stores duplicate elements also
    multiset<int> ms;
    ms.insert(1);
    ms.insert(1);
    ms.insert(1); // {1, 1, 1}
    ms.erase(1); // all 1's erased
    int cnt = ms.count(1);
    // only a single one erased
    ms.erase(ms.find(1));
    ms.erase(ms.find(1), ms.find(1) + 2);
    // rest all functions are same as set

}

void usetExplained() {
    unordered_set<int> st;
    // lower_bound and upper_bound function
    // does not works, rest all fns are same
    // as above, it does not stores in any particular order
    // it has a better complexity, in most cases it has O(1)
    // than set in most cases, except some when collision happens

}

void mapExplained() {
    // map stores unique keys in sorted order of key
    map<int, int> mp;
    map<int, pair<int, int>> mp1;
    map<pair<int, int>, int> mp2;

    mp[1] = 2; // on key 1, store value 2
    mp.emplace({3, 1}); // key = 3, value = 1
    mp.insert({2, 4}); // key = 2, value = 4
    mp2[{2, 3}] = 10; // key = {2, 3}, value = 10;

    // {
    //     {1, 2}
    //     {2, 4}
    //     {3, 1}
    // }

    for(auto it: mp) {
        cout << it.first << " " << it.second << endl;
    }

    cout << mp[1]; // value = 2
    cout << mp[5]; // if it doesn exist it will gove 0

    auto it = mp.find(3);
    cout << *(it).second;

    auto it = mp.find(5); // points to mp.end(), in case 5 key is not found

    auto it = mp.lower_bound(2);

    auto it = mp.upper_bound(3);
    // erase, swap, size, empty are same as above
}

void multimapExplained() {
    // everything same as map, only it can store multiple keys
    // only mp[key] cant be used here
}

void umapExplained() {
    // same as set and uordered_set diff
}

bool comp(pair<int, int> p1, pair<int, int> p2) {
    // if(p1.second < p2.second) {
    //     return true;
    // } else if(p1.second == p2.second) {
    //     if(p1.first > p2.second) return true;
    // }
    // return false;
    if(p1.second < p2.second) return true;
    if(p1.second > p2.second) return false;
    if(p1.first > p2.first) return true;
    return false;
}

// algorithms

void extraExplained() {
    sort(a, a + n); // (start_iterator, end_iterator)
    sort(v.begin(), v.end());
    sort(a + 2, a + 4);
    sort(a, a + n, greater<int>); // will sort in descending order
    pair<int, int> a[] = {{1, 2}, {2, 1}, {4, 1}};
    // sort it according to second element
    // if second element is same, then sort
    // it according to first element but in descending

    sort(a, a + n, comp); // {{4, 1}, {2, 1}, {1, 2}};

    int num = 7;
    // for 6, it will return 2, as 6 = 110
    int cnt = __builtin_popcount(); // how many 1's or setbits are there, for 7 its 111, it will return 3 

    long long num = 165786578687;
    int cnt = __builtin_popcountll();

    string s = "123";
    sort(s.begin(), s.end());

    do {
        cout << s << endl;
    } while(next_permutation(s.begin(), s.end()));

    int maxi = *max_element(a, a + n);
}

int main() {
    pairsExplained();
    vectorsExplained();
    listExplained();
    dequeueExplained();
    stackExplained();
    queueExplained();
    pqExplained();
    setExplained();
    multisetExplained();
    usetExplained();
    mapExplained();
    multimapExplained();
    umapExplained();
    extraExplained();
    return 0;
}
