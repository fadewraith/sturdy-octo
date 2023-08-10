package main

// import "fmt"
import ( // for importing multiple libraries, without using comma separator
	"fmt"
	"math"

	// "math"
	"errors"
)

// strcut is a collection of fields, so we can group things together, to create it , create outside of the function
type person struct {
	name string
	age  int
}

func main() { // this is where the program starts
	// pointers
	k := 7
	// here k is copy by value, so we're not returning anything, so copied value of k will be discarded. if we pass pointer to the variable, then the function is gonna be able to look @ value at that memory reference and modify the original version
	// increment(k)
	// this is how we send the pointer
	increment(&k)
	fmt.Println(k)
	fmt.Println(&k)
	fmt.Println()
	// declaring struct
	p := person{name: "john", age: 32}
	// fmt.Println(p)
	fmt.Println(p.name)
	fmt.Println()
	// fmt.Println("hello world")
	// declare a variable and if we dont initialise variable, it will give 0
	var x int
	// shorthand syntax for declaraing value
	y := 5
	// if statements with no brackets
	fmt.Println(x + y)
	fmt.Println()
	if x > 6 {
		fmt.Println("more than 6")
	}
	// arrays
	var arr [5]int
	arr[2] = 7
	// shorthand syntax forr arrays -
	arr2 := [5]int{1, 2, 3, 4, 5}      // this is fixed
	arr3 := []int{1, 2, 3, 4, 5, 6, 7} // this one is not fixed
	arr3 = append(arr3, 13)            // append doesnt modifies the original slice and returns a new one
	// this is how map is created and declared
	vertices := make(map[string]int)
	vertices["triangle"] = 3
	vertices["square"] = 4
	fmt.Println(arr)
	fmt.Println(arr2)
	fmt.Println(arr3)
	fmt.Println()
	fmt.Println(vertices)
	fmt.Println(vertices["square"])
	// delete(vertices, "square")
	fmt.Println(vertices)
	fmt.Println()

	// loops, the only type of loop is for loop
	for i := 0; i < 5; i++ {
		fmt.Print((i + 1), ", ")
	}
	fmt.Println()
	// this for loop works like a while loop
	j := 0
	for j < 5 {
		fmt.Print((j + 1), " => j, ")
		j++
	}
	fmt.Println()
	fmt.Println()

	// another way to iterate over data
	for index, value := range arr {
		fmt.Println("index: ", index, ", value: ", value)
	}
	fmt.Println()
	for key, value := range vertices {
		fmt.Println("key: ", key, ", value: ", value)
	}
	fmt.Println()
	result := sum(5, 7)
	fmt.Println(result)
	fmt.Println()
	res, err := sqrt(-81)
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Println((res))
	}
	fmt.Println()
}

func sum(x int, y int) int {
	return x + y
}

func sqrt(x float64) (float64, error) {
	if x < 0 {
		return 0, errors.New("undefined for negative numbers")
	}
	return math.Sqrt(x), nil
}

// func increment(x int) {
// 	x++
// }

// this is how we accept the pointer by prefixing the type with an *
func increment(x *int) {
	// here we are dereferencing the pointer
	*x++
}
