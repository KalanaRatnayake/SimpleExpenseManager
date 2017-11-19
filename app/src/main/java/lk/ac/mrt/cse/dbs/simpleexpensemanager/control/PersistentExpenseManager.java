package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by Kalana on 11/19/2017.
 */

public class PersistentExpenseManager extends ExpenseManager {

    Context context;

    public PersistentExpenseManager(Context tempContext){
        context = tempContext;
        setup();
    }
    @Override
    public void setup() {
        TransactionDAO PTransactionDAO = new PersistentTransactionDAO(context);
        setTransactionsDAO(PTransactionDAO);

        AccountDAO PAccountDAO = new PersistentAccountDAO(context);
        setAccountsDAO(PAccountDAO);
    }
}
