package compilers.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TREE-WALKING INTERPRETER for a tiny language (lexer -> parser -> AST -> evaluator)
 *
 * WHAT IT IS:
 * A complete, minimal programming language in one file:
 *   let x = 10;               variable declaration (block-scoped)
 *   x = x + 1;                assignment
 *   print x * 2;              output
 *   if (x > 5) { ... } else { ... }
 *   while (x < 100) { ... }
 *   { ... }                   nested block = new scope
 * Expressions: integers, true/false, + - * / %, == != < <= > >=, && || (short-
 * circuit), unary - and !, parentheses. Values are 64-bit integers; booleans are
 * 1 and 0 (like C).
 *
 * WHEN TO USE THIS:
 * - Understanding how Python/Ruby/early JavaScript run code; config/rule/formula
 *   languages, template engines, calculators, DSLs inside larger systems.
 * - Next steps beyond this: compile the AST to bytecode for a stack VM (faster),
 *   add functions/closures (environments captured at definition time).
 *
 * THE PIPELINE:
 * 1. LEXER: characters -> tokens (NUMBER, IDENT, keyword, operator).
 * 2. PARSER (recursive descent, one method per precedence level):
 *      or -> and -> equality -> comparison -> term (+ -) -> factor (* / %) -> unary -> primary
 *    Lower levels bind tighter, so 1 + 2 * 3 parses as 1 + (2 * 3).
 * 3. AST: Java records (Num, Var, Binary, Unary, Let, Assign, Print, If, While, Block).
 * 4. EVALUATOR: walks the tree recursively. An ENVIRONMENT (map + parent pointer)
 *    implements scopes: lookups walk outward, `let` defines in the innermost scope.
 * Errors (undefined variable, division by zero, syntax errors) throw with a message.
 *
 * COMPLEXITY:
 * Lexing and parsing: O(N) in the source length. Evaluation: proportional to the
 * number of AST nodes executed (loops re-execute their body).
 */
public class ASTInterpreter {

    // ======================= LEXER =======================
    enum T { NUM, IDENT, LET, PRINT, IF, ELSE, WHILE, TRUE, FALSE, OP, LPAREN, RPAREN, LBRACE, RBRACE, SEMI, EOF }

    record Token(T type, String text, int pos) {}

    static List<Token> lex(String src) {
        List<Token> out = new ArrayList<>();
        int i = 0;
        while (i < src.length()) {
            char c = src.charAt(i);
            if (Character.isWhitespace(c)) { i++; continue; }
            if (c == '/' && i + 1 < src.length() && src.charAt(i + 1) == '/') { // // comment
                while (i < src.length() && src.charAt(i) != '\n') i++;
                continue;
            }
            int start = i;
            if (Character.isDigit(c)) {
                while (i < src.length() && Character.isDigit(src.charAt(i))) i++;
                out.add(new Token(T.NUM, src.substring(start, i), start));
            } else if (Character.isLetter(c) || c == '_') {
                while (i < src.length() && (Character.isLetterOrDigit(src.charAt(i)) || src.charAt(i) == '_')) i++;
                String w = src.substring(start, i);
                T type = switch (w) {
                    case "let" -> T.LET; case "print" -> T.PRINT; case "if" -> T.IF; case "else" -> T.ELSE;
                    case "while" -> T.WHILE; case "true" -> T.TRUE; case "false" -> T.FALSE; default -> T.IDENT;
                };
                out.add(new Token(type, w, start));
            } else {
                String two = i + 1 < src.length() ? src.substring(i, i + 2) : "";
                if (two.equals("==") || two.equals("!=") || two.equals("<=") || two.equals(">=") || two.equals("&&") || two.equals("||")) {
                    out.add(new Token(T.OP, two, start));
                    i += 2;
                    continue;
                }
                T type = switch (c) {
                    case '(' -> T.LPAREN; case ')' -> T.RPAREN; case '{' -> T.LBRACE; case '}' -> T.RBRACE; case ';' -> T.SEMI;
                    case '+', '-', '*', '/', '%', '<', '>', '=', '!' -> T.OP;
                    default -> throw new RuntimeException("unexpected character '" + c + "' at " + i);
                };
                out.add(new Token(type, String.valueOf(c), start));
                i++;
            }
        }
        out.add(new Token(T.EOF, "", src.length()));
        return out;
    }

    // ======================= AST =======================
    sealed interface Expr permits Num, Var, Binary, Unary {}
    record Num(long value) implements Expr {}
    record Var(String name) implements Expr {}
    record Binary(String op, Expr left, Expr right) implements Expr {}
    record Unary(String op, Expr operand) implements Expr {}

