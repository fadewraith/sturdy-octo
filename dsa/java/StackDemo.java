import java.util.Stack;

public class StackDemo {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();

        System.out.println(stack.empty());

        stack.push("Minecraft");
        stack.push("Skyrim");
        stack.push("DOOM");
        stack.push("Borderlands");
        stack.push("IGI");

        System.out.println(stack.empty());
        System.out.println(stack);
        System.out.println(stack.pop());;
        System.out.println(stack.peek());
        System.out.println(stack.search("DOOM"));

//        for(int i = 0; i < 1000000000; i++) {
//            stack.push("Fortnite");
//        }

//        uses of stack
//        undo/redo features in text editors
//        moving back/forward through browser history
//        backtracking algorithms (maze, file directories)
//        calling functions (call stack)
    }
}
