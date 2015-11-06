package org.slf4j.ext.events.test;

import org.slf4j.ext.events.*;

/**
 * @author Himanshu Vijay
 */
public class EventExample2 extends RootNode {
    public final Who who = new Who("who", this);
    public final What what = new What("what", this);
    //private When when;
    //private Where where;
    //private Why why;

    public class Who extends NonLeafNode {
        private StringNode id = new StringNode("id", this, "");

        private Who(String name, Node parent){
            super(name, parent);
        }

        @Override
        public void setToDefault() {
            id.setToDefault();
        }

        public String getId() {
            return id.get();
        }

        public void setId(String id) {
            this.id.set(id);
        }
    }

    public class What extends NonLeafNode {
        private Outcome outcome = new Outcome("outcome", this);

        private What(String name, Node parent){
            super(name, parent);
        }

        @Override
        public void setToDefault() {
            outcome.setToDefault();
        }

        public Outcome getOutcome() {
            return outcome;
        }

        public void setOutcome(Outcome outcome) {
            this.outcome = outcome;
        }

        public class Outcome extends NonLeafNode {
            private StringNode result = new StringNode("result", this, "");
            private StringNode error = new StringNode("error", this, "");
            private IntegerNode statusCode = new IntegerNode("statusCode", this, 0);

            private Outcome(String name, Node parent){
                super(name, parent);
            }

            public String getResult() {
                return result.get();
            }

            public void setResult(String result) {
                this.result.set(result);
            }

            public StringNode getError() {
                return error;
            }

            public void setError(String error) {
                this.error.set(error);
            }

            public IntegerNode getStatusCode() {
                return statusCode;
            }

            public void setStatusCode(Integer statusCode) {
                this.statusCode.set(statusCode);
            }

            @Override
            public void setToDefault() {
                result.setToDefault();
                error.setToDefault();
                statusCode.setToDefault();
            }
        }
    }

    /**
     * @param marker Could be something like 'EVENT' or 'AUDIT' etc.
     * @param name
     */
    private EventExample2() {
        super("EVENT", "event");
    }

    protected static RootNode newInstance(){
        return new EventExample2();
    }

    @Override
    public void setToDefault() {
        who.setToDefault();
        what.setToDefault();
    }

    public static EventExample2 get(){
        return (EventExample2)RootNode.get();
    }
}
