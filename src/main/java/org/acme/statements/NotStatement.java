package org.acme.statements;

public class NotStatement implements Statement {
    private Statement rightStatement;
    private String statement;

    public void setRightStatement(Statement statement) {this.rightStatement = statement;}

    public NotStatement (){
    }

    public static NotStatement of(){return new NotStatement();}

    public Statement addStatement(Statement statement) {
        this.rightStatement = statement;
        return this;
    }

    public static class Builder {

        public Builder(){}

        public Statement build(){
            return new NotStatement(this);
        }
    }

    private NotStatement(Builder builder){
    }

    public boolean evaluate(){
        if ((this.rightStatement != null)) {
            return !rightStatement.evaluate();
        }
        else {
            throw new RuntimeException("Error in statement construction!");
        }
    }

    public String getString() {
        if (this.statement == null){
            setString();
            return statement;
        }
        else {
            return statement;
        }
    }

    private void setString(){
        if (rightStatement != null){
            if(this.rightStatement.isSingleVar()) {
                this.statement = String.format("not %s", this.rightStatement.getString());
            }
            else {
                this.statement = String.format("not (%s)", this.rightStatement.getString());
            }
        }
    }

    public boolean isSingleVar(){
        return false;
    }
}
