package datastructures;

import java.util.Stack;

public class UndoStack {

    Stack<String> actions = new Stack<>();

    public void pushAction(String action) {

        actions.push(action);
    }

    public String undoAction() {

        if(actions.isEmpty()) {

            return "Nothing To Undo";
        }

        return actions.pop();
    }

    public void showActions() {

        for(String action : actions) {

            System.out.println(action);
        }
    }
}