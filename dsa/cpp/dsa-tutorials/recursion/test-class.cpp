#include<iostream>
using namespace std;

class Rectangle {
    private:
        int length;
        int breadth;
    public:
        Rectangle() {
            int length = breadth = 0;
        }
        Rectangle(int l, int b) { // constructor overloading
            length = l;
            breadth = b;
        }
        int area() {
            return length * breadth;
        }
        int perimeter() {
            return 2 * (length + breadth);
        }
        void setLength(int l) { // mutator function
            length = l;
        }
        void setBreadth(int b) {
            breadth = b;
        }
        int getLength() { // accessor function
            return length;
        }
        int getBreadth() {
            return breadth;
        }
        ~Rectangle() { // destructor, use if dynamic memory is used
            cout << "Destructor called at the end";
        }
};

int main() {
    Rectangle r(10, 5);
    cout << "Area => " << r.area() << endl;
    cout << "Perimeter => " << r.perimeter() << endl;
    return 0;
}
