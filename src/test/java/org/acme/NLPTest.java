package org.acme;

import org.acme.pda.*;
import org.acme.statements.AndStatement;
import org.acme.statements.IffStatement;
import org.acme.pda.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class NLPTest {


    @Test
    public void containsTransition(){
        Input input = Input.of(State.START, new HashSet<Integer>(),StackItems.EMPTY);
        assert(Transitions.containsInput(input));
    }

    @Test
    public void testHashCodeInput(){
        Input input = Input.of(State.START, new HashSet<String>(),StackItems.EMPTY);
        assert(input.getId().hashCode() == Transitions.getTransition(input).getBehavior().getInput().getId().hashCode());
    }

    @Test
    public void testPdaInput(){
        AllVars setOfVars = AllVars.of("A and B");
        PushDown pda = new PushDown("A and B", setOfVars);
        assert(pda.getMainStatement() instanceof AndStatement);
    }
    @Test
    public void testPdaInput2(){
        AllVars setOfVars = AllVars.of("A and B");
        PushDown pda = new PushDown("A and B", setOfVars);
        Assertions.assertFalse(pda.getMainStatement() instanceof IffStatement);
    }


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
    public void splitWithCommas(){
        String input = "A and B, B or C";
        String [] expected = {"A and B","B or C"};
        String [] splitString = input.split(",");
        for (int i = 0; i < splitString.length; i++) {
            splitString[i] = splitString[i].trim();
        }
        assert(Arrays.equals(expected,splitString));

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
        Input input = Input.of(State.START,Operator.NOT_SET, StackItems.EMPTY);
        assert(Transitions.containsInput(input));
    }

    @Test
    public void containsInputFalse(){
        Input input = Input.of(State.START, Operator.NON_NOT_OPERATORS, StackItems.PARENTHESIS);
        Assertions.assertFalse(Transitions.containsInput(input));
    }

    @Test
    public void containsTransitionTrue(){
        assert(Transitions.contains(Transitions.START_ID));
    }

    @Test
    public void getTransition(){
        Input input = Input.of(State.START, Identifier.IDS, StackItems.EMPTY);
        assert(Transitions.START_ID == Transitions.getTransition(input));
    }

    @Test
    public void equals(){
        Input input = Input.of(State.START, Identifier.IDS,StackItems.EMPTY);
        assert(Transitions.START_ID.equals(Transitions.getTransition(input)));
    }

    @Test
    public void inputEqualityRightSide(){
        Input input = Input.of(State.START, Identifier.IDS, StackItems.EMPTY);
        Input input1 = Input.of(State.START, Identifier.IDS,StackItems.EMPTY);
        assert(input.equals(Transitions.START_ID.getBehavior().getInput()));
    }

    @Test
    public void inputEqualityLeftSide(){
        Input input = Input.of(State.START, Identifier.IDS,StackItems.EMPTY);
        Input input1 = Input.of(State.START, Identifier.IDS,StackItems.EMPTY);
        assert (input.equals(input1));
    }

    @Test
    public void inputEqualityOtherSide(){
        Input input = Input.of(State.START, Identifier.IDS,StackItems.EMPTY);
        Input input1 = Input.of(State.START, Identifier.IDS,StackItems.EMPTY);
        assert (input1.equals(input));
    }

    @Test
    public void inputEqualityFalse(){
        Input input = Input.of(State.ID, Identifier.IDS,StackItems.EMPTY);
        Input input1 = Input.of(State.START, Identifier.IDS,StackItems.EMPTY);
        Assertions.assertFalse(input.equals(input1));
    }

    @Test
    public void objectEquality(){
        Input input = Input.of(State.ID, Identifier.IDS,StackItems.EMPTY);
        Input input1 = Input.of(State.ID, Identifier.IDS,StackItems.EMPTY);
        assert(input.equals(input1));
    }

    @Test
    public void readInputTest(){
        String s = "A and B";
    }

    @Test
    public void pushItem(){
        Stack<StackItems> stack = new Stack<>();
        stack.push(Transitions.PARENTHESIS_OPEN_E.getBehavior().getOutput().getItem());
        assert(stack.peek().equals(StackItems.PARENTHESIS));
    }

    @Test
    public void popItem(){
        Stack<StackItems> stack = new Stack<>();
        stack.push(StackItems.EMPTY);
        stack.push(Transitions.PARENTHESIS_OPEN_E.getBehavior().getOutput().getItem());
        stack.pop();
        assert(stack.peek().equals(StackItems.EMPTY));
    }

    @Test
    public void InputHashTrue(){
        assert(Input.of(State.START, Identifier.IDS, StackItems.EMPTY).hashCode() ==
                Input.of(State.START, Identifier.IDS,StackItems.EMPTY).hashCode()
        );
    }

    @Test
    public void InputHashFalse(){
        Assertions.assertFalse(Input.of(State.START, Identifier.IDS, StackItems.EMPTY).hashCode() ==
                Input.of(State.START, Identifier.IDS,StackItems.PARENTHESIS).hashCode());
    }

    @Test
    public void InputHashFalseId(){
        Assertions.assertFalse(Input.of(State.START, Identifier.IDS, StackItems.EMPTY).hashCode() ==
                Input.of(State.START,Operator.NON_NOT_OPERATORS,StackItems.PARENTHESIS).hashCode());
    }

    @Test
    public void InputHashFalseState(){
        Assertions.assertFalse(Input.of(State.START, Identifier.IDS, StackItems.EMPTY).hashCode() ==
                Input.of(State.ID, Identifier.IDS,StackItems.PARENTHESIS).hashCode());
    }

    @Test
    public void testSetVars(){
        AllVars vars = AllVars.of("A and B and C");
        assert(vars.contains("A"));
    }

    @Test
    public void testNonNot(){
        assert(Operator.NON_NOT_OPERATORS.contains(Operator.AND));
    }


    @Test
    public void testSplit(){
        assert(Parser.splitStatement("A and (B or C)")
                .equals(" A and ( B or C )"));
    }



}


