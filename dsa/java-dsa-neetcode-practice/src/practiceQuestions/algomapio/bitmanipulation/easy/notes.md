Understanding Bit Manipulation in Programming
Bit manipulation is a core concept in computer science and programming, particularly valuable in technical interviews and low-level algorithm optimization. It involves directly working with the binary representation of numbers using bitwise operators. These operations are efficient, low-level, and help solve a wide range of problems, from mathematical tricks to performance-critical code. This guide explains the key bit manipulation techniques introduced in the video, using clear descriptions and proper terminology for SEO and technical learning purposes.

Bitwise AND (&)
The bitwise AND operation, represented by the & symbol, compares each bit of two binary numbers and returns 1 only if both corresponding bits are 1. Otherwise, the result at that position is 0. This operation is often used to check whether a specific bit is set in a binary number. For example, applying AND between a number and a bitmask helps isolate or test particular bits. It is one of the fundamental tools in masking and flag manipulation.

Conceptually, the truth table for AND shows the result is only 1 when both inputs are 1: 0 & 0 = 0, 1 & 0 = 0, 0 & 1 = 0, and 1 & 1 = 1.

Bitwise OR (|)
The bitwise OR operation, denoted by the | symbol, compares two binary numbers and returns 1 at a position if at least one of the corresponding bits is 1. If both bits are 0, the result at that position is 0. This operation is useful when you want to set specific bits in a number without altering the other bits. It is commonly used to combine flags or merge bit states in a compact way.

The logic of bitwise OR can be understood through its truth table: 0 | 0 = 0, 1 | 0 = 1, 0 | 1 = 1, and 1 | 1 = 1.

Bitwise XOR (^)
Bitwise XOR, or exclusive OR, is represented in most programming languages by the ^ symbol. This operator returns 1 at a bit position only if the corresponding bits in the two input values are different. If the bits are the same (both 0 or both 1), the result is 0 at that position.

This property makes XOR particularly useful in programming for toggling bits or identifying unique elements in an array where all other elements appear twice. XORing a number with itself results in 0, and XORing any number with 0 returns the number unchanged. These characteristics are frequently used in algorithmic tricks and data encoding.

The implied truth table for XOR: 0 ^ 0 = 0, 1 ^ 0 = 1, 0 ^ 1 = 1, 1 ^ 1 = 0.

Bitwise NOT (~)
The bitwise NOT operation is a unary operator represented by the ~ symbol. It flips each bit of the number—changing 1s to 0s and 0s to 1s. In binary terms, this means converting all 1s to 0s and all 0s to 1s across the entire bit length of the number.

However, because modern systems use two's complement to represent signed integers, applying NOT to a positive number results in a negative number. Interpreting the result often requires understanding the two's complement representation, where the flipped value is effectively -(n + 1).

Left Shift (<<)
The left shift operation is denoted by the << symbol. It moves the bits of a binary number to the left by a specified number of positions. As bits are shifted out of the left end, they are discarded. Zeros are padded on the right.

Left shifting by n positions is equivalent to multiplying the number by 2ⁿ. This operation is often used in performance optimization or to manipulate specific bit positions. For example, shifting 1 by 3 positions (1 << 3) gives 8, which sets the fourth bit in a byte.

Right Shift (>>)
The right shift operation, represented by the >> symbol, moves the bits of a binary number to the right by a specified number of positions. Bits shifted out of the right end are discarded. What gets filled on the left depends on the type of right shift performed.

In an arithmetic right shift (also called signed right shift), the sign bit is preserved. This means that for positive numbers, zeros are padded on the left, and for negative numbers, ones are padded. Python’s right shift operator >> performs this type of shift. This behavior ensures that the sign of the number remains consistent, making it suitable for signed integers.

A logical right shift always pads zeros on the left, regardless of the sign of the original number. This type is common in some programming languages for unsigned integers but may not be directly supported in languages like Python. Logical shifts are particularly useful for manipulating unsigned binary data.

Right shifting by n positions is roughly equivalent to integer division by 2ⁿ, discarding any remainder.

Conclusion
Bit manipulation is an essential skill in systems programming, cryptography, competitive programming, and technical interviews. Understanding how each bitwise operator works—AND, OR, XOR, NOT, left shift, and right shift—allows developers to write highly efficient code and unlock performance benefits that are often impossible with higher-level abstractions alone.

Mastering bit manipulation begins with a firm grasp of binary number representations, signed and unsigned integers, and how computers use two's complement for negative numbers. From there, applying and combining bitwise operations becomes a powerful tool in solving complex logical problems with concise and efficient code.