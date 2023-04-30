#include<iostream>
using namespace std;

class Element {
    public:
        int i;
        int j;
        int x;
};

class Sparse {
    
    private:
        int m, n, num; Element *e;
        
    public:
        Sparse(int m, int n, int num) {
            this->m = m;
            this->n = n;
            this->num = num;
            e = new Element[this->num]; // creating dynamic array of Element
        }
        ~Sparse() {
            delete [] e;
            cout << "destructor called";
        }
        
        Sparse operator+(Sparse &s); // declaring member function for adding
        
        // operator overloading specific for insertion and extraction, where & is return by reference
        // >> = extraction, << = insertion, is and os can be any name
        // making it as a friend function, because these method of mplementation is done using friend function
        // function is friend, they are global and we can use them withou scope resolution operator
        friend istream & operator>>(istream &is, Sparse &s); // signature for istream operator, i.e. extraction operator
        friend ostream & operator<<(ostream &os, Sparse &s); // signature for ostream, i.e. insertion operator
        
                // void read() { // converting read and display method 
        //     cout << "enter non zero elements:\n";
        //     for(int i = 0; i < num; i++) {
        //         cin >> e[i].i >> e[i].j >> e[i].x;
        //     }
        // }
        
//         void display() {
//             int k = 0;
//             for(int i = 0; i < m; i++) {
//                 for(int j = 0; j < n; j++) {
//                     if(i == e[k].i && j == e[k].j) {
//                         cout << e[k++].x << " ";
//                     } else {
//                         cout << "0 ";
//                     }
//                 }
//                 cout << endl;
//             }
//         }
};
        istream & operator>>(istream &is, Sparse &s) { // converting read and display method 
            cout << "enter non zero elements:\n";
            for(int i = 0; i < s.num; i++) {
                cin >> s.e[i].i >> s.e[i].j >> s.e[i].x;
            }
            return is; // because function is havbing return type of istream as is, so return is
        }
        
        ostream & operator<<(ostream &os, Sparse &s) {
            int k = 0;
            for(int i = 0; i < s.m; i++) {
                for(int j = 0; j < s.n; j++) {
                    if(i == s.e[k].i && j == s.e[k].j) {
                        cout << s.e[k++].x << " ";
                    } else {
                        cout << "0 ";
                    }
                }
                cout << endl;
            }
            return os;
        }
        
        Sparse Sparse::operator+(Sparse &s) { // first Sparse is return type and second Sparse is class name
            int i, j, k; // here i is used for trakcing of Sparse1, j for Sparse2 and k for Sum Sparse 
            if(m != s.m || n != s.n)
                return Sparse(0, 0, 0);
            Sparse *sum = new Sparse(m, n, num + s.num);
            i = j = k = 0;
            while(i < num && j < s.num) { // this condition is for is one of them is not equal, then simply copy them as it is
                if(e[i].i < s.e[j].i)
                    sum->e[k++] = e[i++];
                else if(e[i].i > s.e[j].i)
                    sum->e[k++] = s.e[j++];
                else { // doing the same thing for columns
                    if(e[i].j < s.e[j].j)
                        sum->e[k++] = e[i++];
                    else if(e[i].j > s.e[j].j)
                        sum->e[k++] = s.e[j++];
                    else {
                         sum->e[k] = e[i]; // copy i,j and x
                         sum->e[k++].x = e[i++].x + s.e[j++].x;
                    }
                }
            }
            for(; i < num; i++) sum->e[k++] = e[i];
            for(; j < s.num; j++) sum->e[k++] = s.e[j];
            sum->num = k;
            
            return *sum; // as it is call by value, we will return the dereferenced value
        }
        

int main() {
    Sparse s1(5, 5, 5);
    Sparse s2(5, 5, 5);
    cin >> s1;
    cin >> s2;
    Sparse sum = s1 + s2;
    cout << "first matrix" << endl << s1;
    cout << "second matrix" << endl << s2;
    cout << "sum matrix" << endl << sum;
    
    // s1.read();
    // s1.display();
    return 0;
}

