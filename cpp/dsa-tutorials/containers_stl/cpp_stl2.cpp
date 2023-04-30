// #include<bits/stdc++.h>
#include<iostream>
#include<map>
#include<unordered_map>
#include<vector>

using namespace std;

int main() {
    // map<char, int> M;
    // unordered_map<char, int> U;
    /*
    in simple maps, they maintain the sorted nature of the keys, so ordered_map is having a BST internally and this tree is always balanced, keys are nodes and values are used to build the BST. value is also gets tored inside in each node
    unordered_map makes an array and using hashing function sets the keys, their can be a chance of collision also
    - using codeforces contest 808 problem 0
    given array - [2, 2, 3, 4, 5]
    splitting the arrays into 2 parts where some of first pasrt = sum of second part and array them accordingly
    ans -> [2, 2, 4, 3, 5] // [2, 2, 4] = 8 = [3, 5]
    logic - 
    sum of an array S, parition, sum = S / 2, S / 2
    iterating on all the prefixes or all the first parts that are possible
    now lets say, we are at position i = 5, and from 0 to i, it will be S', if S' = S / 2, then we need to do partition from  o to 5th index
    if thats not the case, then what can happen is - S' < S , other case
    case 1 - S' < S / 2
    case 2 - S' > S / 2
    case 1 - means that the number we have to delete lies in the second part - 
    let x = S/2 - S', then delete x from other parition and put it in any position before index = 5(here ex case)
    so now overall it becomes, S' + x = S / 2, and we can now do the partition
    so ath this place, if we know the sum of first i numbers, we simply compute x, and will check if the remaining part of the element has x, if it has, we can simply print the S,
    case 2 - 
    which means we need to remove some element, y from first part, so that S' can be reduced upto S / 2, y = S' - (S / 2),
    if y exists delete it from first part and insert it into second part, and then simply print the S
    --
    array, i = 0 to n - 1
    map<int, int> and a[0] is map to 1, that means it exists 
    either we will perform lookup at {a[0], 1} or {a[1], a[2], a[n - 1]}
    */
    
    // string s = "hello world";
    // // add(key, value) - logN(for ordered map), O(1)(for unordered_map)
    // // erase(key) - logN(for ordered map), O(1)(for unordered_map)
    // for(char c: s) M[c]++; // O(NlogN), N = size of the string
    
    // for(char c: s) U[c]++; // O(N)
    
    int n;
    cin >> n;
    vector<int> A(n + 5, 0); // initialise with all 0, to avoid segmentation fault, we add 5 to n
    long long S = 0; // to avoid overflow
    
    for(int i = 0; i < n; i++) cin >> A[i], S += A[i];
    if (S & 1) { // which means if the number was odd
        cout << "No\n";
        return 0;
    }
    
    // map<long long, int> first, second;
    unordered_map<long long, int> first, second;
    
    first[A[0]] = 1;
    
    for(int i = 1; i < n; i++) second[A[i]]++;
    
    long long sdash = 0;
    
    for(int i = 0; i < n; i++) {
        sdash += A[i]; // first i numbers
        if (sdash == S / 2) {
            cout << "Yes\n";
            return 0;
        }
        if (sdash < S / 2) {
            long long x = S / 2 - sdash;
            // delete element from second half, and insert into first half
            if(second[x] > 0 ) {
                cout << "yes\n";
                return 0;
            }
        } else {
            long long y = sdash - S / 2;
            if(first[y] > 0) {
                cout << "yes\n";
                return 0;
            }
        }
        // moving to i + 1, i + 1 has to be moved to the first part and removed from the second part
        first[A[i + 1]]++;
        second[A[i + 1]]--; 
    }
    
    cout << "no\n";
    
    return 0;
}