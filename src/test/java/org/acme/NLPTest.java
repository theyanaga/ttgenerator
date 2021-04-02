package org.acme;

import org.acme.pda.*;
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
        assert(Transition.contains(Transition.START_ID));
    }

    @Test
    public void getTransition(){
        Input input = Input.formInput(State.START, "id", StackItems.EMPTY);
        assert(Transition.START_ID == Transition.getTransition(input));
    }

    @Test
    public void equals(){
        Input input = Input.formInput(State.START,"id",StackItems.EMPTY);
        assert(Transition.START_ID.equals(Transition.getTransition(input)));
    }

    @Test
    public void inputEqualityRightSide(){
        Input input = Input.formInput(State.START,"id",StackItems.EMPTY);
        Input input1 = Input.formInput(State.START,"id",StackItems.EMPTY);
        assert(input.equals(Transition.START_ID.getBehavior().getInput()));
    }

    @Test
    public void inputEqualityLeftSide(){
        Input input = Input.formInput(State.START,"id",StackItems.EMPTY);
        Input input1 = Input.formInput(State.START,"id",StackItems.EMPTY);
        assert (input.equals(input1));
    }

    @Test
    public void inputEqualityOtherSide(){
        Input input = Input.formInput(State.START,"id",StackItems.EMPTY);
        Input input1 = Input.formInput(State.START,"id",StackItems.EMPTY);
        assert (input1.equals(input));
    }

    @Test
    public void inputEqualityFalse(){
        Input input = Input.formInput(State.ID,"id",StackItems.EMPTY);
        Input input1 = Input.formInput(State.START,"id",StackItems.EMPTY);
        Assertions.assertFalse(input.equals(input1));
    }

    @Test
    public void objectEquality(){
        Input input = Input.formInput(State.ID,"id",StackItems.EMPTY);
        Input input1 = Input.formInput(State.ID,"id",StackItems.EMPTY);
        assert(input.equals(input1));
    }

    @Test
    public void readInputTest(){
        String s = "A and B";
    }

    @Test
    public void pushItem(){
        Stack<StackItems> stack = new Stack<>();
        stack.push(Transition.PARENTHESIS_OPEN_E.getBehavior().getOutput().getItem());
        assert(stack.peek().equals(StackItems.PARENTHESIS));
    }

    @Test
    public void popItem(){
        Stack<StackItems> stack = new Stack<>();
        stack.push(StackItems.EMPTY);
        stack.push(Transition.PARENTHESIS_OPEN_E.getBehavior().getOutput().getItem());
        stack.pop();
        assert(stack.peek().equals(StackItems.EMPTY));
    }

    @Test
    public void InputHashTrue(){
        assert(Input.formInput(State.START, "id", StackItems.EMPTY).hashCode() ==
                Input.formInput(State.START,"id",StackItems.EMPTY).hashCode()
        );
    }

    @Test
    public void InputHashFalse(){
        Assertions.assertFalse(Input.formInput(State.START, "id", StackItems.EMPTY).hashCode() ==
                Input.formInput(State.START,"id",StackItems.PARENTHESIS).hashCode());
    }

    @Test
    public void InputHashFalseId(){
        Assertions.assertFalse(Input.formInput(State.START, "id", StackItems.EMPTY).hashCode() ==
                Input.formInput(State.START,"potato",StackItems.PARENTHESIS).hashCode());
    }

    @Test
    public void InputHashFalseState(){
        Assertions.assertFalse(Input.formInput(State.START, "id", StackItems.EMPTY).hashCode() ==
                Input.formInput(State.ID,"id",StackItems.PARENTHESIS).hashCode());
    }

    @Test
    public void readInput(){
        PushDown pda = new PushDown();
        assert(pda.readInput("id and id"));
    }

    @Test
    public void readInputSimpleP(){
        PushDown pda = new PushDown();
        assert(pda.readInput("(id and id)"));
    }

    @Test
    public void pushParenthesis(){ //See everything through with the parenthesis stuff
        PushDown pda = new PushDown();
        pda.readInput("(");
        assert(pda.getAutomataStack().peek().equals(StackItems.PARENTHESIS));
    }

    @Test
    public void popParenthesis(){
        PushDown pda = new PushDown();
        assert(pda.readInput("(id)"));
    }

    @Test
    public void readInputXor(){
        PushDown pda = new PushDown();
        assert(pda.readInput("(id xor id)"));
    }
}
