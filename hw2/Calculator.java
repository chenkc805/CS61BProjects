import list.EquationList;

public class Calculator {
<<<<<<< HEAD
    public EquationList historyOfEquations = null;
=======
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
    // YOU MAY WISH TO ADD SOME FIELDS

    /**
     * TASK 2: ADDING WITH BIT OPERATIONS
     * add() is a method which computes the sum of two integers x and y using 
     * only bitwise operators.
     * @param x is an integer which is one of two addends
     * @param y is an integer which is the other of the two addends
     * @return the sum of x and y
     **/
    public int add(int x, int y) {
<<<<<<< HEAD
        while (y != 0) {
            int carry = x & y;
            x = x ^ y; 
            y = carry << 1;
        }
        return x;
=======
        // YOUR CODE HERE
        return -1;
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
    }

    /**
     * TASK 3: MULTIPLYING WITH BIT OPERATIONS
     * multiply() is a method which computes the product of two integers x and 
     * y using only bitwise operators.
     * @param x is an integer which is one of the two numbers to multiply
     * @param y is an integer which is the other of the two numbers to multiply
     * @return the product of x and y
     **/
    public int multiply(int x, int y) {
<<<<<<< HEAD
        if (x < y) {
            int placeholder = x;
            x = y;
            y = placeholder;
        }
        int result = 0;
        while (y != 0) {
            if ((y & 01) != 0) {
                result = add(result, x);
            }
            x = x << 1;
            y = y >>> 1;
        }
        return result;
=======
        // YOUR CODE HERE
        return -1;
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
    }

    /**
     * TASK 5A: CALCULATOR HISTORY - IMPLEMENTING THE HISTORY DATA STRUCTURE
     * saveEquation() updates calculator history by storing the equation and 
     * the corresponding result.
     * Note: You only need to save equations, not other commands.  See spec for 
     * details.
     * @param equation is a String representation of the equation, ex. "1 + 2"
     * @param result is an integer corresponding to the result of the equation
     **/
    public void saveEquation(String equation, int result) {
<<<<<<< HEAD
        String numbers = "0123456789";
        if (numbers.indexOf(equation.charAt(0)) != -1) {
            historyOfEquations = new EquationList(equation, result, historyOfEquations);
        }
        else {
            return;
        }
=======
        // YOUR CODE HERE
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
    }

    /**
     * TASK 5B: CALCULATOR HISTORY - PRINT HISTORY HELPER METHODS
     * printAllHistory() prints each equation (and its corresponding result), 
     * most recent equation first with one equation per line.  Please print in 
     * the following format:
     * Ex   "1 + 2 = 3"
     **/
    public void printAllHistory() {
<<<<<<< HEAD
        int length = 0;
        EquationList equations = historyOfEquations;
        while (equations != null) {
            length += 1;
            equations = equations.next;
        }
        printHistory(length);
=======
        // YOUR CODE HERE
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
    }

    /**
     * TASK 5B: CALCULATOR HISTORY - PRINT HISTORY HELPER METHODS
     * printHistory() prints each equation (and its corresponding result), 
     * most recent equation first with one equation per line.  A maximum of n 
     * equations should be printed out.  Please print in the following format:
     * Ex   "1 + 2 = 3"
     **/
    public void printHistory(int n) {
<<<<<<< HEAD
        EquationList equations = historyOfEquations;
        for (int i = 0; i < n; i++) {
            if (equations == null) {
                return;
            }
            else {
                System.out.println(equations.equation + " = " + Integer.toString(equations.result));
                equations = equations.next;
            }
        }
=======
        // YOUR CODE HERE
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
    }    

    /**
     * TASK 6: CLEAR AND UNDO
     * undoEquation() removes the most recent equation we saved to our history.
    **/
    public void undoEquation() {
<<<<<<< HEAD
        historyOfEquations = historyOfEquations.next;
=======
        // YOUR CODE HERE
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
    }

    /**
     * TASK 6: CLEAR AND UNDO
     * clearHistory() removes all entries in our history.
     **/
    public void clearHistory() {
<<<<<<< HEAD
        historyOfEquations = null;
=======
        // YOUR CODE HERE
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
    }

    /**
     * TASK 7: ADVANCED CALCULATOR HISTORY COMMANDS
     * cumulativeSum() computes the sum over the result of each equation in our 
     * history.
     * @return the sum of all of the results in history
     **/
    public int cumulativeSum() {
<<<<<<< HEAD
        int finalResult = 0;
        EquationList equations = historyOfEquations;
        while (equations != null) {
            finalResult = equations.result + finalResult;
            equations = equations.next;
        }
        return finalResult;
=======
        // YOUR CODE HERE
        return -1;
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
    }

    /**
     * TASK 7: ADVANCED CALCULATOR HISTORY COMMANDS
     * cumulativeProduct() computes the product over the result of each equation 
     * in history.
     * @return the product of all of the results in history
     **/
    public int cumulativeProduct() {
<<<<<<< HEAD
        int finalResult = 1;
        EquationList equations = historyOfEquations;
        while (equations != null) {
            finalResult = equations.result * finalResult;
            equations = equations.next;
        }
        return finalResult;
    }
}


=======
        // YOUR CODE HERE
        return -1;
    }
}
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
