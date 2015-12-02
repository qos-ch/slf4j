package org.slf4j.ext.mdc.example1;

import org.slf4j.ext.mdc.*;
import org.slf4j.ext.mdc.tree.*;

/**
 * @author Himanshu Vijay
 *
 * This is how we write the POJO structure currently. Lot of boilerplate code.
 */
public class Event extends RootNode<Event> {
  private Who who = new Who("who", this);
  private What what = new What("what", this);
  private StringNode trackingId = new StringNode("trackingId", this, "0");

  public Who getWho() {
    return who;
  }

  public void setWho(Who who) {
    this.who = who.copy();
  }

  public What getWhat() {
    return what;
  }

  public void setWhat(What what) {
    this.what = what.copy(this);
  }

  public StringNode getTrackingId(){
    return trackingId;
  }

  public void setTrackingId(String trackingId){
    this.trackingId.set(trackingId);
  }

  public class Who extends NonLeafNode<Who> {
    private StringNode id = new StringNode("id", this, "");

    private Who(String name, Node parent) {
      super(name, parent);
    }

    @Override
    public Who copy(Node parent) {
      Who copy = new Who(this.NAME, null);
      copy.setParent(parent);
      return copy;
    }

    @Override
    public void setToDefault() {

    }
  }

  public class What extends NonLeafNode<What> {
    private Outcome outcome = new Outcome("outcome", this);

    private What(String name, Node parent) {
      super(name, parent);
    }

    @Override
    public What copy(Node parent) {
      What copy = new What(this.NAME, null);
      copy.setOutcome(this.outcome.copy());
      copy.setParent(parent);
      return copy;
    }

    @Override
    public void setToDefault() {
      outcome.setToDefault();
    }

    public Outcome getOutcome() {
      return outcome;
    }

    public void setOutcome(Outcome outcome) {
      this.outcome = outcome.copy();
    }

    public class Outcome extends NonLeafNode<Outcome> {
      private StringNode resultString = new StringNode("result_string", this, "");
      private IntegerNode resultNumber = new IntegerNode("result_number", this, 0);
      private BooleanNode resultBoolean = new BooleanNode("result_boolean", this, false);
      private StringNode error = new StringNode("error", this, "");
      private IntegerNode statusCode = new IntegerNode("statusCode", this, 0);

      private Outcome(String name, Node parent) {
        super(name, parent);
      }

      @Override
      public Outcome copy(Node parent) {
        Outcome copy = new Outcome(this.NAME, null);
        copy.setResultString(this.resultString.get());
        copy.setResultNumber(this.resultNumber.get());
        copy.setResultBoolean(this.resultBoolean.get());
        copy.setError(this.error.get());
        copy.setStatusCode(this.statusCode.get());
        copy.setParent(parent);
        return copy;
      }

      public String getResultString() {
        return resultString.get();
      }

      public void setResultString(String result) {
        this.resultString.set(result);
      }

      public Integer getResultNumber() {
        return resultNumber.get();
      }

      public void setResultNumber(Integer resultNumber) {
        this.resultNumber.set(resultNumber);
      }

      public Boolean getResultBoolean() {
        return resultBoolean.get();
      }

      public void setResultBoolean(Boolean resultBoolean) {
        this.resultBoolean.set(resultBoolean);
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
        resultString.setToDefault();
        error.setToDefault();
        statusCode.setToDefault();
      }
    }
  }

  private Event() {
    super("EVENT", "event");
  }

  protected static RootNode newInstance() {
    return new Event();
  }

  @Override
  public void setToDefault() {
    who.setToDefault();
    what.setToDefault();
  }

  public static Event get() {
    return (Event) RootNode.get();
  }

  @Override
  public Event copy() {
    Event copy = new Event();
    copy.setWho(who.copy());
    copy.setWhat(what.copy());
    return copy;
  }
}
