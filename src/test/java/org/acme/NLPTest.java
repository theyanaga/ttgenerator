package org.acme;

import org.acme.pda.Input;
import org.acme.pda.StackItems;
import org.acme.pda.State;
import org.acme.pda.Transition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class NLPTest {

    @Test
    public void splitMethod(){
        String input = "A iff B, A and B";
        String[] expected = {"A iff B", " A and B"};
        assert(Arrays.equals(expected, input.split(",")));
    }

    @Test
    public void splitAndTrim(){
        String input = "A iff B, A and B";
        String[] expected = {"A iff B", "A and B"};
        String [] splitInput = input.split(",");
        for (int i = 0; i < splitInput.length; i++) {
            splitInput[i] = splitInput[i].trim();
        }
        assert(Arrays.equals(expected, splitInput));
    }

    @Test
    public void splitParenthesis(){
        String input = "A iff (not A and B)";
    }

    @Test
    public void splitStatement(){
        String statement = "not A and not B";
        String[] splitStatement = statement.split("\\s");
        String[] expected = {"not","A","and","not","B"};
        assert(Arrays.equals(splitStatement, expected));
    }

    @Test
    public void getVars(){
        String statement = "not A and not B";
        String[] splitStatement = statement.split("\\s");
        Set<String> operatorSet = new HashSet<>();
        operatorSet.add("not");
        operatorSet.add("and");
        operatorSet.add("iff");
        operatorSet.add("or");
        operatorSet.add("if");
        operatorSet.add("xor");
        List<String> statementOperators = new ArrayList<>();
        for (String s : splitStatement) {
            if (operatorSet.contains(s)) {
                statementOperators.add(s);
            }
        }
        String[] expected = {"not","and","not"};
        assert(Arrays.equals(expected, statementOperators.toArray()));
    }

    @Test
    public void containsInput(){
        Input input = Input.formInput(State.START,"not", StackItems.EMPTY);
        assert(Transition.containsInput(input));
    }

    @Test
    public void containsInputFalse(){
        Input input = Input.formInput(State.START, "xor", StackItems.PARENTHESIS);
        Assertions.assertFalse(Transition.containsInput(input));
    }

    @Test
    public void containsTransitionTrue(){
        assert(Transition.contains(Transition.ID_N));
    }

    @Test
    public void getTransition(){
        Input input = Input.formInput(State.START, "id", StackItems.EMPTY);
        assert(Transition.ID_E == Transition.getTransition(input));
    }

    @Test
    public void equals(){
        Input input = Input.formInput(State.START,"id",StackItems.EMPTY);
        assert(Transition.ID_E.equals(Transition.getTransition(input)));
    }

    @Test
    public void inputEquality(){
        Input input = Input.formInput(State.START,"id",StackItems.EMPTY);
        Input input1 = Input.formInput(State.START,"id",StackItems.EMPTY);
        assert(input.equals(Transition.ID_E.getBehavior().getInput()));
    }

    @Test
    public void readInputTest(){
        String s = "A and B";
    }

    @Test
    public void pushItem(){
        Stack<StackItems> stack = new Stack<>();
        stack.push(Transition.NOT_E.getBehavior().getOutput().getItem());
        assert(stack.peek().equals(StackItems.NOT_OPERATOR));
    }

    @Test
    public void popItem(){
        Stack<StackItems> stack = new Stack<>();
        stack.push(StackItems.EMPTY);
        stack.push(Transition.NOT_E.getBehavior().getOutput().getItem());
        stack.pop();
        assert(stack.peek().equals(StackItems.EMPTY));
    }
}

