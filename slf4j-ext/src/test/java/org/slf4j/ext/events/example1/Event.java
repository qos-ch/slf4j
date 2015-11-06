package org.slf4j.ext.events.example1;

import org.slf4j.ext.events.*;

/**
 * @author Himanshu Vijay
 */
public class Event extends RootNode<Event> {
    private Who who = new Who("who", this);
    private What what = new What("what", this);
    //private When when;
    //private Where where;
    //private Why why;

    public Who getWho(){
        return who;
    }

    public void setWho(Who who){
        this.who = who.copy();
    }

    public What getWhat() {
        return what;
    }

    public void setWhat(What what) {
        this.what = what;
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
    private Event() {
        super("EVENT", "event");
    }

    protected static RootNode newInstance(){
        return new Event();
    }

    @Override
    public void setToDefault() {
        who.setToDefault();
        what.setToDefault();
    }

    public static Event get(){
        return (Event)RootNode.get();
    }

    public Event copy(){
        Event copy = new Event();
        copy.setWho(who.copy());
    }
}
