package org.conagyurig;

public class TransactionState {
    private String baton = null;
    private boolean inTransaction = false;
    public TransactionState() {}

    public String getBaton() {
        return baton;
    }

    public void setBaton(String baton) {
        this.baton = baton;
    }

    public boolean isInTransaction() {
        return inTransaction;
    }

    public void setInTransaction(boolean inTransaction) {
        this.inTransaction = inTransaction;
    }
}