    sealed interface Stmt permits Let, Assign, Print, If, While, Block {}
    record Let(String name, Expr value) implements Stmt {}
    record Assign(String name, Expr value) implements Stmt {}
    record Print(Expr value) implements Stmt {}
    record If(Expr cond, Stmt then, Stmt otherwise) implements Stmt {}
    record While(Expr cond, Stmt body) implements Stmt {}
    record Block(List<Stmt> body) implements Stmt {}

    // ======================= PARSER =======================
    static class Parser {
        private final List<Token> tokens;
        private int pos = 0;
        Parser(List<Token> tokens) { this.tokens = tokens; }

        private Token peek() { return tokens.get(pos); }
        private boolean check(T type, String text) { Token t = peek(); return t.type() == type && (text == null || t.text().equals(text)); }
        private boolean match(T type, String text) { if (check(type, text)) { pos++; return true; } return false; }
        private Token expect(T type, String text) {
            if (!check(type, text)) throw new RuntimeException("syntax error at " + peek().pos() + ": expected "
                    + (text != null ? "'" + text + "'" : type) + " but found '" + peek().text() + "'");
            return tokens.get(pos++);
        }

        List<Stmt> program() {
            List<Stmt> stmts = new ArrayList<>();
            while (!check(T.EOF, null)) stmts.add(statement());
            return stmts;
        }

        Stmt statement() {
            if (match(T.LET, null)) {
                String name = expect(T.IDENT, null).text();
                expect(T.OP, "=");
                Expr e = expression();
                expect(T.SEMI, null);
                return new Let(name, e);
            }
            if (match(T.PRINT, null)) { Expr e = expression(); expect(T.SEMI, null); return new Print(e); }
            if (match(T.IF, null)) {
                expect(T.LPAREN, null); Expr cond = expression(); expect(T.RPAREN, null);
                Stmt then = statement();
                Stmt otherwise = match(T.ELSE, null) ? statement() : null;
                return new If(cond, then, otherwise);
            }
            if (match(T.WHILE, null)) {
                expect(T.LPAREN, null); Expr cond = expression(); expect(T.RPAREN, null);
                return new While(cond, statement());
            }
            if (match(T.LBRACE, null)) {
                List<Stmt> body = new ArrayList<>();
                while (!check(T.RBRACE, null)) {
                    if (check(T.EOF, null)) throw new RuntimeException("syntax error: missing '}'");
                    body.add(statement());
                }
                expect(T.RBRACE, null);
                return new Block(body);
            }
            String name = expect(T.IDENT, null).text();
            expect(T.OP, "=");
            Expr e = expression();
            expect(T.SEMI, null);
            return new Assign(name, e);
        }

        Expr expression() { return or(); }

        // Each level: parse the tighter level, then loop while we see this level's operators (left-associative)
        private Expr or() { Expr e = and(); while (check(T.OP, "||")) { pos++; e = new Binary("||", e, and()); } return e; }
        private Expr and() { Expr e = equality(); while (check(T.OP, "&&")) { pos++; e = new Binary("&&", e, equality()); } return e; }
        private Expr equality() {
            Expr e = comparison();
            while (check(T.OP, "==") || check(T.OP, "!=")) { String op = tokens.get(pos++).text(); e = new Binary(op, e, comparison()); }
            return e;
        }
        private Expr comparison() {
            Expr e = term();
            while (check(T.OP, "<") || check(T.OP, "<=") || check(T.OP, ">") || check(T.OP, ">=")) {
                String op = tokens.get(pos++).text(); e = new Binary(op, e, term());
            }
            return e;
        }
        private Expr term() {
            Expr e = factor();
            while (check(T.OP, "+") || check(T.OP, "-")) { String op = tokens.get(pos++).text(); e = new Binary(op, e, factor()); }
            return e;
        }
        private Expr factor() {
            Expr e = unary();
            while (check(T.OP, "*") || check(T.OP, "/") || check(T.OP, "%")) { String op = tokens.get(pos++).text(); e = new Binary(op, e, unary()); }
            return e;
        }
        private Expr unary() {
            if (check(T.OP, "-") || check(T.OP, "!")) { String op = tokens.get(pos++).text(); return new Unary(op, unary()); }
            return primary();
        }
        private Expr primary() {
            Token t = peek();
            if (match(T.NUM, null)) return new Num(Long.parseLong(t.text()));
            if (match(T.TRUE, null)) return new Num(1);
            if (match(T.FALSE, null)) return new Num(0);
            if (match(T.IDENT, null)) return new Var(t.text());
            if (match(T.LPAREN, null)) { Expr e = expression(); expect(T.RPAREN, null); return e; }
            throw new RuntimeException("syntax error at " + t.pos() + ": unexpected '" + t.text() + "'");
        }
    }

    // ======================= EVALUATOR =======================
    static class Environment {
        private final Map<String, Long> vars = new HashMap<>();
        private final Environment parent;
        Environment(Environment parent) { this.parent = parent; }

