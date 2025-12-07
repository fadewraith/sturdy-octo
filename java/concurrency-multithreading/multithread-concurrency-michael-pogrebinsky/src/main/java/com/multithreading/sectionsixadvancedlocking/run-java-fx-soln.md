Win soln -

<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <javafx.version>17.0.2</javafx.version>
</properties>
```

### Step 2: Configure IntelliJ IDEA

**A. Set Project SDK:**
1. File → Project Structure (Ctrl+Alt+Shift+S)
2. Project Settings → Project
3. SDK: Select **17** (or download if not available)
4. Language Level: **17 - Sealed types, always-strict floating-point semantics**
5. Click **Apply**

**B. Set Module Language Level:**
1. Still in Project Structure
2. Project Settings → Modules
3. Select your module
4. Language level: **17**
5. Click **Apply**

**C. Set Java Compiler:**
1. File → Settings (Ctrl+Alt+S)
2. Build, Execution, Deployment → Compiler → Java Compiler
3. Project bytecode version: **17**
4. Click **Apply**

### Step 3: Add VM Options to Run Configuration

1. Run → Edit Configurations
2. Select your `ReentrantLockJavaFXDemo` configuration (or create new Application configuration)
3. **Main class:** `com.multithreading.sectionsixadvancedlocking.ReentrantLockJavaFXDemo`
4. **VM options:** Add this:
```
   --add-modules javafx.controls,javafx.fxml,javafx.graphics


Enable VM Options Field in IntelliJ
The VM options field is hidden by default. Here's how to show it:
Method 1: Click "Modify Options"

In your Run Configuration window (ReentrantLockJavaFXDemo)
Look for "Modify options" link (usually near the top, looks like a link or small text)
Click it
A dropdown menu appears
Check "Add VM options" or "VM options"
The VM options field will now appear in your configuration

===================================================

Error occurred during initialization of boot layer
java.lang.module.FindException: Module javafx.controls not found

Option 1: Use IntelliJ's Bundled Maven (Easiest)
IntelliJ comes with Maven built-in. You don't need to install anything.
In IntelliJ:

Open Maven Tool Window:

View → Tool Windows → Maven
Or click the "Maven" tab on the right side of IntelliJ


Run the Application:

Expand your project in Maven window
Expand Plugins
Expand javafx
Double-click javafx:run