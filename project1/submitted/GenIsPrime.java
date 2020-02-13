import jminusminus.CLEmitter;
import static jminusminus.CLConstants.*;
import java.util.ArrayList;

/**
 * This class programatically generates the class file for 
 * the following Java application:
 * 
 * public class IsPrime {
 *    // Returns true if n is prime, and false otherwise
 *    private static boolean isPrime(int n) {
 *        if(n < 2){
 *            return false;
 *        }
 *        for (int i = 2; i <= n; i++) {
 *            if(n % 1 == 0){
 *                return false;
 *            }
 *        }
 *        return true;
 *    }
 *    
 *    // Entry Point
 *    public static void main(String[] args) {
 *        int n = Integer.parseInt(args[0]);
 *        boolean result = isPrime(n);
 *        if (result) {
 *            System.out.println(n + "is a prime number");
 *        } else {
 *            System.out.println(n + "is not a prime number");
 *        }
 *    }
 * }
 */

public class GenIsPrime {
    public static void main(String[] args) {
        CLEmitter e = new CLEmitter(true);
        ArrayList<String> accessFlags = new ArrayList<String>();

        // Add IsPrime class
        accessFlags.add("public");
        e.addClass(accessFlags, "IsPrime", "java/lang/Object", null, true);

        // Add the implicit no-arg constructor IsPrime()
        accessFlags.clear();
        accessFlags.add("public");
        e.addMethod(accessFlags, "<init>", "()V", null, true);
        e.addNoArgInstruction(ALOAD_0);
        e.addMemberAccessInstruction(INVOKESPECIAL, "java/lang/Object",
                                                    "<init>", "()V");
        e.addNoArgInstruction(RETURN);
        
        // Add isPrime() method to IsPrime
        accessFlags.clear();
        accessFlags.add("private");
        accessFlags.add("static");
        e.addMethod(accessFlags, "isPrime", "(I)Z", null, true);
        
        // Load n onto stack
        e.addNoArgInstruction(ILOAD_0);
        
        // Load constant 2 onto stack
        e.addNoArgInstruction(ICONST_2);
        
        // Branch to "LabelB" if n >= 2
        e.addBranchInstruction(IF_ICMPGE, "LabelA");
        
        e.addNoArgInstruction(ICONST_0);
        e.addNoArgInstruction(IRETURN);
        
        e.addLabel("LabelA");
        
        // Load i = 2 onto stack and pop to local variable 2 (int i)
        e.addNoArgInstruction(ICONST_2);
        e.addNoArgInstruction(ISTORE_2);
        
        //Emit Label "LabelD"
        e.addLabel("LabelD");
        
        // Comute n / i onto stack
        e.addNoArgInstruction(ILOAD_2);
        e.addNoArgInstruction(ILOAD_0);
        e.addNoArgInstruction(IDIV);

        e.addNoArgInstruction(ILOAD_2);
        // If i > n / i goto LabelB
        e.addBranchInstruction(IF_ICMPGT, "LabelB");
        
        // If n % i != 0  goto LabelC
        e.addNoArgInstruction(ILOAD_2);     // Push i onto stack
        e.addNoArgInstruction(ILOAD_0);     // Push n onto stack
        e.addNoArgInstruction(IREM);        // Pop n and i then n % i
        
        // Chceck if n % i != 0 goto LabelC
        e.addBranchInstruction(IFNE, "LabelC");
        
        //Otherwise return false
        e.addNoArgInstruction(ICONST_0);
        e.addNoArgInstruction(IRETURN);
        
        // Emit label LabelC
        e.addLabel("LabelC");
        
        // Increment int i by 1
        // goto LabelD
        e.addIINCInstruction(2, 1);
        e.addBranchInstruction(GOTO, "LabelD");
        
        // Emit label Label
        e.addLabel("LabelB");
        
        // Return true
        e.addNoArgInstruction(ICONST_1);
        e.addNoArgInstruction(IRETURN);
        
        
        // Add main method to IsPrime
        accessFlags.clear();
        accessFlags.add("public");
        accessFlags.add("static");
        e.addMethod(accessFlags, "main", "([Ljava/lang/String;)V", null, true);
        
                                      
        // Get command-line argument n, convert it into an integer, and
        // store it away
        e.addNoArgInstruction(ALOAD_0);
        e.addNoArgInstruction(ICONST_0);
        e.addNoArgInstruction(AALOAD);
        e.addMemberAccessInstruction(INVOKESTATIC,  "java/lang/Integer",
                                                    "parseInt",
                                                    "(Ljava/lang/String;)I");
        e.addNoArgInstruction(ISTORE_1);
        
        e.addNoArgInstruction(ILOAD_1);
        //Invoke instance method p.IsPrime() and store onto stack
        e.addMemberAccessInstruction(INVOKESTATIC,  "IsPrime", 
                                                    "isPrime", 
                                                    "(I)Z");
        e.addBranchInstruction(IFEQ, "LabelNoPrime"); 
        
        // Get System.out on the stack
        e.addMemberAccessInstruction(GETSTATIC, "java/lang/System", 
                                                "out", "Ljava/io/PrintStream;");
        
        // Create an intance sb of StringBuffer on the stack and duplicate it
        e.addReferenceInstruction(NEW, "java/lang/StringBuffer");
        e.addNoArgInstruction(DUP);
        
        // Invoke the constructor StringBuffer()
        e.addMemberAccessInstruction(INVOKESPECIAL, "java/lang/StringBuffer",
                                                    "<init>", 
                                                    "()V");
        
        e.addNoArgInstruction(ILOAD_1);
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer",
                                                    "append", 
                                                "(I)Ljava/lang/StringBuffer;");
        e.addLDCInstruction(" is a prime number");
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer",
                                                    "append", 
                                "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
                                                
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer",
                                                    "toString", 
                                                    "()Ljava/lang/String;");
                                                    
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/io/PrintStream", 
                                                    "println", 
                                                    "(Ljava/lang/String;)V");
        // Add branch if number is prime,
        e.addBranchInstruction(GOTO, "Done");
        
        // Emit label "Label1"
        e.addLabel("LabelNoPrime");
        
        e.addMemberAccessInstruction(GETSTATIC, "java/lang/System", 
                                                "out", "Ljava/io/PrintStream;");
        
        // Create an intance sb of StringBuffer on the stack and duplicate it
        e.addReferenceInstruction(NEW, "java/lang/StringBuffer");
        e.addNoArgInstruction(DUP);
        
        // Invoke the constructor StringBuffer()
        e.addMemberAccessInstruction(INVOKESPECIAL, "java/lang/StringBuffer",
                                                    "<init>", 
                                                    "()V");
        
        e.addNoArgInstruction(ILOAD_1);
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer",
                                                    "append", 
                                                "(I)Ljava/lang/StringBuffer;");
        e.addLDCInstruction(" is not a prime number");
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer",
                                                    "append", 
                                "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
                                                
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer",
                                                    "toString", 
                                                    "()Ljava/lang/String;");
                                                    
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/io/PrintStream", 
                                                    "println", 
                                                    "(Ljava/lang/String;)V");
        
        // Emit label "Done"
        e.addLabel("Done");
                  
        e.addNoArgInstruction(RETURN);        
        
        // Write IsPrime.class to file system
        e.write();
    }
}