        void define(String name, long value) {
            if (vars.containsKey(name)) throw new RuntimeException("variable '" + name + "' already declared in this scope");
            vars.put(name, value);
        }
        long get(String name) {
            for (Environment e = this; e != null; e = e.parent) if (e.vars.containsKey(name)) return e.vars.get(name);
            throw new RuntimeException("undefined variable '" + name + "'");
        }
        void assign(String name, long value) {
            for (Environment e = this; e != null; e = e.parent) if (e.vars.containsKey(name)) { e.vars.put(name, value); return; }
            throw new RuntimeException("assignment to undeclared variable '" + name + "'");
        }
    }

    private final StringBuilder output = new StringBuilder();
    private long steps = 0;
    private final long maxSteps;

    public ASTInterpreter(long maxSteps) { this.maxSteps = maxSteps; }

    /** Runs a program and returns everything it printed (one value per line). */
    public static String run(String source) {
        ASTInterpreter interp = new ASTInterpreter(10_000_000);
        Environment global = new Environment(null);
        for (Stmt s : new Parser(lex(source)).program()) interp.exec(s, global);
        return interp.output.toString();
    }

    /** Parses and evaluates a single expression (no variables). */
    public static long evaluate(String expression) {
        Parser p = new Parser(lex(expression));
        Expr e = p.expression();
        if (!p.check(T.EOF, null)) throw new RuntimeException("syntax error: trailing input");
        return new ASTInterpreter(1).eval(e, new Environment(null));
    }

    void exec(Stmt s, Environment env) {
        if (++steps > maxSteps) throw new RuntimeException("step limit exceeded (infinite loop?)");
        switch (s) {
            case Let l -> env.define(l.name(), eval(l.value(), env));
            case Assign a -> env.assign(a.name(), eval(a.value(), env));
            case Print p -> output.append(eval(p.value(), env)).append('\n');
            case If i -> {
                if (eval(i.cond(), env) != 0) exec(i.then(), env);
                else if (i.otherwise() != null) exec(i.otherwise(), env);
            }
            case While w -> { while (eval(w.cond(), env) != 0) exec(w.body(), env); }
            case Block b -> {
                Environment inner = new Environment(env); // new scope
                for (Stmt st : b.body()) exec(st, inner);
            }
        }
    }

    long eval(Expr e, Environment env) {
        return switch (e) {
            case Num n -> n.value();
            case Var v -> env.get(v.name());
            case Unary u -> u.op().equals("-") ? -eval(u.operand(), env) : (eval(u.operand(), env) == 0 ? 1 : 0);
            case Binary b -> {
                if (b.op().equals("&&")) yield (eval(b.left(), env) != 0 && eval(b.right(), env) != 0) ? 1 : 0; // short-circuit
                if (b.op().equals("||")) yield (eval(b.left(), env) != 0 || eval(b.right(), env) != 0) ? 1 : 0;
                long l = eval(b.left(), env), r = eval(b.right(), env);
                yield switch (b.op()) {
                    case "+" -> l + r; case "-" -> l - r; case "*" -> l * r;
                    case "/" -> { if (r == 0) throw new RuntimeException("division by zero"); yield l / r; }
                    case "%" -> { if (r == 0) throw new RuntimeException("division by zero"); yield l % r; }
                    case "==" -> l == r ? 1 : 0; case "!=" -> l != r ? 1 : 0;
                    case "<" -> l < r ? 1 : 0; case "<=" -> l <= r ? 1 : 0;
                    case ">" -> l > r ? 1 : 0; case ">=" -> l >= r ? 1 : 0;
                    default -> throw new RuntimeException("unknown operator " + b.op());
                };
            }
        };
    }

    public static void main(String[] args) {
        System.out.println("--- AST INTERPRETER DEMO ---");
        String program = """
                // factorial and the first Fibonacci numbers
                let n = 10;
                let fact = 1;
                let i = 1;
                while (i <= n) { fact = fact * i; i = i + 1; }
                print fact;

                let a = 0; let b = 1; let k = 0;
                while (k < 10) {
                    print a;
                    let next = a + b;   // scoped to the loop body
                    a = b; b = next; k = k + 1;
                }

                let x = 1;
                { let x = 2; print x; }   // shadowing in an inner scope
                print x;
                if (x == 1 && !(fact < 0)) print 100; else print 200;
                """;
        System.out.print(run(program));
        // 3628800, 0 1 1 2 3 5 8 13 21 34, 2, 1, 100
        System.out.println("evaluate(\"2 + 3 * (7 - 2) % 4\") = " + evaluate("2 + 3 * (7 - 2) % 4")); // 2 + 15 % 4 = 5
        try {
            run("let y = 1; print z;");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
